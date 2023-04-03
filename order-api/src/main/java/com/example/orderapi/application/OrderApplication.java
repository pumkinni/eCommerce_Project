package com.example.orderapi.application;

import static com.example.orderapi.exception.ErrorCode.ORDER_FAIL_CHECK_CART;
import static com.example.orderapi.exception.ErrorCode.ORDER_FAIL_NO_MONEY;

import com.example.orderapi.client.UserClient;
import com.example.orderapi.client.user.ChangeBalanceForm;
import com.example.orderapi.client.user.CustomerDto;
import com.example.orderapi.domain.redis.Cart;
import com.example.orderapi.exception.CustomException;
import com.example.orderapi.service.ProductItemService;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderApplication {

    private final CartApplication cartApplication;
    private final UserClient userClient;
    private final ProductItemService productItemService;

    @Transactional
    public void order(String token, Cart cart) {
        // 1번 : 주문 시 기존 카트 버림
        // 2번 : 선택 주문 - 내가 사지 않은 아이템을 살려야 함(나중에 구현하기)
        Cart orderCart = cartApplication.refreshCart(cart);

        if (orderCart.getMessages().size() > 0) {
            throw new CustomException(ORDER_FAIL_CHECK_CART);
        }

        CustomerDto customerDto = userClient.getCustomerInfo(token).getBody();

        int totalPrice = getTotalPrice(cart);

        if (customerDto.getBalance() < totalPrice){
            throw new CustomException(ORDER_FAIL_NO_MONEY);
        }

        // 롤백 추가 구현 필요
        userClient.changeBalance(token, ChangeBalanceForm.builder()
                .from("USER")
                .message("Order")
                .money(-totalPrice)
                .build());

        for (Cart.Product product : orderCart.getProducts()){
            for (Cart.ProductItem cartItem : product.getItems()){
                com.example.orderapi.domain.model.ProductItem productItem = productItemService.getProductItem(cartItem.getId());

                productItem.setCount(productItem.getCount() - cartItem.getCount());
            }
        }
    }

    public Integer getTotalPrice(Cart cart) {
        return cart.getProducts().stream().flatMapToInt(product ->
            product.getItems().stream().flatMapToInt(productItem -> IntStream.of(
                productItem.getPrice() * productItem.getCount()))).sum();
    }

    // 결제를 위해 필요한 것
    // 1번 : 물건들이 전부 주문 가능한 상태인지 확인
    // 2번 : 가격 변동이 있었는지에 대해 확인
    // 3번 : 고객의 돈이 충분한지
    // 4번 : 결제 & 상품의 재고 확인

}
