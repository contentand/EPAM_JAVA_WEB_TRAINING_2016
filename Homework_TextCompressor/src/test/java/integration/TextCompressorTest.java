package integration;

import com.daniilyurov.training.compressor.TextCompressor;
import com.daniilyurov.training.compressor.TextExpander;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TextCompressorTest {
    public File uncompressedFile;
    public File compressedFile;
    public String textBeforeCompression;

    @Before
    public void setup() throws IOException {
        uncompressedFile = new File("src/test/resources/book.txt");
        compressedFile = new File("data.dt");
        textBeforeCompression = getText();
    }

    @Test
    public void compressAndDecompressText_textStaysTheSame() throws IOException, ClassNotFoundException {
        TextCompressor textCompressor = new TextCompressor();
        textCompressor.compress(uncompressedFile, compressedFile);

        TextExpander textExpander = new TextExpander();
        String textAfterDecompression = textExpander.expand(compressedFile);

        assertEquals(textBeforeCompression, textAfterDecompression);
    }

    @Test
    public void compressText_compressedFilesAreLighterThanTheOriginalFile() throws IOException {
        TextCompressor textCompressor = new TextCompressor();
        textCompressor.compress(uncompressedFile, compressedFile);

        long uncompressedFileSize = uncompressedFile.length();
        long compressedFileSize = getComponentSize(compressedFile);

        assertTrue(uncompressedFileSize > compressedFileSize);
    }

    private long getComponentSize(File compressedFile) throws IOException {
        FileInputStream fileInput = new FileInputStream(compressedFile);
        ZipInputStream zipInput = new ZipInputStream(fileInput);
        long result = 0;

        ZipEntry entry;
        while ((entry = zipInput.getNextEntry()) != null) {
            result += entry.getSize();
        }

        zipInput.close();
        fileInput.close();
        return result;
    }

    private String getText() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(uncompressedFile));
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
        if (!compressedFile.delete()){
            throw new IllegalStateException("File has not been closed!");
        }
    }

}
