package com.example.marketplace.product.service;


import com.example.marketplace.global.fixture.ProductFixture.*;
import com.example.marketplace.product.controller.dto.GetProductsRequest;
import com.example.marketplace.product.domain.Product;
import com.example.marketplace.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.example.marketplace.product.constant.DeliveryTypeEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("Fetches products eligible for express delivery.")
    @Test
    public void getProductsByIsExpress() {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(EXPRESS)
                .start(1)
                .listSize(10)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).getProductsByIsExpress(any(Boolean.class), any(Integer.class), any(Integer.class));
    }

    @DisplayName("Fetches products eligible for fresh delivery.")
    @Test
    public void getProductsByIsExpressFresh() {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(EXPRESS_FRESH)
                .start(1)
                .listSize(10)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).getProductsByIsExpressFresh(any(Boolean.class), any(Integer.class), any(Integer.class));
    }

    @DisplayName("Fetches products eligible for global delivery.")
    @Test
    public void getProductsByIsExpressGlobal() {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(EXPRESS_GLOBAL)
                .start(1)
                .listSize(10)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).getProductsByIsExpressGlobal(any(Boolean.class), any(Integer.class), any(Integer.class));
    }

    @DisplayName("Fetches products eligible for both fresh and express delivery.")
    @Test
    public void getProductsByIsExpressAndIsExpressFresh() {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(EXPRESS_FRESH)
                .express(true)
                .start(1)
                .listSize(10)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).getProductsByIsExpressAndIsExpressFresh(any(Boolean.class), any(Boolean.class), any(Integer.class), any(Integer.class));
    }

    @DisplayName("Fetches products eligible for both global and express delivery.")
    @Test
    public void getProductsByIsExpressAndIsExpressGlobal() {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(EXPRESS_GLOBAL)
                .express(true)
                .start(1)
                .listSize(10)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).getProductsByIsExpressAndIsExpressGlobal(any(Boolean.class), any(Boolean.class), any(Integer.class), any(Integer.class));
    }

    @DisplayName("Fetches products containing the keyword.")
    @Test
    public void searchProductsByKeyword() {
        // given
        final String keyword = "1";
        final List<Product> products = Arrays.asList(Product1.PRODUCT);
        given(productRepository.getProductsByKeyword(keyword))
                .willReturn(products);

        // then
        assertThat(productRepository.getProductsByKeyword(keyword)).isEqualTo(products);
    }
}
