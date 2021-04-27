package com.geongo.MinesweeperOnline.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "type_id", nullable = false)
    private ItemType type;
    private int amount;
    private LocalDateTime purchaseDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public  Purchase(Item item, LocalDateTime date, User user){
        this.purchaseDate = date;
        this.user = user;
        this.type = item.getType();
        this.amount = item.getAmount();
    }
}
