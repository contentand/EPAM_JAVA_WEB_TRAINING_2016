package integration;

import com.daniilyurov.training.compressor.Compressor;
import com.daniilyurov.training.compressor.Expander;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;

public class CompressorTest {
    public File uncompressedText;
    public File dictionaryFile;
    public File textFile;

    @Before
    public void setup() {
        uncompressedText = new File("src/test/resources/book.txt");
        dictionaryFile = new File("dict.dk");
        textFile = new File("data.dt");
    }

    @Test
    public void compressAndDecompressText_textStaysTheSame() throws IOException, ClassNotFoundException {
        String textBeforeCompression = getText();

        Compressor compressor = new Compressor(textBeforeCompression);
        compressor.compress(new FileOutputStream(textFile), new FileOutputStream(dictionaryFile));

        Expander expander = new Expander();
        String textAfterDecompression = expander.decompress(new FileInputStream(textFile),
                new FileInputStream(dictionaryFile));

        assertEquals(textBeforeCompression, textAfterDecompression);
    }

    @Test
    public void compressText_compressedFilesAreLighterThanTheOriginalFile() throws IOException {
        String textBeforeCompression = getText();

        Compressor compressor = new Compressor(textBeforeCompression);
        compressor.compress(new FileOutputStream(textFile), new FileOutputStream(dictionaryFile));

        long dictionarySpace = dictionaryFile.length();
        long compressedDataSpace = textFile.length();

        long beforeCompression = uncompressedText.length();
        long afterCompression = dictionarySpace + compressedDataSpace;

        assertTrue(beforeCompression > afterCompression);
    }

    private String getText() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(uncompressedText));
        char[] buf = new char[4096];
        int length;
        StringBuilder result = new StringBuilder();
        while ((length = br.read(buf)) != -1) {
            result.append(buf, 0, length);
        }
        return result.toString();
    }

    @After
    public void cleanup() {
        dictionaryFile.delete();
        textFile.delete();
    }

}
