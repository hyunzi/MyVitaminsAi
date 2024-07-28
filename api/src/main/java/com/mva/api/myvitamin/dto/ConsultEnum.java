package com.mva.api.myvitamin.dto;

public enum ConsultEnum {
    NEW_CONSULT("newConsult", "1"),
    EXISTING_CONSULT("existingConsult", "2");

    private final String consultType;
    private final String termsValue;

    ConsultEnum(String consultType, String termsValue) {
        this.consultType = consultType;
        this.termsValue = termsValue;
    }

    public String getConsultType() {
        return consultType;
    }

    public String getTermsValue(){
        return termsValue;
    }

    public static ConsultEnum fromTermsValue(String termsValue) {
        for (ConsultEnum consultEnum : ConsultEnum.values()) {
            if (consultEnum.getTermsValue().equals(termsValue)) {
                return consultEnum;
            }
        }
        throw new IllegalArgumentException("Invalid terms value: " + termsValue);
    }
}
