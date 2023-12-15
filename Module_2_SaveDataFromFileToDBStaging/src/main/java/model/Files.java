package model;

import java.sql.Timestamp;

public class Files {
    private int id;
    private int configId;
    private String name;
    private String columnName;
    private String dataFormat;
    private Timestamp fileTimestamp;
    private String destination;
    private String dirSave;
    private String dirArchive;
    private String note;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String createdBy;
    private String updatedBy;

    // Constructors

    public Files() {
        // Default constructor
    }

    public Files(int id, int configId, String name, String columnName, String dataFormat, Timestamp fileTimestamp,
                 String destination, String dirSave, String dirArchive, String note, String status,
                 Timestamp createdAt, Timestamp updatedAt, String createdBy, String updatedBy) {
        this.id = id;
        this.configId = configId;
        this.name = name;
        this.columnName = columnName;
        this.dataFormat = dataFormat;
        this.fileTimestamp = fileTimestamp;
        this.destination = destination;
        this.dirSave = dirSave;
        this.dirArchive = dirArchive;
        this.note = note;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public Timestamp getFileTimestamp() {
        return fileTimestamp;
    }

    public void setFileTimestamp(Timestamp fileTimestamp) {
        this.fileTimestamp = fileTimestamp;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDirSave() {
        return dirSave;
    }

    public void setDirSave(String dirSave) {
        this.dirSave = dirSave;
    }

    public String getDirArchive() {
        return dirArchive;
    }

    public void setDirArchive(String dirArchive) {
        this.dirArchive = dirArchive;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
