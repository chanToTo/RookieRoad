package com.orange.designPattern.decoratorPattern;

public class Bread extends Food{
    private Food basicFood;

    public Bread(Food basicFood) {
        this.basicFood = basicFood;
    }

    public String make() {
        return basicFood.make() + "+面包";
    }
}
