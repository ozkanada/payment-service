package com.eywatech.eywarent.payment.exception.handler;

import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.eywatech.eywarent.payment.enums.FriendlyMessageCodes;
import com.eywatech.eywarent.payment.exception.exceptions.CardInfoNotBlankException;
import com.eywatech.eywarent.payment.exception.exceptions.HgsPaymentException;
import com.eywatech.eywarent.payment.exception.exceptions.PaymentNotFoundException;
import com.eywatech.pandora.dto.FriendlyMessage;
import com.eywatech.pandora.response.InternalApiResponse;
import com.eywatech.pandora.utils.FriendlyMessageUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CardInfoNotBlankException.class)
    public InternalApiResponse<String> handleCardInfoIsNotBlankException(CardInfoNotBlankException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonMap(
                        exception.getFriendlyMessageCode().getFriendlyMessageCodeToString(),
                        exception.getMessage()))
                .build();
    }
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PaymentNotFoundException.class)
    public InternalApiResponse<String> handlePaymentNotFoundException(PaymentNotFoundException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonMap(
                        exception.getFriendlyMessageCode().getFriendlyMessageCodeToString(),
                        exception.getMessage()))
                .build();
    }
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HgsPaymentException.class)
    public InternalApiResponse<String> handleHgsPaymentException(HgsPaymentException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .hasError(true)
                .errorMessages(Collections.singletonMap(
                        exception.getFriendlyMessageCode().getFriendlyMessageCodeToString(),
                        exception.getMessage()))
                .build();
    }
}


