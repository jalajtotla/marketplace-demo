package com.example.marketplace.cart.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.marketplace.cart.domain.Cart;
import com.example.marketplace.cart.repository.CartRepository;
import com.example.marketplace.product.controller.dto.SaveToCartRequest;
import com.example.marketplace.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {

	private final CartRepository cartRepository;
	private final ProductService productService;

	@Transactional
	public void saveProduct(long userId, SaveToCartRequest dto){
		if(!productService.checkIsProductExist(dto.getProductId()))
			throw new IllegalArgumentException("Product does not exist.");
		if(dto.getProductNum() == 0)
			throw new IllegalArgumentException("Quantity must be at least 1.");

		Cart cart = Cart.builder()
			.productId(dto.getProductId())
			.productNum(dto.getProductNum())
			.userId(userId)
			.build();
		if(!checkIsProductExistInCart(cart))
			cartRepository.insertProduct(cart);
		else {
			int savedNum = getSavedProductNum(userId, dto.getProductId());
			cartRepository.updateProductNum(dto.getProductId(), savedNum + dto.getProductNum());
		}
	}

	public boolean checkIsProductExistInCart(Cart cart){
		return cartRepository.findByProductId(cart.getUserId(), cart.getProductId()).isPresent();
	}

	public int getSavedProductNum(long userId, long productId){
		Optional<Cart> product = cartRepository.findByProductId(userId, productId);
		return product.get().getProductNum();
	}
}