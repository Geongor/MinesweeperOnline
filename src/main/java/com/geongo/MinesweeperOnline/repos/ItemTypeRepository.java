package com.geongo.MinesweeperOnline.repos;

import com.geongo.MinesweeperOnline.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
    ItemType findByName(String name);
}
