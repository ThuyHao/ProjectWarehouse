package com.example.dw_huy.beans.DBNew;

import java.sql.Timestamp;

public class game_newsFact {
    private int id;
    private String title;
    private int author_id;
    private String description;
    private String url;
    private String image;
    private String content;
    private String source;
    private int category_id;
    private int isDelete;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String created_by;
    private String updated_by;

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

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public game_newsFact(int id, String title, int author_id, String description, String url, String image, String content, String source, int category_id, int isDelete, Timestamp created_at, Timestamp updated_at, String created_by, String updated_by) {
        this.id = id;
        this.title = title;
        this.author_id = author_id;
        this.description = description;
        this.url = url;
        this.image = image;
        this.content = content;
        this.source = source;
        this.category_id = category_id;
        this.isDelete = isDelete;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.created_by = created_by;
        this.updated_by = updated_by;
    }

    public game_newsFact() {
    }

    @Override
    public String toString() {
        return "game_newsFact{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author_id=" + author_id +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", source='" + source + '\'' +
                ", category_id=" + category_id +
                ", isDelete=" + isDelete +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", created_by='" + created_by + '\'' +
                ", updated_by='" + updated_by + '\'' +
                '}';
    }
}


