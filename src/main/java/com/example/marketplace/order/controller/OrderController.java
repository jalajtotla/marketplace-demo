package com.example.marketplace.order.controller;

import com.example.marketplace.order.domain.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplace.auth.AuthRequired;
import com.example.marketplace.global.common.StatusEnum;
import com.example.marketplace.global.common.SuccessResponse;
import com.example.marketplace.order.controller.dto.OrderRequestDto;
import com.example.marketplace.order.service.OrderService;
import com.example.marketplace.user.service.LoginService;

import java.util.List;

@RestController
public class OrderController {

	private final OrderService orderService;
	private final LoginService loginService;

	public OrderController(OrderService orderService, @Qualifier("userSessionLoginService")LoginService loginService){
		this.orderService = orderService;
		this.loginService = loginService;
	}

// TODO: [GET]/orders rewrite
//	@Transactional(readOnly = true)
//	@AuthRequired
//	@GetMapping("/orders")
//	public SuccessResponse GetOrderInfo() {
//		long userId = loginService.getLoginUserId();
//		List<Order> orderBills = orderService.getOrderInfo(userId);
//		SuccessResponse res = SuccessResponse.builder()
//			.status(StatusEnum.CREATED)
//			.data(orderBills)
//			.message("Fetched order information")
//			.build();
//		return res;
//	}

	@Transactional
	@AuthRequired
	@PostMapping("/orders")
	public SuccessResponse order(@RequestBody OrderRequestDto orderRequestDto) {
		long userId = loginService.getLoginUserId();
		orderService.order(userId, orderRequestDto);
		SuccessResponse res = SuccessResponse.builder()
				.status(StatusEnum.CREATED)
				.message("Order placed")
				.build();

		return res;
	}
}