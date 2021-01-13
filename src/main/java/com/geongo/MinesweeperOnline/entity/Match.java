package com.geongo.MinesweeperOnline.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private int fieldWidth;
    private int fieldHeight;
    private int minesCount;
    private String gameStatus;
    private Date startTime;
    private Date endTime;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Match(int fieldWidth, int fieldHeight, int minesCount, String gameStatus, Date startTime, Date endTime, User user) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.minesCount = minesCount;
        this.gameStatus = gameStatus;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
    }

    public Match() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public void setMinesCount(int minesCount) {
        this.minesCount = minesCount;
    }

    public String isGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
