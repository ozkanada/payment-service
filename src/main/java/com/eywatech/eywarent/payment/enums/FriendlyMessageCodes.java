package com.eywatech.eywarent.payment.enums;

import com.eywatech.pandora.exception.IFriendlyMessageCode;

public enum FriendlyMessageCodes implements IFriendlyMessageCode {
    OK(1000),
    ERROR(1001),
    SUCCESS(1002),
    NOT_FOUND_EXCEPTION(1003), 
	PAYMENT_RECEIVED_SUCCESSFULY(1500),
	CARD_INFO_NOT_BLANK_EXCEPTION(1501),
	PAYMENT_NOT_FOUND_EXCEPTION(1502);

    private final int value;

    FriendlyMessageCodes(int value) {
        this.value = value;
    }

    @Override
    public int getFriendlyMessageCode() {
        return value;
    }

    @Override
    public String getFriendlyMessageCodeToString() {
        return String.valueOf(value);
    }
}

