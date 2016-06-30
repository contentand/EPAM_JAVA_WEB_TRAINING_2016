package com.daniilyurov.training.concurrency.violinists;

public class Violinist extends Thread {

    private Box box;
    private int performedTimes;

    public Violinist(Box box, int index) {
        this.box = box;
        this.setName(String.valueOf(index));
    }

    @Override
    public void run() {
        while (true) {
            Box.Element rarerElement = box.getRarerElement();
            if (rarerElement != null) {
                while (true) {
                    Box.Element oftenerElement = box.getOftenerElement();
                    if (oftenerElement != null) {
                        // perform
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        performedTimes++;

                        box.returnOftenerElement(oftenerElement);
                        break;
                    }
                }
            }
            box.returnRarerElement(rarerElement);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public int getPerformedTimes() {
        return performedTimes;
    }
}
