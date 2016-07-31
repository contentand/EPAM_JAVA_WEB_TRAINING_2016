package com.daniilyurov.training.trees.avl;

public class AvlTree<E extends Comparable<E>> {
    public Node root;

    private class Node {
        E element;
        Node left;
        Node right;
        int height;

        public Node (E element){
            this (element, null, null);
        }

        public Node (E element, Node left, Node right){
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    private int height (Node node){
        return node == null ? -1 : node.height;
    }

    private int max (int a, int b){
        return a > b ? a : b;
    }

    public boolean add(E element){
        Node n = add(element, root);
        if (n == null) {
            return false;
        }
        root = n;
        return true;
    }

    private Node add(E element, Node node) {
        if (node == null)
            node = new Node(element);
        else if (element.compareTo(node.element) < 0){
            node.left = add(element, node.left);

            if (height(node.left) - height(node.right) == 2){
                if (element.compareTo (node.left.element) < 0){
                    node = rotateWithLeftChild(node);
                }
                else {
                    node = doubleWithLeftChild(node);
                }
            }
        }
        else if (element.compareTo (node.element) > 0){
            node.right = add(element, node.right);

            if ( height (node.right) - height (node.left) == 2)
                if (element.compareTo (node.right.element) > 0){
                    node = rotateWithRightChild (node);
                }
                else{
                    node = doubleWithRightChild (node);
                }
        }
        else {
            return null;
        }

        node.height = max (height (node.left), height (node.right)) + 1;
        return node;
    }

    private Node rotateWithLeftChild (Node k2){
        Node k1 = k2.left;

        k2.left = k1.right;
        k1.right = k2;

        k2.height = max (height (k2.left), height (k2.right)) + 1;
        k1.height = max (height (k1.left), k2.height) + 1;

        return (k1);
    }


    private Node doubleWithLeftChild (Node k3){
        k3.left = rotateWithRightChild (k3.left);
        return rotateWithLeftChild (k3);
    }

    private Node rotateWithRightChild (Node k1){
        Node k2 = k1.right;

        k1.right = k2.left;
        k2.left = k1;

        k1.height = max (height (k1.left), height (k1.right)) + 1;
        k2.height = max (height (k2.right), k1.height) + 1;

        return (k2);
    }

    private Node doubleWithRightChild (Node k1){
        k1.right = rotateWithLeftChild (k1.right);
        return rotateWithRightChild (k1);
    }

    public boolean isEmpty(){
        return (root == null);
    }


    private Node findMax(Node t)
    {
        if( t == null )
            return t;

        while(t.right != null)
            t = t.right;
        return t;
    }

    public void remove(E x) {
        root = remove(x, root);
    }

    public Node remove(E x, Node t) {
        if (t==null)    {
            return null;
        }

        if (x.compareTo(t.element) < 0 ) {
            t.left = remove(x,t.left);
            int l = t.left != null ? t.left.height : 0;

            if((t.right != null) && (t.right.height - l >= 2)) {
                int rightHeight = t.right.right != null ? t.right.right.height : 0;
                int leftHeight = t.right.left != null ? t.right.left.height : 0;

                if(rightHeight >= leftHeight)
                    t = rotateWithLeftChild(t);
                else
                    t = doubleWithRightChild(t);
            }
        }
        else if (x.compareTo(t.element) > 0) {
            t.right = remove(x,t.right);
            int r = t.right != null ? t.right.height : 0;
            if((t.left != null) && (t.left.height - r >= 2)) {
                int leftHeight = t.left.left != null ? t.left.left.height : 0;
                int rightHeight = t.left.right != null ? t.left.right.height : 0;
                if(leftHeight >= rightHeight)
                    t = rotateWithRightChild(t);
                else
                    t = doubleWithLeftChild(t);
            }
        }

        else if(t.left != null) {
            t.element = findMax(t.left).element;
            remove(t.element, t.left);

            if((t.right != null) && (t.right.height - t.left.height >= 2)) {
                int rightHeight = t.right.right != null ? t.right.right.height : 0;
                int leftHeight = t.right.left != null ? t.right.left.height : 0;

                if(rightHeight >= leftHeight)
                    t = rotateWithLeftChild(t);
                else
                    t = doubleWithRightChild(t);
            }
        }

        else
            t = (t.left != null) ? t.left : t.right;

        if(t != null) {
            int leftHeight = t.left != null ? t.left.height : 0;
            int rightHeight = t.right!= null ? t.right.height : 0;
            t.height = Math.max(leftHeight,rightHeight) + 1;
        }
        return t;
    }

    public boolean contains(E x){
        return contains(x, root);
    }

    protected boolean contains(E x, Node t) {
        if (t == null){
            return false;

        } else if (x.compareTo(t.element) < 0){
            return contains(x, t.left);
        } else if (x.compareTo(t.element) > 0){
            return contains(x, t.right);
        }

        return true;
    }


    public static void main(String[] args) {
        AvlTree<Integer> ls = new AvlTree<>();
        System.out.println(ls.add(4));
        System.out.println(ls.contains(5));
    }
}
