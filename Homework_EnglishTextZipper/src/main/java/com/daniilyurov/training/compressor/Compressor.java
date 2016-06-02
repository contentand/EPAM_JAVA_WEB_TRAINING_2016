package com.daniilyurov.training.compressor;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compressor {
    String text;

    public Compressor(String text) {
        this.text = text;
    }

    public void compress(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename + ".zip");
        ZipOutputStream zos = new ZipOutputStream(fos);

        Dictionary dictionary = getDictionary(text);
        ZipEntry zipEntry = new ZipEntry("dict");
        zos.putNextEntry(zipEntry);
        new ObjectOutputStream(zos).writeObject(dictionary);

        Accumulator accumulator = new Accumulator(dictionary);
        text.codePoints().forEach(accumulator::accumulate);
        zipEntry = new ZipEntry("data");
        zos.putNextEntry(zipEntry);
        accumulator.write(zos);

        zos.close();
        fos.close();
    }

    public void compress(OutputStream textOutputStream, OutputStream dictionaryOutputStream) throws IOException {
        Dictionary dictionary = getDictionary(text);
        Accumulator accumulator = new Accumulator(dictionary);
        text.codePoints().forEach(accumulator::accumulate);
        accumulator.write(textOutputStream);
        textOutputStream.flush();
        textOutputStream.close();

        ObjectOutputStream os = new ObjectOutputStream(dictionaryOutputStream);
        os.writeObject(dictionary);
        os.flush();
        os.close();
    }

    // transforms PriorityQueue into tree and returns a root node
    // CAUTION - PriorityQueue itself gets destroyed!
    public Node treefy(PriorityQueue<Node> k) {
        if (k.size() == 0) {
            return null;
        }
        while (k.size() > 1) {
            Node lessFrequent = k.remove();
            Node moreFrequent = k.remove();
            Node merge = new Node(lessFrequent, moreFrequent);
            k.add(merge);
        }
        return k.remove(); //last element left is the root.
    }

    public PriorityQueue<Node> analyze(String text) {
        PriorityQueue<Node> result = new PriorityQueue<>();

        Stream<Integer> strm = text.codePoints().mapToObj(Integer::new);

        Map<Integer, Long> map = strm.collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));

        map.forEach((codePoint, frequency) -> {
            result.add(new Node(codePoint, frequency));
        });

        return result;
    }

    public Dictionary getDictionary(String text) {
        PriorityQueue<Node> nodes = analyze(text);
        Node root = treefy(nodes);

        Map<Integer, List<Boolean>> map = new HashMap<>();
        root.populate(map);
        return new Dictionary(map);
    }

    private static class Node implements Comparable<Node> {
        private Integer codePoint;
        private long frequency;
        private Node left;
        private Node right;

        Node(int codePoint, long frequency) {
            this.codePoint = codePoint;
            this.frequency = frequency;
        }

        Node(Node lessFrequent, Node moreFrequent) {
            this.left = moreFrequent;
            this.right = lessFrequent;
            this.frequency = lessFrequent.frequency + moreFrequent.frequency;
        }

        public void populate(Map<Integer, List<Boolean>> map) {
            if (this.left == null && this.right == null) {
                LinkedList<Boolean> val = new LinkedList<>();
                val.add(false);
                fill(map, val);
                return;
            }

            fill(map, new LinkedList<>());
        }

        private void fill(Map<Integer, List<Boolean>> map, List<Boolean> code) {
            if (codePoint != null) {
                map.put(codePoint, code);
                return;
            }

            LinkedList<Boolean> leftCode;
            leftCode = new LinkedList<>(code);
            leftCode.add(false);
            left.fill(map, leftCode);

            LinkedList<Boolean> rightCode;
            rightCode = new LinkedList<>(code);
            rightCode.add(true);
            right.fill(map, rightCode);
        }

        @Override
        public int compareTo(Node other) {
            return (int)(this.frequency - other.frequency);
        }
    }

    private static class Accumulator {
        Dictionary dictionary;
        List<Byte> l;
        Queue<Boolean> toProcess;

        Accumulator(Dictionary dictionary) {
            this.dictionary = dictionary;
            this.l = new ArrayList<>();
            this.toProcess = new LinkedList<>();
        }

        void accumulate(int i) {
            toProcess.addAll(dictionary.getBits(i));
        }

        void process() {
            while (toProcess.size() > 8) {
                packByte(8);
            }
            int size = toProcess.size();
            if (size > 0) {
                dictionary.setBitsInLastByte(size);
                packByte(size);
            }

        }

        private void packByte(int length) {
            byte result = 0;
            for (int i = 0; i < length; i++) {
                if (toProcess.remove()) {
                    result |= (1<<i);
                }
            }
            l.add(result);
        }

        void write(OutputStream out) throws IOException {
            process();
            out.write(getBytes());
        }

        private byte[] getBytes(){
            byte[] result = new byte[l.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = l.get(i);
            }
            return result;
        }

    }
}
