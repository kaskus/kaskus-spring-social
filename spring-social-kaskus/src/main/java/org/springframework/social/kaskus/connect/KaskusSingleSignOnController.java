package org.springframework.social.kaskus.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/sso")
public class KaskusSingleSignOnController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(KaskusSingleSignOnController.class);

    private static final String ACTION_LOGOUT = "logout";

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final UsersConnectionRepository usersConnectionRepository;

    private final SignInAdapter signInAdapter;

    private final String providerId = "kaskus";

    private ConnectSupport connectSupport;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Inject
    public KaskusSingleSignOnController(
        ConnectionFactoryLocator connectionFactoryLocator,
        UsersConnectionRepository usersConnectionRepository,
        SignInAdapter signInAdapter) {

        this.connectionFactoryLocator = connectionFactoryLocator;
        this.usersConnectionRepository = usersConnectionRepository;
        this.signInAdapter = signInAdapter;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> sso(
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String logout,
            @RequestParam(required = false) String action,
            NativeWebRequest request) {
        if (key != null) {
            OAuth1ConnectionFactory<?> connectionFactory = (OAuth1ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);

            KaskusConnectionFactory kaskusConnectionFactory = (KaskusConnectionFactory) connectionFactory;

            OAuthToken oAuthToken = kaskusConnectionFactory.exchangeForToken(key);

            handleSignIn(request, connectionFactory, oAuthToken);
        } else if (logout != null && action != null && action.equals(ACTION_LOGOUT)) {
            handleLogout(request);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ExceptionHandler({KaskusSingleSingOnException.class, Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        logger.error(e.getMessage());
        if (e instanceof KaskusSingleSingOnException)
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.connectSupport = new ConnectSupport(sessionStrategy);
        this.connectSupport.setUseAuthenticateUrl(true);
    }

    private void handleSignIn(NativeWebRequest request, OAuth1ConnectionFactory<?> connectionFactory, OAuthToken oAuthToken) {
        // remove current authentication if there is successful sign in attempt
        handleLogout(request);

        Connection<?> connection = connectionFactory.createConnection(oAuthToken);

        List<String> userIds = usersConnectionRepository.findUserIdsWithConnection(connection);
        if (userIds.size() == 1) {
            usersConnectionRepository.createConnectionRepository(userIds.get(0)).updateConnection(connection);
            signInAdapter.signIn(userIds.get(0), connection, request);
        }
    }

    private void handleLogout(NativeWebRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request.getNativeRequest();
            HttpServletResponse httpServletResponse = (HttpServletResponse) request.getNativeResponse();
            new CookieClearingLogoutHandler("SESSION", "remember-me").logout(httpServletRequest, httpServletResponse, auth);
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
        }
    }

}
