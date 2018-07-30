package com.jpa.jpatest.domain;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void 카테고리_아이템_쿼리확인() {
        Item item = new Item();
        item.setName("아이템1");

        itemRepository.save(item);

        Category category = new Category();
        category.setName("카테고리1");
        // 꺼내서 add() 하는 방식은 안썼었는데
        category.getItems().add(item);

        categoryRepository.save(category);

        assertThat(itemRepository.findAll().get(0).getCategories().get(0).getName(), is("카테고리1"));
        // fetch = FetchType.EAGER 이거 해줘야 한다.
    }
}