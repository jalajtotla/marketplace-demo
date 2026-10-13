package com.example.marketplace.product.controller;

import com.example.marketplace.cart.service.CartService;
import com.example.marketplace.global.common.StatusEnum;
import com.example.marketplace.global.common.SuccessResponse;
import com.example.marketplace.global.constant.CacheKey;
import com.example.marketplace.product.controller.dto.GetProductsRequest;
import com.example.marketplace.product.controller.dto.SimpleProduct;
import com.example.marketplace.product.service.ProductService;
import com.example.marketplace.user.service.LoginService;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.List;


@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;
    private final LoginService loginService;

    public ProductController(ProductService productService, CartService cartService, @Qualifier("userSessionLoginService") LoginService loginService){
        this.productService = productService;
        this.cartService = cartService;
        this.loginService = loginService;
    }

    @Cacheable(key="#dto.start", value= CacheKey.PRODUCTS)
    @GetMapping
    public SuccessResponse getProducts(@Valid @ModelAttribute GetProductsRequest dto) {
        List<SimpleProduct> products = productService.getProducts(dto);
        return SuccessResponse.builder()
                .status(StatusEnum.OK)
                .message("Fetched products")
                .data(products)
                .build();
    }

    @GetMapping("/search")
    public SuccessResponse searchProductsByKeyword (@NotBlank @RequestParam String keyword) {
        List<SimpleProduct> products = productService.searchProductsByKeyword(keyword);
        return SuccessResponse.builder()
                .status(StatusEnum.OK)
                .message("Product search successful")
                .data(products)
                .build();
    }
}