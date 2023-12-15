package model;

import java.sql.Timestamp;

public class Staging {
    private int id;
    private String title;
    private String description;
    private String authorName;
    private Timestamp timeUp;
    private String url;
    private String categoryName;
    private String image;
    private String content;
    private String sourceName;
    private String status;
    private Timestamp createdAt;

    // Constructors

    public Staging() {
        // Default constructor
    }

    public Staging(String title, String description, String authorName, Timestamp timeUp, String url,
                       String categoryName, String image, String content, String sourceName, String status) {
        this.title = title;
        this.description = description;
        this.authorName = authorName;
        this.timeUp = timeUp;
        this.url = url;
        this.categoryName = categoryName;
        this.image = image;
        this.content = content;
        this.sourceName = sourceName;
        this.status = status;
    }

    // Getters and Setters

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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
