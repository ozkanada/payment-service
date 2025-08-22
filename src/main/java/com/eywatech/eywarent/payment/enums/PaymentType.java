package com.eywatech.eywarent.payment.enums;

public enum PaymentType {
    SP("Sanal Pos"),
    FP("Fiziksel Pos"),
    FPD("Fiziksel Pos Depozito"),
    N("Nakit"),
	ND("Nakit Depozito");

    private final String code;

    PaymentType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PaymentType fromCode(String code) {
        for (PaymentType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}

