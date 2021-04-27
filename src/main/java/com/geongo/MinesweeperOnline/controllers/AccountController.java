package com.geongo.MinesweeperOnline.controllers;

import com.geongo.MinesweeperOnline.entity.Item;
import com.geongo.MinesweeperOnline.entity.ItemType;
import com.geongo.MinesweeperOnline.entity.Match;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.services.ItemService;
import com.geongo.MinesweeperOnline.services.ItemTypeService;
import com.geongo.MinesweeperOnline.services.MatchService;
import com.geongo.MinesweeperOnline.services.impl.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AccountController {

    ItemService itemService;
    MatchServiceImpl matchService;
    ItemTypeService itemTypeService;

    @Autowired
    public AccountController(ItemService itemService, MatchServiceImpl matchService, ItemTypeService itemTypeService) {

        this.itemService = itemService;
        this.matchService = matchService;
        this.itemTypeService = itemTypeService;
    }

    @GetMapping("/account")
    public String userInfo(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Item> inventory = itemService.findItemsByUser(user);
        List<Match> matches = matchService.getAllMatchesByUser(user);
        List<ItemType> types = itemTypeService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("inventory", inventory);
        model.addAttribute("matches", matches);
        model.addAttribute("types", types);

        return "account_page";
    }
}
