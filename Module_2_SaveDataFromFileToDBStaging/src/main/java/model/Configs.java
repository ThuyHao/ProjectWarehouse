package model;

import java.sql.Timestamp;

public class Configs {
    private int id;
    private String name;
    private String description;
    private String sourcePath;
    private String destination;
    private String columns;
    private String separator;
    private String location;
    private String format;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String createdBy;
    private String updatedBy;

    // Constructors

    public Configs() {
        // Default constructor
    }

    public Configs(int id, String name, String description, String sourcePath, String destination, String columns,
                  String separator, String location, String format, Timestamp createdAt, Timestamp updatedAt,
                  String createdBy, String updatedBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sourcePath = sourcePath;
        this.destination = destination;
        this.columns = columns;
        this.separator = separator;
        this.location = location;
        this.format = format;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    // Getter and Setter methods

    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // SourcePath
    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    // Destination
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Columns
    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    // Separator
    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    // Location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Format
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    // CreatedAt
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // UpdatedAt
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // CreatedBy
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    // UpdatedBy
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}

