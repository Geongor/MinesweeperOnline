package com.geongo.MinesweeperOnline.repos;

import com.geongo.MinesweeperOnline.entity.Purchase;
import com.geongo.MinesweeperOnline.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    List<Purchase> findAllByUser(User user);
}
