package com.daniilyurov.training.project.xml.model;

import com.daniilyurov.training.project.xml.ThreadUnsafe;

import java.util.Arrays;
import java.util.List;

//TODO: doc comments
@ThreadUnsafe
public class Performance {
    private String number;
    private String type;
    private String scene;
    private String numberOfDancers;
    private String music;
    private String groupName;
    private List<Dancer> dancers;

    public static final class Element {
        public static transient final String
                TYPE = "type",
                SCENE = "scene",
                NUMBER_OF_DANCERS = "number_of_dancers",
                MUSIC = "music",
                DANCERS = "dancers";
    }

    public static final class Attribute {
        public static transient final String
                NUMBER = "number",
                GROUP_NAME = "groupName";
    }

    public String getGroupName() {
        return groupName;
    } //TODO: check

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNumber() {
        return number;
    } //TODO: check

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getNumberOfDancers() {
        return numberOfDancers;
    } //TODO: check

    public void setNumberOfDancers(String numberOfDancers) {
        this.numberOfDancers = numberOfDancers;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public List<Dancer> getDancers() {
        return dancers;
    } //TODO: check

    public void setDancers(List<Dancer> dancers) {
        this.dancers = dancers;
    }

    @Override
    public String toString() {
        return "Танец номер " + number.substring(2) + "\n" +
                "\t тип: " + type + "\n" +
                "\t scene: " + scene + "\n" +
                "\t music: " + music + "\n" +
                "\t group: " + (groupName != null ? groupName : "нет названия") + "\n" +
                "\t number: " + numberOfDancers + "\n" +
                "\t dancers: \n\t\t\t " + dancers.stream()
                        .map(Dancer::toString)
                        .reduce((d1, d2) -> d1 + "\n\t\t\t " + d2)
                        .get();
    }
}
