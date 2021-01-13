package com.geongo.MinesweeperOnline.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "itemTypes")
public class ItemType {

    @Id
    private Long id;
    private String name;
    private String description;

    public ItemType(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ItemType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
