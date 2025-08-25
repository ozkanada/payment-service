package com.eywatech.eywarent.payment.dao;

import java.math.BigDecimal;
import java.util.List;

import com.eywatech.eywarent.payment.enums.Currency;
import com.eywatech.eywarent.payment.enums.PaymentReason;
import com.eywatech.eywarent.payment.enums.PaymentType;
import com.eywatech.pandora.dao.BaseDAO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentReceivedDAO extends BaseDAO{
	private String documentNo;
	private PaymentType paymentType;
	private PaymentReason paymentReason;
	private String cardFullname;
	private String cardId;
	private String cardExpiredMont;
	private String cardExpiredYear;
	private String cardCvv;
	private Boolean is3D;
	private BigDecimal amount;
	private Currency currency;
	private List<String> hgsIdList; 
}
