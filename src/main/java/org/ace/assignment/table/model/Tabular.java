package org.ace.assignment.table.model;

import lombok.Data;

@Data
public class Tabular {
    private String id;
    private String type;
    private String name;
    private String batterType;
    private String toppingType;

    public Tabular(String id, String type, String name, String batterType, String toppingType) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.batterType = batterType;
        this.toppingType = toppingType;
    }
}
