package com.daniilyurov.training.project.flowershop;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Bouquet bouquet = Bouquet.loadBouquetFromSsvFile("bouquet.ssv");
        if (bouquet != null) {
            bouquet.printPrice();
            bouquet.printFlowers();
            bouquet.printFreshestFlowerMatchingStemLengthBounds(getLowerBoundFromUser(), getUpperBoundFromUser());
        }
    }

    private static int getLowerBoundFromUser() {
        System.out.println("Enter lower bound of flower stem length (inclusive) : ");
        return scanner.nextInt();
    }

    private static int getUpperBoundFromUser() {
        System.out.println("Enter upper bound of flower stem length (inclusive) : ");
        return scanner.nextInt();
    }
}
