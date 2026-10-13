package com.example.marketplace.product.controller;


import com.example.marketplace.global.constant.SessionKey;
import com.example.marketplace.global.fixture.CartFixture.*;
import com.example.marketplace.global.fixture.UserFixture;
import com.example.marketplace.global.template.ControllerTestTemplate;
import com.example.marketplace.global.util.MultiValueMapConverter;
import com.example.marketplace.product.controller.dto.GetProductsRequest;
import com.example.marketplace.product.controller.dto.SaveToCartRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

import static com.example.marketplace.product.constant.DeliveryTypeEnum.EXPRESS;
import static com.example.marketplace.product.constant.DeliveryTypeEnum.EXPRESS_FRESH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProductControllerTest extends ControllerTestTemplate {

    @DisplayName("Selecting express delivery shows products eligible for express delivery.")
    @Test
    public void getProductsByIsExpress() throws Exception {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(EXPRESS)
                .start(1)
                .listSize(2)
                .build();
        final MultiValueMap<String, String> params = MultiValueMapConverter.convert(objectMapper, dto);

        // when
        final ResultActions actions = mvc.perform(get("/products")
            .params(params))
            .andDo(print());

        // then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Selecting fresh delivery with the express filter returns products eligible for both.")
    @Test
    public void getProductsByIsExpressAndIsExpressFresh() throws Exception {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(EXPRESS_FRESH)
                .express(true)
                .start(1)
                .listSize(2)
                .build();
        final MultiValueMap<String, String> params = MultiValueMapConverter.convert(objectMapper, dto);

        // when
        final ResultActions actions = mvc.perform(get("/products")
                .params(params))
                .andDo(print());

        // then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Searching by keyword returns products containing the keyword.")
    @Test
    public void searchProductsByKeyword() throws Exception {
        // given
        final String keyword = "123";

        // when
        final ResultActions actions = mvc.perform(get("/products/search")
                .param("keyword", keyword))
                .andDo(print());

        // then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Selecting a product and quantity adds it to the cart.")
    @Test
    public void saveProductsToCart() throws Exception {
        // given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionKey.LOGIN_USER_ID, UserFixture.User1.ID);

        final SaveToCartRequest dto = SaveToCartRequest.builder()
            .productId(Cart1.PRODUCT_ID)
            .productNum(Cart1.PRODUCT_NUM)
            .build();

        // when
        final ResultActions actions = mvc.perform(post("/products/cart")
            .session(session)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andDo(print());

        // then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }
}
