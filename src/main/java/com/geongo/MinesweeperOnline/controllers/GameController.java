package com.geongo.MinesweeperOnline.controllers;

import com.geongo.MinesweeperOnline.classes.Cell;
import com.geongo.MinesweeperOnline.classes.GameField;
import com.geongo.MinesweeperOnline.entity.Item;
import com.geongo.MinesweeperOnline.entity.Match;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.services.ItemService;
import com.geongo.MinesweeperOnline.services.UserService;
import com.geongo.MinesweeperOnline.services.impl.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("gameField")
public class GameController {

    @Autowired
    MatchServiceImpl matchService;
    @Autowired
    ItemService itemService;

    @PostMapping("/field/cell")
    public @ResponseBody
    Map<String, String> cellChoose(@ModelAttribute(value = "cell") Cell cell, GameField gameField) {

        Map<String,String> cellsToChange = new HashMap<>();
        if (!gameField.isGameStarted()){

            gameField.startGame(cell);
        }
        cellsToChange = gameField.getMapOfCellsToChange(cell, cellsToChange);

        if (gameField.getEndTime() != null) {

            matchService.saveMatch(gameField.getMatch());
        }

        return cellsToChange;

    }

    @PostMapping("/field/new")
    public @ResponseBody
    Map<String, Integer> createNewFiled(Model model, GameField gameField,  @RequestParam int width,
                                 @RequestParam int height, @RequestParam int minesCount, HttpSession session) {
        gameField.newField(width, height, minesCount);
        model.addAttribute("field", gameField.getField());
        Map<String, Integer> fieldSize = new HashMap<>();
        fieldSize.put("width", width);
        fieldSize.put("height", height);
        List<Item> inventory = itemService.findItemsByUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        session.setAttribute("inventory", inventory);
        return fieldSize;
    }

    @PostMapping("/field/item/locator")
    public @ResponseBody
    Map<String, String> useItemLocator(@ModelAttribute(value = "cell") Cell cell, GameField gameField, HttpSession session){

        return itemService.useLocator(gameField.getField(), cell, session);

    }
    @PostMapping("/field/item/chance")
    public @ResponseBody
    Map<String, String> useItemLocator(GameField gameField, HttpSession session){

        return itemService.useChance(gameField, session);

    }
}
