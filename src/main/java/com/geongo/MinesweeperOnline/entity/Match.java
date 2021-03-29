package com.geongo.MinesweeperOnline.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "matches")
@Data
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

    public void setUser(User user) {
        this.user = user;
    }
}
