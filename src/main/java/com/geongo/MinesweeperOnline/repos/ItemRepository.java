package com.geongo.MinesweeperOnline.repos;

import com.geongo.MinesweeperOnline.entity.Item;
import com.geongo.MinesweeperOnline.entity.ItemType;
import com.geongo.MinesweeperOnline.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByUser(User user);
    Item findByTypeAndUser(ItemType type, User user);
}
