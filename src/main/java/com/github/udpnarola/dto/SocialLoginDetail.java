package com.github.udpnarola.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLoginDetail {

    private String authCode;
    private String oauthToken;
    private String oauthVerifier;

    public SocialLoginDetail() {
    }

    public SocialLoginDetail(String authCode) {
        this.authCode = authCode;
    }

    public SocialLoginDetail(String oauthToken, String oauthVerifier) {
        this.oauthToken = oauthToken;
        this.oauthVerifier = oauthVerifier;
    }
}
