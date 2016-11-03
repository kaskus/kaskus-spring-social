package org.springframework.social.kaskus.api;

import java.io.Serializable;

public class KaskusProfile extends KaskusObject implements Serializable {

    private static final long serialVersionUID = 9040174441548063059L;

    private long userid;
    private String username;
    private long lastlogin;
    private long lastlogout;
    private String usergroupid;
    private String firstname;
    private String lastname;
    private int gender;
    private String email;
    private String address;
    private String country;
    private String province;
    private String ktpnumber;
    private String phone;
    private long joindate;
    private long dateofbirth;
    private long profilevisits;
    private String biography;
    private long wallet_id;
    private int is_friend;
    private int is_following;
    private long is_ignored;
    private String profilepicture;
    private String usertitle;
    private long number_of_post;
    private long total_badge;
    private int enable_reputation;
    private long reputation;
    private String reputation_title;
    private boolean is_donatur;
    private int reputation_box;
    private int total_wtb;
    private int total_wts;
    private int friend;
    private int following;
    private int follower;
    private String country_name;
    private int vm_enable;
    private long donatur_expiry_date;
    private long donatur_registration_date;

    public KaskusProfile() {}

    public long getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public long getLastlogin() {
        return lastlogin;
    }

    public long getLastlogout() {
        return lastlogout;
    }

    public String getUsergroupid() {
        return usergroupid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getKtpnumber() {
        return ktpnumber;
    }

    public String getPhone() {
        return phone;
    }

    public long getJoindate() {
        return joindate;
    }

    public long getDateofbirth() {
        return dateofbirth;
    }

    public long getProfilevisits() {
        return profilevisits;
    }

    public String getBiography() {
        return biography;
    }

    public long getWallet_id() {
        return wallet_id;
    }

    public int getIs_friend() {
        return is_friend;
    }

    public int getIs_following() {
        return is_following;
    }

    public long getIs_ignored() {
        return is_ignored;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public String getUsertitle() {
        return usertitle;
    }

    public long getNumber_of_post() {
        return number_of_post;
    }

    public long getTotal_badge() {
        return total_badge;
    }

    public int getEnable_reputation() {
        return enable_reputation;
    }

    public long getReputation() {
        return reputation;
    }

    public String getReputation_title() {
        return reputation_title;
    }

    public boolean isIs_donatur() {
        return is_donatur;
    }

    public int getReputation_box() {
        return reputation_box;
    }

    public int getTotal_wtb() {
        return total_wtb;
    }

    public int getTotal_wts() {
        return total_wts;
    }

    public int getFriend() {
        return friend;
    }

    public int getFollowing() {
        return following;
    }

    public int getFollower() {
        return follower;
    }

    public String getCountry_name() {
        return country_name;
    }

    public int getVm_enable() {
        return vm_enable;
    }

    public long getDonatur_expiry_date() {
        return donatur_expiry_date;
    }

    public long getDonatur_registration_date() {
        return donatur_registration_date;
    }

    public String getSecuredProfilepicture() {
        return profilepicture.replace("http://", "https://");
    }
}
