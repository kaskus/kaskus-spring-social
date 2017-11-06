package org.springframework.social.kaskus.connect;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "return")
public class SsoResponse {

    private String oauthToken;
    private String oauthTokenSecret;
    private String result;

    @XmlElement(name = "oauth_token")
    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    @XmlElement(name = "oauth_token_secret")
    public String getOauthTokenSecret() {
        return oauthTokenSecret;
    }

    public void setOauthTokenSecret(String oauthTokenSecret) {
        this.oauthTokenSecret = oauthTokenSecret;
    }

    @XmlElement
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
