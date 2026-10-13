package com.example.marketplace.payment.service;

import org.springframework.stereotype.Service;

import com.example.marketplace.payment.controller.dto.PaymentDto;
import com.example.marketplace.payment.domain.Payment;
import com.example.marketplace.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentService {

	private final PaymentRepository paymentRepository;

	public void pay(PaymentDto paymentDto){
		if(!mockPayment(paymentDto))
			throw new IllegalArgumentException("Payment failed.");
	}

	public long savePaymentInfo(Payment payment){
		paymentRepository.insertPaymentInfo(payment);
		return payment.getId();
	}

	public boolean mockPayment(PaymentDto dto){
		return true;
	}
}
