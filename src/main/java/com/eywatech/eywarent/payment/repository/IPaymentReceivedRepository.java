package com.eywatech.eywarent.payment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eywatech.eywarent.payment.dto.PaymentReceivedDTO;
import com.eywatech.eywarent.payment.entity.PaymentReceived;
import com.eywatech.pandora.repository.BaseRepository;

public interface IPaymentReceivedRepository  extends BaseRepository<PaymentReceived, String> {

	Page<PaymentReceived> findAllByDocumentNo(String documentNo, Pageable pageable);

}
