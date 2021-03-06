package com.geongo.MinesweeperOnline.classes;

import org.springframework.stereotype.Component;

import javax.swing.text.Position;
import java.awt.*;

public class Cell {
    private String id = null;
    private Point position;
    private cellTypes trueCellType = cellTypes.NONE;
    private boolean visible = false;

    public Cell(int x, int y, cellTypes cellType) {
        this.trueCellType = cellType;
        position = new Point(x,y);

        this.id = "cell"+y+"_"+x;
    }


    public boolean isSame(Cell cell){

        return  position.equals(cell.getPosition());
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public cellTypes getTrueCellType() {
        return trueCellType;
    }

    public void setTrueCellType(cellTypes trueCellType) {
        this.trueCellType = trueCellType;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
