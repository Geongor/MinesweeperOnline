package com.geongo.MinesweeperOnline.controllers;

import com.geongo.MinesweeperOnline.classes.Cell;
import com.geongo.MinesweeperOnline.classes.GameField;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.services.ItemService;
import com.geongo.MinesweeperOnline.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.catalina.session.StandardSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class MainController {

    ItemService itemService;
    UserService userService;

    @GetMapping("/main")
    public String greeting(Model model, HttpSession session) {
        GameField gameField = new GameField(20,10,20);
        model.addAttribute("field",gameField.getField());
        session.setAttribute("gameField", gameField);
        model.addAttribute("items", itemService.findItemsByUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));

        return "game_page";
    }

    @GetMapping("/")
    public String greeting(Model model, @RequestParam(value = "sort", defaultValue = "level") String sort) {

        ArrayList<User> users;

        if (sort.equals("record")) users = (ArrayList<User>) userService.findUsersOrderByRecord();
        else users = (ArrayList<User>) userService.findUsersOrderByLevel();


        model.addAttribute("users", users);

        return "index";
    }


}
