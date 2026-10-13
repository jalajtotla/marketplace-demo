package com.example.marketplace.coupon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplace.auth.AuthRequired;
import com.example.marketplace.coupon.domain.Coupon;
import com.example.marketplace.coupon.service.CouponService;
import com.example.marketplace.global.common.StatusEnum;
import com.example.marketplace.global.common.SuccessResponse;
import com.example.marketplace.user.service.LoginService;

@RestController
public class CouponController {

	private final CouponService couponService;
	private final LoginService loginService;

	public CouponController(CouponService couponService, @Qualifier("userSessionLoginService") LoginService loginService){
		this.couponService = couponService;
		this.loginService = loginService;
	}

	@GetMapping("/available-coupons")
	public SuccessResponse getAvailableCoupons(){
		List<Coupon> coupons = couponService.getAvailableCoupons();
		SuccessResponse res = SuccessResponse.builder()
			.status(StatusEnum.OK)
			.message("Fetched available coupons")
			.data(coupons)
			.build();
		return res;
	}

	@AuthRequired
	@PostMapping("/available-coupons/{id}")
	public SuccessResponse saveCoupon(@PathVariable("id") final Long id){
		Long userId = loginService.getLoginUserId();
		couponService.saveCoupon(id, userId);
		SuccessResponse res = SuccessResponse.builder()
			.status(StatusEnum.CREATED)
			.message("Coupon saved")
			.build();
		return res;
	}
}
