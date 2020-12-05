package com.geongo.MinesweeperOnline.controllers;

import com.geongo.MinesweeperOnline.classes.Cell;
import com.geongo.MinesweeperOnline.classes.GameField;
import org.apache.catalina.session.StandardSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/main")
    public String greeting(Model model, HttpSession session) {
        GameField gameField = new GameField(20,10,20);
        model.addAttribute("field",gameField.getField());
        session.setAttribute("gameField", gameField);
        return "game_page";
    }

}
