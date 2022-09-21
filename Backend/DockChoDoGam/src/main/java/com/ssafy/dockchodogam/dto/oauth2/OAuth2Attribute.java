package com.ssafy.dockchodogam.dto.oauth2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;

    public static OAuth2Attribute of(String provider, String attributeKey, Map<String, Object> attributes){
        switch (provider){
            case "kakao":
                return ofKakao(attributeKey, attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static OAuth2Attribute ofKakao(String attributeKey, Map<String, Object> attributes){

        return OAuth2Attribute.builder()
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }
}
