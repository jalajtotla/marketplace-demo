package com.example.marketplace.cart.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.marketplace.cart.domain.Cart;
import com.example.marketplace.cart.repository.CartRepository;
import com.example.marketplace.global.fixture.CartFixture.*;
import com.example.marketplace.global.fixture.ProductFixture.*;
import com.example.marketplace.global.fixture.UserFixture.*;
import com.example.marketplace.product.controller.dto.SaveToCartRequest;
import com.example.marketplace.product.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
	
	@InjectMocks
	private CartService cartService;

	@Mock
	private ProductService productService;
	
	@Mock
	private CartRepository cartRepository;

	@DisplayName("Adding an existing product to the cart for the first time succeeds.")
	@Test
	void saveProduct() {
		// given
		final SaveToCartRequest dto = SaveToCartRequest.builder()
			.productId(Cart1.PRODUCT_ID)
			.productNum(Cart1.PRODUCT_NUM)
			.build();
		final Optional<Cart> notFoundInCart = Optional.ofNullable(null);

		given(productService.checkIsProductExist(Product1.ID)).willReturn(true);
		given(cartRepository.findByProductId(User1.ID, Cart1.PRODUCT_ID)).willReturn(notFoundInCart);

		// when
		cartService.saveProduct(User1.ID, dto);

		// then
		then(cartRepository).should(times(1)).insertProduct(any());
	}

	@DisplayName("Adding a product already in the cart succeeds.")
	@Test
	void saveProductWithDuplicateItem() {
		// given
		final SaveToCartRequest dto = SaveToCartRequest.builder()
			.productId(Cart1.PRODUCT_ID)
			.productNum(Cart1.PRODUCT_NUM)
			.build();
		final Optional<Cart> cart = Optional.of(Cart1.CART);

		given(productService.checkIsProductExist(Product1.ID)).willReturn(true);
		given(cartRepository.findByProductId(User1.ID, Cart1.PRODUCT_ID)).willReturn(cart);

		// when
		cartService.saveProduct(User1.ID, dto);

		// then
		then(cartRepository).should(times(1)).updateProductNum(Product1.ID, 2);
	}

	@DisplayName("Adding a non-existent product to the cart fails.")
	@Test
	void saveProductDoesNotExist() {
		// given
		final SaveToCartRequest dto = SaveToCartRequest.builder()
			.productId(Product2.ID)
			.productNum(1)
			.build();
		given(productService.checkIsProductExist(Product2.ID)).willReturn(false);

		// then
		assertThrows(IllegalArgumentException.class, () -> cartService.saveProduct(User1.ID, dto));
	}

	@DisplayName("Adding a quantity of zero to the cart fails.")
	@Test
	void saveProductZero() {
		// given
		final SaveToCartRequest dto = SaveToCartRequest.builder()
			.productId(Cart1.PRODUCT_ID)
			.productNum(0)
			.build();
		given(productService.checkIsProductExist(Product1.ID)).willReturn(true);

		// then
		assertThrows(IllegalArgumentException.class, () -> cartService.saveProduct(User1.ID, dto));
	}
}
