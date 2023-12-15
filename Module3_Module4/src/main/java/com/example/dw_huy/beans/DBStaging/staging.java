package com.example.dw_huy.beans.DBStaging;

import java.sql.Timestamp;

public class staging {
    private int id;
    private String title;
    private String description;
    private String author_name;
    private Timestamp timeUp;
    private String url;
    private String category_name;
    private String image;
    private String content;
    private String source_name;
    private String status;
    private Timestamp created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public Timestamp getTimeUp() {
        return timeUp;
    }

    public void setTimeUp(Timestamp timeUp) {
        this.timeUp = timeUp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public staging(int id, String title, String description, String author_name, Timestamp timeUp, String url, String category_name, String image, String content, String source_name, String status, Timestamp created_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author_name = author_name;
        this.timeUp = timeUp;
        this.url = url;
        this.category_name = category_name;
        this.image = image;
        this.content = content;
        this.source_name = source_name;
        this.status = status;
        this.created_at = created_at;
    }

    public staging() {
    }
}
