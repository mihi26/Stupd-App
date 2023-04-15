package com.ptit.androidptit.repository;

import com.ptit.androidptit.model.Item;

import java.util.List;

public interface IItemRepository {
    List<Item> getAllItems();
    Item getItemById(int id);
    int addItem(Item item);
    void updateItem(Item item);
    void deleteItem(int id);
}
