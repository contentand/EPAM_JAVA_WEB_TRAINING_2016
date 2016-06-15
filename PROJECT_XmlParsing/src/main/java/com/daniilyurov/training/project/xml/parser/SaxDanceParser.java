package com.daniilyurov.training.project.xml.parser;

import com.daniilyurov.training.project.xml.ParseException;
import com.daniilyurov.training.project.xml.ThreadUnsafe;
import com.daniilyurov.training.project.xml.model.Dance;
import com.daniilyurov.training.project.xml.model.Dancer;
import com.daniilyurov.training.project.xml.model.Performance;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//TODO: doc comments
@ThreadUnsafe
public class SaxDanceParser extends DefaultHandler implements DanceParser {

    private Dance performances;
    private List<Dancer> dancers;
    private Performance performance;
    private Dancer dancer;
    private String current = "";
    private StringBuilder text;
    private SAXParser parser;

    public SaxDanceParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        this.parser = factory.newSAXParser();

    }

    @Override // DanceParser Interface
    public Dance parse(String filePath) throws ParseException, IOException {
        File file = new File(filePath);
        return parse(file);
    }

    @Override // DanceParser Interface
    public Dance parse(File file) throws ParseException, IOException {
        InputStream inputStream = new FileInputStream(file);
        return parse(inputStream);
    }

    @Override // DanceParser Interface
    public Dance parse(InputStream danceXmlStream) throws IOException, ParseException {
        try {
            parser.parse(danceXmlStream, this);
        } catch (SAXException e) {
            throw new ParseException(e);
        }
        return performances;
    }

    @Override // DefaultHandler Class
    public void error(SAXParseException e) throws SAXException {
        System.out.println("ERROR at line " + e.getLineNumber() + " of the Xml file: " + e.getMessage());
    }

    @Override // DefaultHandler Class
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        current = localName;

        switch (current) {
            case Dance.DANCE:
                performances = new Dance();
                performances.setPerformances(new ArrayList<>());
                break;
            case Dance.Element.PERFORMANCE:
                performance = new Performance();
                String number = attributes.getValue(Performance.Attribute.NUMBER);
                performance.setNumber(number);
                performances.getPerformances().add(performance);
                break;
            case Performance.Element.DANCERS:
                dancers = new ArrayList<>();
                String groupName = attributes.getValue(Performance.Attribute.GROUP_NAME);
                performance.setGroupName(groupName);
                performance.setDancers(dancers);
                break;
            case Dancer.DANCER:
                dancer = new Dancer();
                dancers.add(dancer);
                break;
            default:
                text = new StringBuilder();
                break;
        }
    }

    @Override // DefaultHandler Class
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (current.equals(Dance.DANCE) || current.equals(Dance.Element.PERFORMANCE)
                || current.equals(Performance.Element.DANCERS) || current.equals(Dancer.DANCER)) {
            return;
        }

        String content = text.toString().trim();
        switch (current) {
            case Dancer.Element.AGE:
                int age = Integer.parseInt(content);
                dancer.setAge(age);
                break;
            case Dancer.Element.NAME:
                dancer.setName(content);
                break;
            case Dancer.Element.YEARS:
                int years = Integer.parseInt(content);
                dancer.setYears(years);
                break;
            case Dancer.Element.DESCRIPTION:
                dancer.setDescription(content);
                break;
            case Performance.Element.TYPE:
                performance.setType(content);
                break;
            case Performance.Element.SCENE:
                performance.setScene(content);
                break;
            case Performance.Element.NUMBER_OF_DANCERS:
                performance.setNumberOfDancers(content);
                break;
            case Performance.Element.MUSIC:
                performance.setMusic(content);
                break;
        }
    }

    @Override // DefaultHandler Class
    public void endDocument() throws SAXException { //TODO: do you really need this method?
        current = "";
    }

    @Override // DefaultHandler Class
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (text != null) {
            text.append(ch, start, length);
        }
    }

}
