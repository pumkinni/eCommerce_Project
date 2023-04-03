package com.example.orderapi.service;

import com.example.orderapi.client.RedisClient;
import com.example.orderapi.domain.product.AddProductCartForm;
import com.example.orderapi.domain.redis.Cart;
import com.example.orderapi.domain.redis.Cart.ProductItem;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final RedisClient redisClient;

    public Cart putCart(Long customerId, Cart cart){
        redisClient.put(customerId, cart);
        return cart;
    }

    public Cart getCart(Long customerId){
        Cart cart = redisClient.get(customerId, Cart.class);
        return cart!=null ? cart : new Cart();
    }


    public Cart addCart(Long customerId, AddProductCartForm form) {
        Cart cart = redisClient.get(customerId, Cart.class);
        if (cart == null) {
            cart = new Cart();
            cart.setCustomerId(customerId);
        }

        // 이전에 같은 상품이 있나
        Optional<Cart.Product> productOptional = cart.getProducts().stream()
            .filter(product -> product.getId().equals(form.getId()))
            .findFirst();

        if (productOptional.isPresent()) {
            Cart.Product redisProduct = productOptional.get();
            // 요청한 아이템
            List<ProductItem> items = form.getItems().stream().map(Cart.ProductItem::from)
                .collect(Collectors.toList());
            //redis 아이템
            Map<Long, ProductItem> redisItemMap = redisProduct.getItems().stream()
                .collect(Collectors.toMap(it -> it.getId(), it -> it));

            // 기존의 장바구니 상품의 이름과 폼의 이름이 같을 때
            if (!redisProduct.getName().equals(form.getName())) {
                cart.addMessage(redisProduct.getName()
                    + "의 정보가 변경되었습니다. 확인 부탁 드립니다.");
            }

            for (Cart.ProductItem item : items) {
                Cart.ProductItem redisItem = redisItemMap.get(item.getId());

                // 가져온 아이템이 장바구니에 없다면 추가
                if (redisItem == null) {
                    redisProduct.getItems().add(item);
                } else {
                    // 가격 변동시
                    if (!redisItem.getPrice().equals(item.getPrice())) {
                        cart.addMessage(redisProduct.getName() + item.getName() +
                            "의 가격이 변경되었습니다. 확인 부탁드립니다.");
                    }
                    // 추가한 상품만큼 개수 추가
                    redisItem.setCount(redisItem.getCount() + item.getCount());
                }
            }
        } else {
            Cart.Product product = Cart.Product.from(form);
            cart.getProducts().add(product);
        }
        redisClient.put(customerId, cart);
        return cart;
    }


}
