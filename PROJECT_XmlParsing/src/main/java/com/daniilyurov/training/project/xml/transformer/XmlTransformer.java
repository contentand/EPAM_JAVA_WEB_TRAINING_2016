package com.daniilyurov.training.project.xml.transformer;

import com.daniilyurov.training.project.xml.ThreadSafe;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

//TODO: comment
//UTILITY CLASS cannot be extended!
@ThreadSafe
public final class XmlTransformer {

    //Private constructor to prevent instantiation.
    private XmlTransformer(){}

    public static void transform(String sourceXmlFileName, String resultHtmlFileName) throws FileNotFoundException,
            TransformerException {
        File sourceXmlFile = new File(sourceXmlFileName);
        File resultHtmlFile = new File(resultHtmlFileName);
        transform(sourceXmlFile, resultHtmlFile);
    }

    public static void transform(File sourceXmlFile, File resultHtmlFile) throws FileNotFoundException,
            TransformerException {
        InputStream sourceXml = new FileInputStream(sourceXmlFile);
        OutputStream resultHtml = new FileOutputStream(resultHtmlFile);
        transform(sourceXml, resultHtml);
    }

    public static void transform(InputStream sourceXml, OutputStream resultHtml) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer(new StreamSource("PROJECT_XmlParsing/target/classes/dance.xsl"));
        t.transform(new StreamSource(sourceXml), new StreamResult(resultHtml));
    }
}
