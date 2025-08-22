package com.eywatech.eywarent.payment.enums;

public enum PaymentReason {
    HGS("HGS"),
    RENT("RENT"),
    AS("Additional Service");

    private final String code;

    PaymentReason(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PaymentReason fromCode(String code) {
        for (PaymentReason type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}

