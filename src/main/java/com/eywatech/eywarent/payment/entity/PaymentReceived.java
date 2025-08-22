package com.eywatech.eywarent.payment.entity;

import java.math.BigDecimal;
import com.eywatech.eywarent.payment.enums.Currency;
import com.eywatech.eywarent.payment.enums.PaymentReason;
import com.eywatech.eywarent.payment.enums.PaymentType;
import com.eywatech.pandora.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_received")
public class PaymentReceived extends BaseEntity{
	private String documentNo;
	private PaymentType paymentType;
	private PaymentReason paymentReason;
	private String cardFullname;
	private String cardId;
	private String cardExpiredMont;
	private String cardExpiredYear;
	private Boolean is3D;
	private BigDecimal amount;
	private Currency currency;
}
