package com.fahimshahrierrasel.collectionnotifier.model;

public class Bin {
    private String name, count, status;

    public Bin(String name, String count, String status) {
        this.name = name;
        this.count = count;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
