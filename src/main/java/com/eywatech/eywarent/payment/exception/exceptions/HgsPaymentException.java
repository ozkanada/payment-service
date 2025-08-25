package com.eywatech.eywarent.payment.exception.exceptions;

import com.eywatech.pandora.exception.IFriendlyMessageCode;
import com.eywatech.pandora.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class HgsPaymentException extends RuntimeException {
    private static final long serialVersionUID = 45736768212439L;
    private final IFriendlyMessageCode friendlyMessageCode;

    public HgsPaymentException(IFriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(friendlyMessageCode));
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[HgsPaymentException] -> message {} developer message {}", FriendlyMessageUtils.getFriendlyMessage(friendlyMessageCode), message);
    }
}
