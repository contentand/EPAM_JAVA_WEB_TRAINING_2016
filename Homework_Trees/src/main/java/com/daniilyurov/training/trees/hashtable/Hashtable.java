package com.daniilyurov.training.trees.hashtable;

import java.util.Arrays;

public class Hashtable<E> {

    private Node[] list;
    private int size;

    public Hashtable(int size) {
        this.list = new Node[size];
        Arrays.fill(this.list, new Node());
    }

    public void add(E element) {
        int index = getIndex(element);
        Node node = list[index];
        node.add(element);
        size++;
    }

    public boolean remove(E element) {
        int index = getIndex(element);
        Node node = list[index];
        boolean isRemoved = node.remove(element);
        if (isRemoved) {
            size--;
        }
        return isRemoved;
    }

    private int getIndex(E element) {
        int hashcode = element.hashCode();
        return hashcode % list.length;
    }

    private static class Node<E> {

        Elem first;

        class Elem {
            E value;
            Elem next;
        }

        void add(E element) {
            Elem el = new Elem();
            el.value = element;
            el.next = first;
            first = el;
        }

        boolean remove(E element) {
            boolean DO_REMOVE = true;
            return get(element, DO_REMOVE);
        }

        boolean isPresent(E element) {
            boolean DO_NOT_REMOVE = false;
            return get(element, DO_NOT_REMOVE);
        }

        boolean get(E element, boolean shouldRemove) {
            Elem previous = null;
            Elem current = first;
            while (current != null) {
                if (current.value.equals(element)) {
                    if (shouldRemove) {
                        if (previous != null) {
                            previous.next = current.next;
                        } else {
                            first = current.next;
                        }
                    }
                    return true;
                }
                previous = current;
                current = current.next;
            }
            return false;
        }
    }



    public static void main(String[] args) {
        Hashtable<Integer> ht = new Hashtable<>(30);
        ht.add(1);
        ht.add(2);
        ht.add(3);
        ht.add(3);

        System.out.println(ht.size);

        ht.remove(3);
        ht.remove(3);
        ht.remove(2);
        ht.remove(1);

        System.out.println(ht.size);

    }
}
