package com.daniilyurov.training.compressor;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The class that can read a text file, compress the text using Huffman Coding, and save it into a file.
 * The result file will contain two files:
 *    - the compressed text;
 *    - the dictionary holding keys to expand the text to original.
 */
public class TextCompressor {
    String text;
    Dictionary dictionary;

    /**
     * Reads the text from the first text file, compresses it using Huffman Coding,
     * and saves it into the second file.
     * @param uncompressedFile
     *        the file to read the original text from.
     * @param compressedFile
     *        the file to write the compressed version to.
     */
    public void compress(File uncompressedFile, File compressedFile) throws IOException {

        readText(uncompressedFile);

        FileOutputStream fileOutput = new FileOutputStream(compressedFile);
        ZipOutputStream zipOutput = new ZipOutputStream(fileOutput);

        addComponentToZip(zipOutput, this::compressText, "text_data");
        addComponentToZip(zipOutput, this::compressDictionary, "dictionary");

        zipOutput.close();
        fileOutput.close();
    }

    private void readText(File uncompressedFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(uncompressedFile));
        char[] buf = new char[4096];
        int length;
        StringBuilder result = new StringBuilder();
        while ((length = br.read(buf)) != -1) {
            result.append(buf, 0, length);
        }
        this.text = result.toString();
        this.dictionary = getDictionary(text);
    }

    private void addComponentToZip(ZipOutputStream targetZip, Consumer<OutputStream> algorithm, String componentName)
            throws IOException {
        ZipEntry zipEntry = new ZipEntry(componentName);
        targetZip.putNextEntry(zipEntry);
        algorithm.accept(targetZip);
    }

    private void compressDictionary(OutputStream dictionaryOutputStream) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(dictionaryOutputStream);
            os.writeObject(dictionary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void compressText(OutputStream textOutputStream) {
        try {
            Accumulator accumulator = new Accumulator(dictionary);
            text.codePoints().forEach(accumulator::addBits);
            accumulator.write(textOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // transforms PriorityQueue into tree and returns a root node
    private Node buildTree(PriorityQueue<Node> k) {
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

    private PriorityQueue<Node> analyze(String text) {
        PriorityQueue<Node> result = new PriorityQueue<>();
        Stream<Integer> stream = text.codePoints().mapToObj(Integer::new);
        Map<Integer, Long> map = stream.collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
        map.forEach((codePoint, frequency) -> result.add(new Node(codePoint, frequency)));
        return result;
    }

    private Dictionary getDictionary(String text) {
        PriorityQueue<Node> nodes = analyze(text);
        Node root = buildTree(nodes);

        Map<Integer, List<Boolean>> map = new HashMap<>();
        if (root != null) root.populate(map);
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

        void populate(Map<Integer, List<Boolean>> map) {
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
        List<Byte> bytes;
        Queue<Boolean> bits;

        Accumulator(Dictionary dictionary) {
            this.dictionary = dictionary;
            this.bytes = new ArrayList<>();
            this.bits = new LinkedList<>();
        }

        void addBits(int i) {
            bits.addAll(dictionary.getBits(i));
        }

        private void packBitsIntoBytes() {
            while (bits.size() > 8) {
                packByte(8);
            }
            int size = bits.size();
            if (size > 0) {
                dictionary.setBitsInLastByte(size);
                packByte(size);
            }
        }

        private void packByte(int length) {
            byte result = 0;
            for (int i = 0; i < length; i++) {
                if (bits.remove()) {
                    result |= (1<<i);
                }
            }
            bytes.add(result);
        }

        void write(OutputStream out) throws IOException {
            packBitsIntoBytes();
            out.write(getBytes());
        }

        private byte[] getBytes(){
            byte[] result = new byte[bytes.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = bytes.get(i);
            }
            return result;
        }

    }
}
