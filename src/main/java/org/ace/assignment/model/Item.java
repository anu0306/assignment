package org.ace.assignment.model;

import lombok.Data;

import java.util.List;

@Data
public class Item {
    private String id;
    private String type;
    private String name;
    private double ppu;
    private Batters batters;
    private List<Topping> topping;
}
