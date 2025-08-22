package com.eywatech.eywarent.payment.enums;

public enum Currency {
    TL("TL"),
    USD("USD"),
    EURO("EURO");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Currency fromCode(String code) {
        for (Currency type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}

