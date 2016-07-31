package com.daniilyurov.training.trees.priority;

import java.util.Arrays;

public class PriorityQueue<E extends Comparable<E>> {
    private Object[] queue;
    private int size;
    private static final int MIN_SIZE = 4;

    public PriorityQueue() {
        queue = new Object[MIN_SIZE];
    }

    public void add(E element) {

        if (element == null) {
            throw new NullPointerException();
        }

        if (size == 0) {
            queue[0] = element;
            size++;
            return;
        }

        int position = binarySearch(element, 0, size - 1);
        int toStay = position;
        int toShift = queue.length - position - 1;

        if (size == queue.length - 1) {
            Object[] newQueue = new Object[queue.length * 2];
            System.arraycopy(queue, 0, newQueue, 0, toStay);
            System.arraycopy(queue, position, newQueue, position + 1, toShift);
            newQueue[position] = element;
            size++;
            queue = newQueue;
        }
        else {
            System.arraycopy(queue, position, queue, position + 1, toShift);
            queue[position] = element;
            size++;
        }
        System.out.println(Arrays.deepToString(queue));
    }

    public E retrieveMax() {
        E result = (E) queue[size - 1];
        queue[size - 1] = null;
        size--;
        if ((size < queue.length / 2) && queue.length > MIN_SIZE) {
            Object[] newQueue = new Object[queue.length / 2];
            System.arraycopy(queue, 0, newQueue, 0, queue.length / 2);
            queue = newQueue;
        }
        return result;
    }

    public int binarySearch(E element, int first, int last) {

        while (first <= last) {
            int middle = (first + last) / 2;
            E middleValue = (E) queue[middle];

            if (middleValue.compareTo(element) < 0) {
                first = middle + 1;
            }
            else if (middleValue.compareTo(element) > 0) {
                last = middle - 1;
            }
            else {
                return middle;
            }
        }

        return first;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> p = new PriorityQueue<>();
        System.out.println("Size: " + p.size());
        p.add(1);
        System.out.println("Size: " + p.size());
        p.add(3);
        System.out.println("Size: " + p.size());
        p.add(6);
        System.out.println("Size: " + p.size());
        p.add(2);
        System.out.println("Size: " + p.size());
        p.add(7);
        System.out.println("Size: " + p.size());
        System.out.println("RETRIEVE: " + p.retrieveMax());
        System.out.println("RETRIEVE: " + p.retrieveMax());
        System.out.println("RETRIEVE: " + p.retrieveMax());
        System.out.println("RETRIEVE: " + p.retrieveMax());
        p.add(4);
    }
}
