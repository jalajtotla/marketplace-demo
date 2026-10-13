package com.example.marketplace.order.service;

import com.example.marketplace.address.repository.AddressRepository;
import com.example.marketplace.cart.repository.CartRepository;
import com.example.marketplace.global.fixture.AddressFixture.*;
import com.example.marketplace.global.fixture.CartFixture.*;
import com.example.marketplace.global.fixture.PaymentFixture.*;
import com.example.marketplace.global.fixture.ProductFixture.*;
import com.example.marketplace.global.fixture.UserFixture.*;
import com.example.marketplace.order.controller.dto.OrderRequestDto;
import com.example.marketplace.payment.service.PaymentService;
import com.example.marketplace.product.domain.Product;
import com.example.marketplace.product.repository.ProductRepository;
import com.example.marketplace.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ProductRepository productRepository;

    // TODO: rewrite order tests
    @DisplayName("Orders the products in the cart.")
    @Test
    public void order() {
//        //given
//        final Optional<Product> FoundProduct = Optional.of(Product1.PRODUCT);
//        final OrderRequestDto dto = OrderRequestDto.builder()
//                .type(Payment1.TYPE)
//                .receiverName(User1.NAME)
//                .receiverPhone(User1.PHONE)
//                .receiverRequest("")
//                .build();
//        given(cartRepository.findByUserId(User1.ID)).willReturn(Arrays.asList(Cart1.CART));
//        given(userRepository.findById(User1.ID)).willReturn(User1.USER);
//        given(addressRepository.findMainContentByUserId(User1.ID)).willReturn(Address1.CONTENT);
//        given(productRepository.findByProductId(Product1.ID)).willReturn(FoundProduct);
//        //when
//        orderService.order(User1.ID, dto);
//        //then
//        then(cartRepository).should(times(1)).deleteCartProducts(Cart1.CART);
    }

    @DisplayName("Fails to order when no products are requested.")
    @Test
    public void NoProductToOrder() {
        //given
        final OrderRequestDto dto = OrderRequestDto.builder()
                .type(Payment1.TYPE)
                .receiverName(User1.NAME)
                .receiverPhone(User1.PHONE)
                .receiverRequest("")
                .build();
        given(cartRepository.findByUserId(User1.ID)).willReturn(Arrays.asList());
        //then
        assertThrows(IllegalArgumentException.class, () -> orderService.order(User1.ID, dto));
    }
}
