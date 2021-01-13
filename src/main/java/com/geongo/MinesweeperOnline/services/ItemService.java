package com.geongo.MinesweeperOnline.services;

import com.geongo.MinesweeperOnline.entity.Item;
import com.geongo.MinesweeperOnline.entity.ItemType;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.repos.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public boolean saveItem(Item item){
        itemRepository.save(item);
        return true;
    }

    public List<Item> findItemsByUser(User user){

        return itemRepository.findAllByUser(user);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public Item findByType(ItemType type, User user){
        return itemRepository.findByTypeAndUser(type, user);
    }
}
