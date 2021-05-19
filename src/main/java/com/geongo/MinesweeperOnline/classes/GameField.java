package com.geongo.MinesweeperOnline.classes;

import com.geongo.MinesweeperOnline.entity.Match;
import com.geongo.MinesweeperOnline.entity.User;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Data
public class GameField {

    private int width;
    private int height;
    private int minesCount;
    private Cell[][] field;
    private Date startTime;
    private Date endTime = null;

    private final SimpleDateFormat gameTimeFormat = new SimpleDateFormat("mm:ss:SSS");
    private Match match;

    private String itemSelected = "none";


    public GameField(int width, int height, int minesCount) {
        newField(width, height, minesCount);

    }

    public GameField() {
    }

    public void generateField(Cell cell){

        Random rnd = new Random();
        int minesToGenerate = minesCount;
        int x;
        int y;
        while (minesToGenerate > 0){
            while (true){
                x = rnd.nextInt(width);
                y = rnd.nextInt(height);
                if (field[y][x].getTrueCellType() != cellTypes.MINE && !field[y][x].isSame(cell)){
                    field[y][x].setTrueCellType(cellTypes.MINE);
                    minesToGenerate--;
                    break;
                }
            }
        }

        int counter = 0;
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if (field[i][j].getTrueCellType() == cellTypes.MINE) continue;
                y = i - 1;
                for (int m = 1; m<=3; m++) {
                    x = j - 1;
                    for (int n = 1; n <= 3; n++) {

                        if ((x >= 0 && x < width) && (y >= 0 && y < height)) {
                            if (field[y][x].getTrueCellType() == cellTypes.MINE) counter++;
                        }
                        x++;
                    }
                    y++;
                }
                switch (counter){
                    case 1: {
                        field[i][j].setTrueCellType(cellTypes.NUMBER_1);
                        break;
                    }
                    case 2: {
                        field[i][j].setTrueCellType(cellTypes.NUMBER_2);
                        break;
                    }
                    case 3: {
                        field[i][j].setTrueCellType(cellTypes.NUMBER_3);
                        break;
                    }
                    case 4: {
                        field[i][j].setTrueCellType(cellTypes.NUMBER_4);
                        break;
                    }
                    case 5: {
                        field[i][j].setTrueCellType(cellTypes.NUMBER_5);
                        break;
                    }
                    case 6: {
                        field[i][j].setTrueCellType(cellTypes.NUMBER_6);
                        break;
                    }
                    case 7: {
                        field[i][j].setTrueCellType(cellTypes.NUMBER_7);
                        break;
                    }
                    case 8: {
                        field[i][j].setTrueCellType(cellTypes.NUMBER_8);
                        break;
                    }
                }
                counter = 0;
            }
        }

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                System.out.print(field[i][j].getTrueCellType() + " ");
            }
            System.out.println();
        }

    }

    public Map<String, String> getMapOfCellsToChange(Cell cell, Map<String,String> cellsToChange){

        switch (findCellInField(cell).getTrueCellType()){

            case MINE: {
                if (this.itemSelected.equals("chance")){
                    cellsToChange.put(cell.getId(), "cell-flag");
                    cellsToChange.put("chanceUsed", "true");
                    this.itemSelected = "none";
                } else {
                    finishGame(cellsToChange, "lose");
                    cellsToChange.put(cell.getId(), "cell-mine");
                    field[cell.getPosition().y][cell.getPosition().x].setVisible(true);
                }
                break;
            }
            case EMPTY: {
                cellsToChange.put(cell.getId(), "cell-opened");

                if ( ( cell.getPosition().x - 1 >= 0 ) && !( cellsToChange.containsKey(field[cell.getPosition().y][cell.getPosition().x-1].getId()) ) ){
                   cellsToChange = getMapOfCellsToChange(field[cell.getPosition().y][cell.getPosition().x-1], cellsToChange);
                }
                if ( ( cell.getPosition().x + 1 < width ) && !( cellsToChange.containsKey(field[cell.getPosition().y][cell.getPosition().x+1].getId()) ) ){
                    cellsToChange = getMapOfCellsToChange(field[cell.getPosition().y][cell.getPosition().x+1], cellsToChange);
                }
                if ( ( cell.getPosition().y - 1 >= 0 ) && !( cellsToChange.containsKey(field[cell.getPosition().y-1][cell.getPosition().x].getId()) ) ){
                    cellsToChange = getMapOfCellsToChange(field[cell.getPosition().y-1][cell.getPosition().x], cellsToChange);
                }
                if ( ( cell.getPosition().y + 1 < height ) && !( cellsToChange.containsKey(field[cell.getPosition().y+1][cell.getPosition().x].getId()) ) ){
                    cellsToChange = getMapOfCellsToChange(field[cell.getPosition().y+1][cell.getPosition().x], cellsToChange);
                }
                if ( ( cell.getPosition().y - 1 >= 0 && cell.getPosition().x - 1 >= 0 ) && !( cellsToChange.containsKey(field[cell.getPosition().y-1][cell.getPosition().x-1].getId()) )
                        && ( field[cell.getPosition().y-1][cell.getPosition().x-1].getTrueCellType() != cellTypes.EMPTY ) ){
                    cellsToChange = getMapOfCellsToChange(field[cell.getPosition().y-1][cell.getPosition().x-1], cellsToChange);
                }
                if ( ( cell.getPosition().y - 1 >= 0 && cell.getPosition().x + 1 < width ) && !( cellsToChange.containsKey(field[cell.getPosition().y-1][cell.getPosition().x+1].getId()) )
                        && ( field[cell.getPosition().y-1][cell.getPosition().x+1].getTrueCellType() != cellTypes.EMPTY ) ){
                    cellsToChange = getMapOfCellsToChange(field[cell.getPosition().y-1][cell.getPosition().x+1], cellsToChange);
                }
                if ( ( cell.getPosition().y + 1 < height && cell.getPosition().x + 1 < width ) && !( cellsToChange.containsKey(field[cell.getPosition().y+1][cell.getPosition().x+1].getId()) )
                        && ( field[cell.getPosition().y+1][cell.getPosition().x+1].getTrueCellType() != cellTypes.EMPTY ) ){
                    cellsToChange = getMapOfCellsToChange(field[cell.getPosition().y+1][cell.getPosition().x+1], cellsToChange);
                }
                if ( ( cell.getPosition().y + 1 < height && cell.getPosition().x - 1 >= 0 ) && !( cellsToChange.containsKey(field[cell.getPosition().y+1][cell.getPosition().x-1].getId()) )
                        && ( field[cell.getPosition().y+1][cell.getPosition().x-1].getTrueCellType() != cellTypes.EMPTY ) ){
                    cellsToChange = getMapOfCellsToChange(field[cell.getPosition().y+1][cell.getPosition().x-1], cellsToChange);
                }
                field[cell.getPosition().y][cell.getPosition().x].setVisible(true);
                break;
            }
            case NUMBER_1: {
                cellsToChange.put(cell.getId(), "cell-opened-1");
                field[cell.getPosition().y][cell.getPosition().x].setVisible(true);
                break;
            }
            case NUMBER_2: {
                cellsToChange.put(cell.getId(), "cell-opened-2");
                field[cell.getPosition().y][cell.getPosition().x].setVisible(true);
                break;
            }
            case NUMBER_3: {
                cellsToChange.put(cell.getId(), "cell-opened-3");
                field[cell.getPosition().y][cell.getPosition().x].setVisible(true);
                break;
            }
            case NUMBER_4: {
                cellsToChange.put(cell.getId(), "cell-opened-4");
                field[cell.getPosition().y][cell.getPosition().x].setVisible(true);
                break;
            }
            case NUMBER_5: {
                cellsToChange.put(cell.getId(), "cell-opened-5");
                field[cell.getPosition().y][cell.getPosition().x].setVisible(true);
                break;
            }
            case NUMBER_6: {
                cellsToChange.put(cell.getId(), "cell-opened-6");
                field[cell.getPosition().y][cell.getPosition().x].setVisible(true);
                break;
            }
            case NUMBER_7: {
                cellsToChange.put(cell.getId(), "cell-opened-7");
                field[cell.getPosition().y][cell.getPosition().x].setVisible(true);
                break;
            }
            case NUMBER_8: {
                cellsToChange.put(cell.getId(), "cell-opened-8");
                field[cell.getPosition().y][cell.getPosition().x].setVisible(true);
                break;
            }
        }

        if (isEndGame()){
            finishGame(cellsToChange, "win");
            System.out.println(endTime.toString());

        }

        return cellsToChange;
    }

    public Cell findCellInField(Cell cell){

        for (int i = 0; i<height; i++){

            for (int j = 0; j<width; j++){

                if (field[i][j].isSame(cell)){
                    cell = field[i][j];
                    return cell;
                }
            }
        }

        cell.setTrueCellType(cellTypes.MINE);
        return cell;
    }

    public boolean isEndGame(){

        for (int i = 0; i<height; i++){

            for (int j = 0; j< width; j++){

                if (!field[i][j].isVisible() && field[i][j].getTrueCellType() != cellTypes.MINE)
                    return false;
            }
        }

        return true;
    }

    public boolean isGameStarted() {
        return this.startTime != null;
    }

    public void startGame(Cell cell) {
        startTime = new Date();
        this.generateField(cell);
    }

    public void finishGame(Map<String,String> cellsToChange, String gameStatus){
        endTime = new Date();
        cellsToChange.put("gameTime", gameTimeFormat.format(endTime.getTime()-startTime.getTime()));
        cellsToChange.put("status", gameStatus);

        match = new Match(width, height, minesCount, gameStatus, startTime, endTime, (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());


    }

    public void newField(int width, int height, int minesCount){
        this.width = width;
        this.height = height;
        this.minesCount = minesCount;
        this.startTime = null;
        this.endTime = null;

        this.field = new Cell[height][width];
        for (int i = 0; i<height; i++){
            for (int j = 0; j<width; j++){
                field[i][j] = new Cell(j,i,cellTypes.EMPTY);
            }
        }
    }
}
