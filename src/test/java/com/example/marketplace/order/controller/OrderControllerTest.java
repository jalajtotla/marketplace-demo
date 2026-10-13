package com.example.marketplace.order.controller;

import com.example.marketplace.global.constant.SessionKey;
import com.example.marketplace.global.fixture.PaymentFixture.*;
import com.example.marketplace.global.fixture.UserFixture.*;
import com.example.marketplace.global.template.ControllerTestTemplate;
import com.example.marketplace.order.controller.dto.OrderRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends ControllerTestTemplate {

    @DisplayName("User places an order for products in the cart.")
    @Test
    public void order() throws Exception{
        // given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionKey.LOGIN_USER_ID, User1.ID);

        final OrderRequestDto dto = OrderRequestDto.builder()
                .receiverName(User1.NAME)
                .receiverPhone(User1.PHONE)
                .type(Payment1.TYPE)
                .receiverRequest("Leave at the door")
                .build();

        //when
        final ResultActions actions = mvc.perform(post("/orders")
                        .session(session)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print());

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }
}
