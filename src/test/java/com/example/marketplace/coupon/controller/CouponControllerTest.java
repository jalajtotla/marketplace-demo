package com.example.marketplace.coupon.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import com.example.marketplace.global.constant.SessionKey;
import com.example.marketplace.global.fixture.CouponFixture.*;
import com.example.marketplace.global.fixture.UserFixture.*;
import com.example.marketplace.global.template.ControllerTestTemplate;
import com.example.marketplace.user.controller.dto.SignUpRequestDto;

public class CouponControllerTest extends ControllerTestTemplate {

	@DisplayName("Shows the list of available coupons.")
	@Test
	public void getAvailableCoupons() throws Exception{
		//when
		final ResultActions actions = mvc.perform(get("/available-coupons"))
			.andDo(print());

		//then
		actions
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("User saves an available coupon.")
	@Test
	public void saveAvailableCoupons() throws Exception{
		// given
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(SessionKey.LOGIN_USER_ID, User1.ID);

		final SignUpRequestDto joinDto = SignUpRequestDto.builder()
			.name(User1.NAME)
			.email(User1.EMAIL)
			.password(User1.PASSWORD)
			.phone(User1.PHONE)
			.build();

		mvc.perform(post("/users/sign-up")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(joinDto)));

		//when
		final ResultActions actions = mvc.perform(post("/available-coupons/{id}", Coupon2.ID)
			.session(session)
			.content(objectMapper.writeValueAsString(Coupon2.COUPON))
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print());

		//then
		actions
			.andExpect(status().isOk())
			.andDo(print());
	}
}