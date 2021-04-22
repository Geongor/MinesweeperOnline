package com.geongo.MinesweeperOnline.services;

import com.geongo.MinesweeperOnline.entity.ItemType;
import com.geongo.MinesweeperOnline.repos.ItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTypeService {

    @Autowired
    ItemTypeRepository itemTypeRepository;

    public List<ItemType> findAll(){
        return itemTypeRepository.findAll();
    }
    public ItemType findByName(String name){
        return itemTypeRepository.findByName(name);
    }
}
