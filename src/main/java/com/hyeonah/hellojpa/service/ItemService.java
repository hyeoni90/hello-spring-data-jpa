package com.hyeonah.hellojpa.service;

import com.hyeonah.hellojpa.domain.item.Item;
import com.hyeonah.hellojpa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hyeonahlee on 2020-11-15.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(final Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(final Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
