package com.example.marketplace.payment.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.marketplace.payment.domain.Payment;

@Mapper
@Repository
public interface PaymentRepository {

	void insertPaymentInfo(Payment payment);
}
