package com.ptit.androidptit.model;

public class Item {
    public static String TABLE_NAME = "items";
    private Integer id;
    private String name;
    private String description;

    private String status;
    private Boolean party;
    private String deadline;

    public Item() {
    }

    public Item(String name, String description, Boolean party, String deadline) {
        this.name = name;
        this.description = description;
        this.party = party;
        this.deadline = deadline;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getParty() {
        return party;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParty(Boolean party) {
        this.party = party;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void validate() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description is required");
        }
        if (deadline == null || deadline.isEmpty()) {
            throw new IllegalArgumentException("Deadline is required");
        }
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }
    }
}
