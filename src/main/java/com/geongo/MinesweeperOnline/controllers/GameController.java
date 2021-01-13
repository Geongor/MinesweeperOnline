package com.geongo.MinesweeperOnline.controllers;

import com.geongo.MinesweeperOnline.classes.Cell;
import com.geongo.MinesweeperOnline.classes.GameField;
import com.geongo.MinesweeperOnline.entity.Match;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.services.impl.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope("session")
@SessionAttributes("gameField")
public class GameController {

    @Autowired
    MatchServiceImpl matchService;

    @PostMapping("/cell") //проверить адрес
    public @ResponseBody
    Map<String, String> cellChoose(@ModelAttribute(value = "cell") Cell cell, GameField gameField) {

        Map<String,String> cellsToChange = new HashMap<>(); //оптимизировать создание и хранение мапы
        if (!gameField.isGameStarted()){

            gameField.startGame();
        }

        cellsToChange = gameField.getMapOfCellsToChange(cell, cellsToChange);

        if (gameField.getEndTime() != null) {

            matchService.saveMatch(gameField.getMatch());
        }

        return cellsToChange;

    }

    @PostMapping("/new_field")
    public @ResponseBody
    Map<String, Integer> createNewFiled(Model model, GameField gameField, HttpSession session, @RequestParam int width,
                                 @RequestParam int height, @RequestParam int minesCount) {
        gameField.newField(width, height, minesCount);
        model.addAttribute("field", gameField.getField());
        Map<String, Integer> fieldSize = new HashMap<>();
        fieldSize.put("width", width);
        fieldSize.put("height", height);
        return fieldSize;
    }
}
