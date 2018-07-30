package com.jpa.jpatest.domain;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void 다대일_UPDATE_한번더_안날라가는지() {
        Item item = new Item();
        item.setName("아이템");

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);

        itemRepository.save(item);
        orderItemRepository.save(orderItem);
    }
}