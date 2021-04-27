package com.geongo.MinesweeperOnline.services;

import com.geongo.MinesweeperOnline.classes.Cell;
import com.geongo.MinesweeperOnline.classes.GameField;
import com.geongo.MinesweeperOnline.classes.cellTypes;
import com.geongo.MinesweeperOnline.entity.Item;
import com.geongo.MinesweeperOnline.entity.ItemType;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.repos.ItemRepository;
import com.geongo.MinesweeperOnline.repos.ItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemTypeRepository itemTypeRepository;

    public boolean saveItem(Item item){
        itemRepository.save(item);
        return true;
    }

    public List<Item> findItemsByUser(User user){

        return itemRepository.findAllByUser(user);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public Item findByTypeAndUser(ItemType type, User user){
        return itemRepository.findByTypeAndUser(type, user);
    }

    public Map<String, String> useLocator(Cell[][] field, Cell chosenCell, HttpSession session){

        List<Item> inventory = (List<Item>) session.getAttribute("inventory");
        Map<String,String> cellsToUpdate = new HashMap<>();
        for (Item item : inventory){
            if (item.getType().getName().equals("locator")) {

                if (item.getAmount() < 1) break;
                for (int i = chosenCell.getPosition().y - 1; i < chosenCell.getPosition().y + 2; i++){
                    if (i < 0 || i > field.length - 1) continue;
                    for (int j = chosenCell.getPosition().x - 1; j < chosenCell.getPosition().x + 2; j++){
                        if (j < 0 || j > field[i].length - 1) continue;

                        if (field[i][j].getTrueCellType() == cellTypes.MINE){
                            cellsToUpdate.put(field[i][j].getId(), "cell-flag");
                        }
                    }
                }
                item.reduceAmount();
                cellsToUpdate.put("locator", String.valueOf(item.getAmount()));
                itemRepository.save(item);
                break;
            }
        }
        session.removeAttribute("inventory");
        session.setAttribute("inventory", inventory);

        return cellsToUpdate;
    }

    public Map<String, String> useChance(GameField gameField, HttpSession session){
        List<Item> inventory = (List<Item>) session.getAttribute("inventory");
        Map<String,String> toUpdate = new HashMap<>();
        for (Item item : inventory){
            if (item.getType().getName().equals("chance") && item.getAmount() > 0){
                item.reduceAmount();
                itemRepository.save(item);
                gameField.setItemSelected("chance");
                toUpdate.put(".item-chance", "cell-selected");
                toUpdate.put("chance", String.valueOf(item.getAmount()));
                break;
            }
        }
        return toUpdate;
    }
}
