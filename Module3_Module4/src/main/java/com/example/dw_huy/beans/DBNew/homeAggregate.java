package com.example.dw_huy.beans.DBNew;

import java.sql.Timestamp;

public class homeAggregate {
    private int id;
    private String name_category;
    private String title;
    private String image;

    private String description;
    private String name_author;
    private Timestamp day_up;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName_author() {
        return name_author;
    }

    public void setName_author(String name_author) {
        this.name_author = name_author;
    }

    public Timestamp getDay_up() {
        return day_up;
    }

    public void setDay_up(Timestamp day_up) {
        this.day_up = day_up;
    }

    public homeAggregate(int id, String name_category, String title, String image, String description, String name_author, Timestamp day_up) {
        this.id = id;
        this.name_category = name_category;
        this.title = title;
        this.image = image;
        this.description = description;
        this.name_author = name_author;
        this.day_up = day_up;
    }

    public homeAggregate() {
    }
}
