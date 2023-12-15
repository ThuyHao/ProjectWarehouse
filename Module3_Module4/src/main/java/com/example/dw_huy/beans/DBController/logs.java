package com.example.dw_huy.beans.DBController;

import java.sql.Timestamp;

public class logs {
    private int id;
    private Timestamp timestamp;
    private String status;
    private String event_name;
    private String location;
    private Timestamp created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
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

    public logs(int id, Timestamp timestamp, String status, String event_name, String location, Timestamp created_at) {
        this.id = id;
        this.timestamp = timestamp;
        this.status = status;
        this.event_name = event_name;
        this.location = location;
        this.created_at = created_at;
    }

    public logs() {
    }
}
