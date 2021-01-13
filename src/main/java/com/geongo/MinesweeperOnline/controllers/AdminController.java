package com.geongo.MinesweeperOnline.controllers;

import com.geongo.MinesweeperOnline.entity.Item;
import com.geongo.MinesweeperOnline.entity.ItemType;
import com.geongo.MinesweeperOnline.entity.Match;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.services.ItemService;
import com.geongo.MinesweeperOnline.services.ItemTypeService;
import com.geongo.MinesweeperOnline.services.UserService;
import com.geongo.MinesweeperOnline.services.impl.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private MatchServiceImpl matchService;
    @Autowired
    private ItemTypeService itemTypeService;

    @GetMapping("/admin")
    public String userList(Model model) {

        model.addAttribute("users", userService.allUsers());


        return "admin_panel";
    }

    @GetMapping("/admin/user_info/{username}")
    public String userInfo(Model model, @PathVariable("username") String username){

        User user = (User) userService.loadUserByUsername(username);
        List<Item> inventory = itemService.findItemsByUser(user);
        List<Match> matches = matchService.getAllMatchesByUser(user);
        List<ItemType> types = itemTypeService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("inventory", inventory);
        model.addAttribute("matches", matches);
        model.addAttribute("types", types);

        return "user_info";
    }

    @PostMapping("/admin/user_info/{username}/delete_item")
    public String  delete_item(Model model, @PathVariable("username") String username, @ModelAttribute(value = "item") Item item){

        item.setUser(null);
        itemService.saveItem(item);

        return "redirect:/admin/user_info/" + username;
    }

    @PostMapping("/admin/user_info/{username}/add_item")
    public String  add_item(Model model, @PathVariable("username") String username, @ModelAttribute(value = "item") Item item){

        Item checkItem = itemService.findByType(item.getType(), (User) userService.loadUserByUsername(username));

        if (checkItem != null){
            checkItem.setAmount(item.getAmount() + checkItem.getAmount());
            itemService.saveItem(checkItem);
        } else {

            item.setUser((User) userService.loadUserByUsername(username));
            itemService.saveItem(item);
        }

        return "redirect:/admin/user_info/" + username;
    }

    @GetMapping("/admin/user_info/{username}/report")
    public String getReport(Model model, @PathVariable("username") String username){

        User user = (User) userService.loadUserByUsername(username);
        List<Item> inventory = itemService.findItemsByUser(user);
        List<Match> matches = matchService.getAllMatchesByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("inventory", inventory);
        model.addAttribute("matches", matches);

        return "report";
    }

}