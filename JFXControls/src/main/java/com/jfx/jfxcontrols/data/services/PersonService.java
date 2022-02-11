package com.jfx.jfxcontrols.data.services;

import com.jfx.jfxcontrols.data.models.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonService {
    public static List<Person> GetPersonList() {
        List<Person> source = new ArrayList<>();
        source.add(new Person(1, "Jack", "Brown",23, "England"));
        source.add(new Person(2, "Jane", "Bowel",25, "England"));
        source.add(new Person(3, "Angelina", "Aniston",43, "USA"));
        source.add(new Person(4, "Austin", "Axel",29, "Denmark"));
        source.add(new Person(5, "Kelvin", "Johnson",31, "Finland"));
        source.add(new Person(6, "Kevin", "Thor",30, "Germany"));
        source.add(new Person(7, "Jennifer", "Thompson",19, "Germany"));
        source.add(new Person(8, "Valeria", "Jobs",26, "Spain"));
        source.add(new Person(9, "Cagdas", "Usfekes",35, "Turkey"));
        source.add(new Person(10, "Donald", "Costner",50, "Canada"));
        return  source;
    }
}
