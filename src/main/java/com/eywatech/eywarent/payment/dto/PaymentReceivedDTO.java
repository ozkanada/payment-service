package com.eywatech.eywarent.payment.dto;

import java.math.BigDecimal;
import com.eywatech.eywarent.payment.enums.Currency;
import com.eywatech.eywarent.payment.enums.PaymentReason;
import com.eywatech.eywarent.payment.enums.PaymentType;
import com.eywatech.pandora.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentReceivedDTO extends BaseDTO{
	private String documentNo;
	private PaymentType paymentType;
	private PaymentReason paymentReason; 
	private Boolean is3D;
	private BigDecimal amount;
	private Currency currency;
}
