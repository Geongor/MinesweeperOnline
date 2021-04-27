package com.geongo.MinesweeperOnline.services;

import com.geongo.MinesweeperOnline.entity.Item;
import com.geongo.MinesweeperOnline.entity.Purchase;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.repos.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PurchaseService {

    PurchaseRepository purchaseRepository;

    ItemService itemService;
    UserService userService;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, ItemService itemService, UserService userService){
        this.purchaseRepository = purchaseRepository;
        this.itemService = itemService;
        this.userService = userService;
    }

    public boolean makeAndSavePurchase(Item item){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Item checkItem = itemService.findByTypeAndUser(item.getType(), user);

        if (user.getMoney() >= item.getAmount() * item.getType().getCost()) {

            userService.reduceMoney(user, item.getAmount() * item.getType().getCost());

            if (checkItem != null) {
                checkItem.setAmount(item.getAmount() + checkItem.getAmount());
                itemService.saveItem(checkItem);
            } else {

                item.setUser(user);
                itemService.saveItem(item);
            }

            Purchase purchase = new Purchase(item, LocalDateTime.now().plusHours(3), user);
            return savePurchase(purchase);
        } else return false;

    }

    public boolean savePurchase(Purchase purchase){

        purchaseRepository.save(purchase);

        return true;
    }
}
