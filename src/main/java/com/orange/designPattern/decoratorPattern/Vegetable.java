package com.orange.designPattern.decoratorPattern;

public class Vegetable extends Food{

    private Food basicFood;

    public Vegetable(Food basicFood) {
        this.basicFood = basicFood;
    }

    public String make() {
        return basicFood.make() + "+蔬菜";
    }
}
