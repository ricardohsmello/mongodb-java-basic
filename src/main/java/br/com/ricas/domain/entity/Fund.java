package br.com.ricas.domain.entity;

import java.util.Date;

public class Fund {
    private String name;
    private Double value;
    private Date date;

    public String getName() {
        return name;
    }

    public Fund() {}
    public Fund(String name, Double value, Date date) {
        this.name = name;
        this.value = value;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", date=" + date +
                '}';
    }
}