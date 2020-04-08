package com.orange.juc.demo;

import lombok.Data;

@Data
public class Test implements Cloneable{

    public int id;
    public String name;
    private ChildClass childClass;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Test test = new Test();
        test.setId(11);
        test.setName("redis");
        test.childClass = new ChildClass();
        test.childClass.setId(13);
        Test test1 = (Test) test.clone();
        test1.childClass.setId(12);
        System.out.println(test);
        System.out.println(test1);
        System.out.println(test1 == test);
        System.out.println(test.name.hashCode());
        System.out.println(test1.name.hashCode());
        System.out.println(test.childClass.id);
        System.out.println(test1.childClass.id);
        System.out.println(test1.childClass.hashCode() == test.childClass.hashCode());
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

@Data
class ChildClass{
    public int id;
    public String name;
}