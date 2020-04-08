package com.orange.designPattern.observerPattern;

import java.util.ArrayList;
import java.util.List;

public class XiaoMei {

    List<Person> list = new ArrayList<>();

    public void addPerson(Person person) {
        list.add(person);
    }

    public void notifyPerson() {
        for (Person person : list) {
            person.getMessage("come on");
        }
    }
}
