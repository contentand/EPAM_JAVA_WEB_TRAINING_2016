package com.daniilyurov.training.concurrency.violinists;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class Box {

    private Queue<Element> rarerElementQueue;
    private Queue<Element> oftenerElementQueue;

    public Box(int violins, int bows) {
        Queue<Element> violinQueue = new LinkedList<>();
        Queue<Element> bowQueue = new LinkedList<>();

        IntStream.rangeClosed(1, violins).forEach(i -> violinQueue.add(new Violin(i)));
        IntStream.rangeClosed(1, bows).forEach(i -> bowQueue.add(new Bow(i)));

        rarerElementQueue = violinQueue.size() >= bowQueue.size() ? bowQueue : violinQueue;
        oftenerElementQueue = violinQueue.size() >= bowQueue.size() ? violinQueue : bowQueue;
    }

    public synchronized Element getRarerElement() {
        return rarerElementQueue.poll();
    }

    public synchronized Element getOftenerElement() {
        return oftenerElementQueue.poll();
    }

    public synchronized void returnRarerElement(Element element) {
        rarerElementQueue.add(element);
    }

    public synchronized void returnOftenerElement(Element element) {
        oftenerElementQueue.add(element);
    }

    public static interface Element {
        String getName();
    }

    private class Violin implements Element {
        private String name;
        public Violin(int id) {
            this.name = "Violin " + id;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private class Bow implements Element {
        private String name;
        public Bow (int id) {
            this.name = "Box " + id;
        }

        @Override
        public String getName() {
            return name;
        }
    }



}
