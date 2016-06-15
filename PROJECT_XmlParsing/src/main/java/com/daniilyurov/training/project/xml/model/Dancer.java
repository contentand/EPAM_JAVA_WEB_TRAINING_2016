package com.daniilyurov.training.project.xml.model;

import com.daniilyurov.training.project.xml.ThreadUnsafe;

import java.io.Serializable;
import java.util.Formatter;

//TODO: doc comments
@ThreadUnsafe
public class Dancer implements Serializable {
    public static transient final String DANCER = "dancer";

    private String name;
    private int age;
    private int years;
    private String description;

    public static final class Element {
        public static transient final String
                NAME = "name",
                AGE = "age",
                YEARS = "years",
                DESCRIPTION = "description";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter().format("A %s year old dancer called %s with over %s years of experience.%s",
                age, name, years, description != null ? " " + description : "");
        return formatter.toString();
    }
}
