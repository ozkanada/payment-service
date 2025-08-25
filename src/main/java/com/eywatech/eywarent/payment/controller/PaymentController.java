package com.eywatech.eywarent.payment.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eywatech.eywarent.payment.client.HgsClient;
import com.eywatech.eywarent.payment.dao.PaymentReceivedDAO;
import com.eywatech.eywarent.payment.dto.PaymentReceivedDTO;
import com.eywatech.eywarent.payment.enums.FriendlyMessageCodes;
import com.eywatech.eywarent.payment.enums.PaymentReason;
import com.eywatech.eywarent.payment.enums.PaymentType;
import com.eywatech.eywarent.payment.exception.exceptions.CardInfoNotBlankException;
import com.eywatech.eywarent.payment.exception.exceptions.PaymentNotFoundException;
import com.eywatech.eywarent.payment.service.impl.PaymentServiceImpl;
import com.eywatech.pandora.dto.FriendlyMessage;
import com.eywatech.pandora.response.InternalApiResponse;
import com.eywatech.pandora.utils.FriendlyMessageUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentServiceImpl paymentService;

	@PostMapping("/request")
	public InternalApiResponse<PaymentReceivedDTO> createPayment(@RequestBody PaymentReceivedDAO dao,
			HttpServletRequest request) {
		log.debug("[{}][createPayment] -> request: {}", this.getClass().getSimpleName(), dao);
		/*
		 * String ip = request.getRemoteAddr(); if (paymentService.isBlockedIp(ip)) {
		 * return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).
		 * body("Çok fazla istek yaptınız."); }
		 */
		// String htmlForm = paymentService.prepare3DForm(dao);
		//return ResponseEntity.ok(htmlForm);
		if (dao.getPaymentType().equals(PaymentType.SP)) {
			if (dao.getCardFullname().isEmpty() || dao.getCardId().isEmpty() || dao.getCardExpiredMont().isEmpty()
					|| dao.getCardExpiredYear().isEmpty() || dao.getCardCvv().isEmpty()) {
				throw new CardInfoNotBlankException(FriendlyMessageCodes.CARD_INFO_NOT_BLANK_EXCEPTION, "Card info is not blank!");
			}
		}
		if (dao.getPaymentReason().equals(PaymentReason.HGS)) {
			paymentService.payHgs(dao.getHgsIdList());
		}
		PaymentReceivedDTO response =  paymentService.save(dao);
		log.debug("[{}][createPayment] -> response: {}", this.getClass().getSimpleName(), response);
		return InternalApiResponse.<PaymentReceivedDTO>builder()
				.friendlyMessage(FriendlyMessage.builder()
						.title(FriendlyMessageUtils.getFriendlyMessage(FriendlyMessageCodes.SUCCESS))
						.description(FriendlyMessageUtils
								.getFriendlyMessage(FriendlyMessageCodes.PAYMENT_RECEIVED_SUCCESSFULY))
						.build())
				.httpStatus(HttpStatus.OK).hasError(false).payload(response).build();
	}

	@PostMapping("/callback")
	public ResponseEntity<String> paymentCallback(@RequestParam Map<String, String> params) {
		try {
			boolean isValid = paymentService.verify3DResponse(params);

			if (!isValid) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Geçersiz MAC");
			}

			// Ödeme durum kontrolü
			if ("Approved".equalsIgnoreCase(params.get("Response"))) {
				// Ödeme başarılı
				return ResponseEntity.ok("Ödeme başarılı");
			} else {
				// Ödeme başarısız
				return ResponseEntity.ok("Ödeme başarısız");
			}
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sunucu hatası");
		}
	}
	
	@GetMapping("/getById/{paymentId}")
	public InternalApiResponse<PaymentReceivedDTO> getPaymentById(@PathVariable("paymentId") String paymentId){
		log.debug("[{}][getPaymentById] -> request: {}", this.getClass().getSimpleName(), paymentId);
		Optional<PaymentReceivedDTO> responseOptional = paymentService.findById(paymentId);
		if(responseOptional.isEmpty())
			throw new PaymentNotFoundException(FriendlyMessageCodes.PAYMENT_NOT_FOUND_EXCEPTION,"Payment not found. PaymentId: " + paymentId);
		log.debug("[{}][getPaymentById] -> response: {}", this.getClass().getSimpleName(), responseOptional.get());
		return InternalApiResponse.<PaymentReceivedDTO>builder() 
				.httpStatus(HttpStatus.OK).hasError(false).payload(responseOptional.get()).build();
	}
	
	@GetMapping("/getAllPaymentsByDocumentNo/{documentNo}")
	public InternalApiResponse<Page<PaymentReceivedDTO>> getAllPaymentsByDocumentNo(@PathVariable("documentNo") String documentNo, Pageable pageable){
		log.debug("[{}][getAllPaymentsByDocumentNo] -> request: {}", this.getClass().getSimpleName(), documentNo);
		Page<PaymentReceivedDTO> response = paymentService.getAllByDocumentNo(documentNo, pageable);
		log.debug("[{}][getAllPaymentsByDocumentNo] -> response: {}", this.getClass().getSimpleName(), response);
		return InternalApiResponse.<Page<PaymentReceivedDTO>>builder() 
				.httpStatus(HttpStatus.OK).hasError(false).payload(response).build();
	}

}
