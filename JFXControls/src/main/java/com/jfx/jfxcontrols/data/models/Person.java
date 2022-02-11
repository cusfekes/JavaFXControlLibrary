package com.jfx.jfxcontrols.data.models;

public class Person {
    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private String country;

    public Person(Integer id, String name, String surname, Integer age, String country) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
