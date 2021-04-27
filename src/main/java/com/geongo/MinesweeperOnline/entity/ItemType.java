package com.geongo.MinesweeperOnline.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "itemTypes")
public class ItemType {

    @Id
    private Long id;
    private String name;
    private String description;
    private int cost;
}
