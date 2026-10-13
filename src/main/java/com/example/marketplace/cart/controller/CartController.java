package com.example.marketplace.cart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketplace.auth.AuthRequired;
import com.example.marketplace.cart.service.CartService;
import com.example.marketplace.global.common.StatusEnum;
import com.example.marketplace.global.common.SuccessResponse;
import com.example.marketplace.product.controller.dto.SaveToCartRequest;
import com.example.marketplace.user.service.LoginService;

@RestController
public class CartController {

	private final CartService cartService;
	private final LoginService loginService;

	public CartController(CartService cartService, @Qualifier("userSessionLoginService") LoginService loginService){
		this.cartService = cartService;
		this.loginService = loginService;
	}

	@AuthRequired
	@PostMapping("/carts")
	public SuccessResponse saveProductsToCart(@Valid @RequestBody final SaveToCartRequest dto){
		long userId = loginService.getLoginUserId();
		cartService.saveProduct(userId, dto);
		return SuccessResponse.builder()
			.status(StatusEnum.OK)
			.message("Product added to cart")
			.build();
	}
}
