package com.daniilyurov.training.project.flowershop;

import com.daniilyurov.training.project.flowershop.abstraction.Accessory;
import com.daniilyurov.training.project.flowershop.abstraction.Flower;
import com.daniilyurov.training.project.flowershop.exception.FileIsEmptyException;
import com.daniilyurov.training.project.flowershop.exception.InvalidFileException;

import java.io.*;
import java.util.*;

public class Bouquet {

    private Set<Accessory> accessories = new HashSet<>();
    private SortedSet<Flower> flowers = new TreeSet<>(Flower.getAscendingFreshnessComparator());

    public void addFlower(Flower flower) {
        flowers.add(flower);
    }

    public void addAccessory(Accessory accessory) {
        accessories.add(accessory);
    }

    public int getPriceInCents() {
        return Flower.getPrice(flowers) + Accessory.getPrice(accessories);
    }

    public Flower getFlowerMatchingStemLengthBounds(int lowerBound, int upperBound) {
        return Flower.getFirstFlowerMatchingStemLengthBounds(lowerBound, upperBound, flowers);
    }

    public void printPrice() {
        System.out.println("Bouquet price : " + (getPriceInCents()/100.0));
    }

    public static Bouquet loadBouquetFromSsvFile(String fileName) {
        Bouquet result = new Bouquet();
        try {
            InputStream stream = getInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String value = reader.readLine();
            if (value == null) {
                throw new FileIsEmptyException();
            }
            while (value != null) {
                StringTokenizer tokens = new StringTokenizer(value, " ");
                String type = tokens.nextToken();
                switch (type) {
                    case "Accessory":
                        result.addAccessory(Accessory.getAccessoryInstance(tokens));
                        break;
                    case "Flower":
                        result.addFlower(Flower.getFlowerInstance(tokens));
                        break;
                    default:
                        throw new InvalidFileException();
                }
                value = reader.readLine();
            }

        } catch (Exception e) {
            if (e instanceof IOException) {
                System.out.println("File could not be loaded.");
            } else if (e instanceof InvalidFileException) {
                System.out.println("File is corrupt.");
            } else if (e instanceof FileIsEmptyException) {
                System.out.println("File is empty");
            }
            return null;
        }
        return result;
    }

    private static InputStream getInputStream(String fileName) throws FileNotFoundException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(fileName);
        if (is == null) {throw new FileNotFoundException();}
        return is;
    }

    public void printFlowers() {
        int index = 1;
        System.out.println("List of flowers sorted by freshness:");
        for (Flower flower : flowers) {
            System.out.print("\t" + index + ". " + flower.getType());
            System.out.print(" cut on : " + flower.getDateTimeCut());
            System.out.println(", stem length " + flower.getStemLengthInCentimeters());
            index++;
        }
    }

    public void printFreshestFlowerMatchingStemLengthBounds(int lowerBound, int upperBound) {
        Flower flower = getFlowerMatchingStemLengthBounds(lowerBound, upperBound);
        if (flower == null) {
            System.out.println("No flower matching the bounds found.");
        } else {
            System.out.println("The freshest flower matching bounds [" + lowerBound + ", " + upperBound + "] cm : ");
            System.out.println("\t" + flower.getType() + ", length : " + flower.getStemLengthInCentimeters()
                    + ", price : " + flower.getUnitPriceInCents() + ", cut on : " + flower.getDateTimeCut());
        }

    }
}
