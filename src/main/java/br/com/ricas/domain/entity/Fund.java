package br.com.ricas.domain.entity;

import java.util.Date;

public class Fund {
    private String name;
    private Double value;
    private Date createdAt;

    public String getName() {
        return name;
    }

    public Fund(String name, Double value, Date createdAt) {
        this.name = name;
        this.value = value;
        this.createdAt = createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
