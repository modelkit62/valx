package com.example.validationdemo.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Car {

    @Pattern(regexp="[abc]+")
    private String name;

    @Size(min = 3, max = 20)
    private String colour;

    public Car() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
