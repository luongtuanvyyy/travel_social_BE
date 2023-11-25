package com.app.security.oauth2.user;


import com.app.exception.OAuth2AuthenticationProcessingException;
import com.app.type.EAuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(EAuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }
        throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
    }
}
