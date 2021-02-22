package com.github.udpnarola.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SocialLoginDetail {

    private String authCode;
    private String oauthToken;
    private String oauthVerifier;
}
