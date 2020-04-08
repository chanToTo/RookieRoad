package com.orange.designPattern.decoratorPattern;

public class Food {
    private String food_name;

    public Food(String food_name) {
        this.food_name = food_name;
    }

    public Food() {
    }

    public String make() {
        return food_name;
    }
}
