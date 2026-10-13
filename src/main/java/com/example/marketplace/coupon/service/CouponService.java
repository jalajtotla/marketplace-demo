package com.example.marketplace.coupon.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.marketplace.cart.domain.Cart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.marketplace.coupon.domain.Coupon;
import com.example.marketplace.coupon.domain.UserCoupon;
import com.example.marketplace.coupon.repository.CouponRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Service
@Slf4j
public class CouponService {

	private final CouponRepository couponRepository;

	public List<Coupon> getAvailableCoupons(){
		List<Coupon> coupons = couponRepository.getAllCoupons();

		List<Coupon> availableCoupons = coupons.stream()
				.filter(coupon -> coupon.getExpirationTime().isAfter(ZonedDateTime.now()))
				.collect(Collectors.toList());

		return availableCoupons;
	}

	@Transactional
	public void saveCoupon(long id, long userId){
		if(!checkIsAvailableCoupon(id))
			throw new IllegalArgumentException("Coupon is not available.");
		int maxCouponCount = couponRepository.getMaxCouponCount(id);
		int issuedCouponCount = 0;
		if(checkIsAlreadyHave(id, userId))
			issuedCouponCount = couponRepository.getIssuedCount(id, userId);

		if(maxCouponCount == issuedCouponCount)
			throw new IllegalArgumentException("Coupon can no longer be issued.");

		if(!checkIsAlreadyHave(id, userId)){
			UserCoupon userCoupon = UserCoupon.builder()
				.userId(userId)
				.couponId(id)
				.issuedCount(issuedCouponCount+1)
				.build();
			couponRepository.insertUserCoupon(userCoupon);
		}
		else
			couponRepository.updateIssuedCount(id, userId, issuedCouponCount+1);
	}

	public boolean checkIsAvailableCoupon(long id){
		ZonedDateTime expirationTime = couponRepository.findCouponById(id).get().getExpirationTime();
		return expirationTime.isAfter(ZonedDateTime.now());
	}

	public boolean checkIsAlreadyHave(long couponId, long userId) {
		return couponRepository.findUserCouponById(couponId, userId).isPresent();
	}

	public long getDiscountPriceByCoupon(long userId, Optional<Long> couponId, List<Cart> cartProducts, long totalProductPrice){
		if(couponId.isEmpty())
			return 0;
		if(!checkIsAlreadyHave(couponId.get(), userId))
			throw new IllegalArgumentException("Coupon has not been issued.");
		if(!checkIsAvailableCoupon(couponId.get()))
			throw new IllegalArgumentException("Coupon has expired.");

		Optional<Coupon> coupon = couponRepository.findCouponById(couponId.get());
		List<Long> productsId = cartProducts.stream()
				.map(cartProduct -> cartProduct.getProductId())
				.collect(Collectors.toList());
		if(!productsId.contains(coupon.get().getProductId()))
			throw new IllegalArgumentException("Coupon does not apply to this product.");
		if(coupon.get().getMinPrice() > totalProductPrice)
			throw new IllegalArgumentException("Minimum order amount not met.");

		Optional<UserCoupon> userCoupon = couponRepository.findUserCouponById(couponId.get(), userId);
		if(userCoupon.get().getIssuedCount() - userCoupon.get().getUseCount() <= 0)
			throw new IllegalArgumentException("Coupon has already been used.");

		return coupon.get().getDiscountPrice();
	}

	public void increaseUseCount(long userId, long couponId){
		Optional<UserCoupon> userCoupon = couponRepository.findUserCouponById(couponId, userId);
		couponRepository.updateUseCount(userId, couponId, userCoupon.get().getUseCount()+1);
	}
}
