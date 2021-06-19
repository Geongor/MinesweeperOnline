package com.geongo.MinesweeperOnline.controllers;

import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;

@Controller
@AllArgsConstructor
public class PlayersController {

    UserService userService;

    @GetMapping("/players")
    public String userList(Model model) {

        ArrayList<User> users = (ArrayList<User>) userService.allUsers();
        Collections.sort(users);

        model.addAttribute("users", users);


        return "players_table";
    }
}
