package com.eywatech.eywarent.payment.mapper;

import org.springframework.stereotype.Component;
import com.eywatech.eywarent.payment.dao.PaymentReceivedDAO;
import com.eywatech.eywarent.payment.dto.PaymentReceivedDTO;
import com.eywatech.eywarent.payment.entity.PaymentReceived;
import com.eywatech.pandora.mapper.GenericMapper;

@Component
public class PaymentReceivedMapper extends GenericMapper<PaymentReceived, PaymentReceivedDAO, PaymentReceivedDTO> {
    public PaymentReceivedMapper() {
        super(PaymentReceived.class, PaymentReceivedDAO.class, PaymentReceivedDTO.class);
    }
}