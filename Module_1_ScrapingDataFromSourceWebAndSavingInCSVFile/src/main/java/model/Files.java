package model;

import java.sql.Timestamp;

public class Files {
    private int id;
    private int config_id;
    private String name;
    private String column_name;
    private String data_format;
    private Timestamp file_timestamp;
    private String destination;
    private String dir_save;
    private String dir_archive;
    private String note;
    private String status;
    private Timestamp created_at;
    private  Timestamp updated_at;
    private String created_by;
    private String updated_by;

    public Files() {
    }

    public Files(int id, int config_id, String name, String column_name, String data_format, Timestamp file_timestamp, String destination, String dir_save, String dir_archive, String note, String status, Timestamp created_at, Timestamp updated_at, String created_by, String updated_by) {
        this.id = id;
        this.config_id = config_id;
        this.name = name;
        this.column_name = column_name;
        this.data_format = data_format;
        this.file_timestamp = file_timestamp;
        this.destination = destination;
        this.dir_save = dir_save;
        this.dir_archive = dir_archive;
        this.note = note;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.created_by = created_by;
        this.updated_by = updated_by;
    }


}
