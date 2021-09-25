package org.ace.assignment.model;

import lombok.Data;

import java.util.List;

@Data
public class Country {
    String name;
    int population;
    private List listOfStates;
}
