package com.orange.designPattern.decoratorPattern;

/**
 * 装饰者模式
 *  Food food = new Bread(new Vegetable(new Cream(new Food("香肠"))));
 */
public class DecoratorTest {

    public static void main(String[] args) {
        Food food = new Bread(new Vegetable(new Cream(new Food("香肠"))));
        System.out.println(food.make());
    }
}
