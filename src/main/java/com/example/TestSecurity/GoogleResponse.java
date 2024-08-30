package com.example.TestSecurity;

import java.util.Map;

public class GoogleResponse implements OAuth2Response {

    private final Map<String,Object> attribute;
    public GoogleResponse(Map<String, Object> attributes) {
        this.attribute=attributes;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProvideId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
