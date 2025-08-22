package com.eywatech.eywarent.payment.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.eywatech.eywarent.payment.MacUtils;
import com.eywatech.eywarent.payment.dao.PaymentReceivedDAO;
import com.eywatech.eywarent.payment.dto.PaymentReceivedDTO;
import com.eywatech.eywarent.payment.entity.PaymentReceived;
import com.eywatech.eywarent.payment.mapper.PaymentReceivedMapper;
import com.eywatech.eywarent.payment.repository.IPaymentReceivedRepository;
import com.eywatech.pandora.service.impl.BaseServiceImpl;

@Service
public class PaymentServiceImpl extends
		BaseServiceImpl<PaymentReceived, String, PaymentReceivedDAO, PaymentReceivedDTO, PaymentReceivedMapper, IPaymentReceivedRepository> {

	public PaymentServiceImpl(IPaymentReceivedRepository repository, PaymentReceivedMapper mapper) {
		super(repository, mapper);
	}
	
	@Override
	protected String getIdFromDao(PaymentReceivedDAO dao) {
		return dao.getId();
	}
	/*
	 * private final RedisTemplate<String, Object> redisTemplate;
	 * 
	 * public boolean isBlockedIp(String ip) { String key = "req:ip:" + ip; Long
	 * count = redisTemplate.opsForValue().increment(key); redisTemplate.expire(key,
	 * Duration.ofMinutes(1)); return count != null && count > 5; }
	 */

	public String prepare3DForm(PaymentReceivedDAO dao) {
		// İş Bankası 3D endpoint’i (test ortamı için)
		String endpoint = "https://entegrasyon.asseco-see.com.tr/fim/est3Dgate";

		return "<form method='post' action='" + endpoint + "'>" + "<input type='hidden' name='clientid' value='XXXX'/>"
				+ "<input type='hidden' name='amount' value='" + dao.getAmount() + "'/>"
				+ "<input type='hidden' name='oid' value='" + dao.getId() + "'/>"
				+ "<input type='hidden' name='okUrl' value='https://seninsite.com/success'/>"
				+ "<input type='hidden' name='failUrl' value='https://seninsite.com/fail'/>"
				+ "<input type='submit' value='Banka Sayfasına Git'/>" + "</form>";
	}

	public boolean verify3DResponse(Map<String, String> params) throws NoSuchAlgorithmException {
		// MAC doğrulama vs. burada yapılır
		// return "Approved".equalsIgnoreCase(params.get("Response"));
		String storeKey = "BANKADAN_ALDIGİNIZ_STORE_KEY";
		boolean validMac = MacUtils.verifyMac(params, storeKey);
		return validMac;
	}

	public Page<PaymentReceivedDTO> getAllByDocumentNo(String documentNo, Pageable pageable) {
		return repository.findAllByDocumentNo(documentNo, pageable).map(mapper::toDto);
	}
}
