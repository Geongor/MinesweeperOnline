package com.geongo.MinesweeperOnline.controllers;

import com.geongo.MinesweeperOnline.entity.Item;
import com.geongo.MinesweeperOnline.entity.ItemType;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.services.ItemService;
import com.geongo.MinesweeperOnline.services.ItemTypeService;
import com.geongo.MinesweeperOnline.services.PurchaseService;
import com.geongo.MinesweeperOnline.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ItemShopController {

    private final ItemTypeService itemTypeService;
    private final PurchaseService purchaseService;

    @Autowired
    public ItemShopController(ItemTypeService itemTypeService, PurchaseService purchaseService){
        this.itemTypeService = itemTypeService;
        this.purchaseService = purchaseService;
    }

    @GetMapping("/shop")
    public String shop(Model model){

        model.addAttribute("types", itemTypeService.findAll());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("money", user.getMoney());

        return "shop_page";
    }
    @PostMapping("/shop/buy")
    public String shopBuy(Model model, @ModelAttribute(value = "item")Item item){

        purchaseService.makeAndSavePurchase(item);

        return "redirect:/shop";
    }
}
