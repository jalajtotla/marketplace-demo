package com.example.marketplace.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.Optional;

import com.example.marketplace.coupon.domain.Coupon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.marketplace.coupon.domain.UserCoupon;
import com.example.marketplace.coupon.repository.CouponRepository;
import com.example.marketplace.global.fixture.CouponFixture.*;
import com.example.marketplace.global.fixture.UserCouponFixture.*;
import com.example.marketplace.global.fixture.UserFixture.*;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {

	@InjectMocks
	private CouponService couponService;

	@Mock
	private CouponRepository couponRepository;

	@DisplayName("Fetches all coupons that have not expired.")
	@Test
	public void getCouponsBeforeExpirationTime() {
		//given
		given(couponRepository.getAllCoupons())
			.willReturn(Arrays.asList(Coupon1.COUPON, Coupon2.COUPON, Coupon3.COUPON));

		//then
		assertThat(couponService.getAvailableCoupons().size()).isEqualTo(2);
	}

	@DisplayName("Saving succeeds for a coupon that has not expired.")
	@Test
	public void saveCouponBeforeExpirationTime() {
		//given
		final Optional<Coupon> FoundCoupon = Optional.ofNullable(Coupon1.COUPON);
		given(couponRepository.findCouponById(Coupon1.ID)).willReturn(FoundCoupon);
		given(couponRepository.getMaxCouponCount(Coupon1.ID)).willReturn(Coupon1.MAX_COUPON_COUNT);

		final Optional<UserCoupon> notFoundUserCoupon = Optional.ofNullable(null);
		given(couponRepository.findUserCouponById(Coupon1.ID, User1.ID)).willReturn(notFoundUserCoupon);

		//when
		couponService.saveCoupon(Coupon1.ID, User1.ID);

		//then
		then(couponRepository).should(times(1)).insertUserCoupon(any());
	}

	@DisplayName("Saving fails for an expired coupon.")
	@Test
	public void saveCouponAfterExpirationTime() {
		//given
		final Optional<Coupon> FoundCoupon = Optional.ofNullable(Coupon3.COUPON);
		given(couponRepository.findCouponById(Coupon3.ID)).willReturn(FoundCoupon);

		//then
		assertThrows(IllegalArgumentException.class, () -> couponService.saveCoupon(Coupon3.ID, User1.ID));
	}

	@DisplayName("Saving succeeds for an existing coupon that can still be issued.")
	@Test
	public void saveCouponAlreadyIssuedCoupon() {
		//given
		final Optional<UserCoupon> FoundUserCoupon = Optional.ofNullable(UserCoupon1.USERCOUPON);
		given(couponRepository.findUserCouponById(Coupon2.ID, User1.ID)).willReturn(FoundUserCoupon);

		final Optional<Coupon> FoundCoupon = Optional.ofNullable(Coupon2.COUPON);
		given(couponRepository.findCouponById(Coupon2.ID)).willReturn(FoundCoupon);
		given(couponRepository.getMaxCouponCount(Coupon2.ID)).willReturn(Coupon2.MAX_COUPON_COUNT);
		given(couponRepository.getIssuedCount(Coupon2.ID, User1.ID)).willReturn(UserCoupon1.ISSUED_COUNT);

		//when
		couponService.saveCoupon(Coupon2.ID, User1.ID);

		//then
		then(couponRepository).should(times(1)).updateIssuedCount(Coupon2.ID, User1.ID, UserCoupon1.ISSUED_COUNT+1);
	}

	@DisplayName("Saving fails when the maximum number of coupons has been issued.")
	@Test
	public void saveCouponAlreadyIssuedMaxCouponCount() {
		//given
		final Optional<UserCoupon> FoundUserCoupon = Optional.ofNullable(UserCoupon2.USERCOUPON);
		given(couponRepository.findUserCouponById(Coupon2.ID, User2.ID)).willReturn(FoundUserCoupon);

		final Optional<Coupon> FoundCoupon = Optional.ofNullable(Coupon2.COUPON);
		given(couponRepository.findCouponById(Coupon2.ID)).willReturn(FoundCoupon);
		given(couponRepository.getMaxCouponCount(Coupon2.ID)).willReturn(Coupon2.MAX_COUPON_COUNT);
		given(couponRepository.getIssuedCount(Coupon2.ID, User2.ID)).willReturn(UserCoupon2.ISSUED_COUNT);

		//then
		assertThrows(IllegalArgumentException.class, () -> couponService.saveCoupon(Coupon2.ID, User2.ID));
	}
}