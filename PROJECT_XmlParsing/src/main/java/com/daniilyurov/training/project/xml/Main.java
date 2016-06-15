package com.daniilyurov.training.project.xml;

import com.daniilyurov.training.project.xml.model.Dance;
import com.daniilyurov.training.project.xml.parser.DanceParser;
import com.daniilyurov.training.project.xml.parser.SaxDanceParser;
import com.daniilyurov.training.project.xml.transformer.XmlTransformer;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException,
            ParseException, TransformerException {
        DanceParser p = new SaxDanceParser();
        Dance pfs = p.parse("PROJECT_XmlParsing\\target\\classes\\dance.xml");
        System.out.println(pfs);

        XmlTransformer.transform("PROJECT_XmlParsing/target/classes/dance.xml", "PROJECT_XmlParsing/res.html");
    }
}
