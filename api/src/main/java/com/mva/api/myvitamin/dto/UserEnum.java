package com.mva.api.myvitamin.dto;

public enum UserEnum {
    NEW_USER("newUser", "1"),
    EXISTING_USER("existingUser", "2");

    private final String userType;
    private final String termsValue;

    UserEnum(String userType, String termsValue) {
        this.userType = userType;
        this.termsValue = termsValue;
    }

    public String getUserType(String termsValue) {
        return userType;
    }

    public String getTermsValue(){
        return termsValue;
    }

}
