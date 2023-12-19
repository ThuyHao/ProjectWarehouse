package com.example.dw_huy.beans.DBController;

import java.sql.Timestamp;

public class logs {
    private int id;
    private String event_name;
    private String event_type;
    private String status;
    private String location;
    private Timestamp created_at;

    public logs() {
    }

    public logs(int id, String event_name, String event_type, String status, String location, Timestamp created_at) {
        this.id = id;
        this.event_name = event_name;
        this.event_type = event_type;
        this.status = status;
        this.location = location;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "logs{" +
                "id=" + id +
                ", event_name='" + event_name + '\'' +
                ", event_type='" + event_type + '\'' +
                ", status='" + status + '\'' +
                ", location='" + location + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
