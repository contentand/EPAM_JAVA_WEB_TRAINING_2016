package com.daniilyurov.training.project.xml.parser;

import com.daniilyurov.training.project.xml.ParseException;
import com.daniilyurov.training.project.xml.ThreadUnsafe;
import com.daniilyurov.training.project.xml.model.Dance;
import com.daniilyurov.training.project.xml.model.Dancer;
import com.daniilyurov.training.project.xml.model.Performance;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

//TODO: comment
@ThreadUnsafe
public class StAxDanceParser implements DanceParser {

    private Dance dance;
    private Performance performance;
    private Dancer dancer;

    @Override
    public Dance parse(String filePath) throws ParseException, IOException {
        File file = new File(filePath);
        return parse(file);
    }

    @Override
    public Dance parse(File file) throws ParseException, IOException {
        InputStream inputStream = new FileInputStream(file);
        return parse(inputStream);
    }

    @Override
    public Dance parse(InputStream inputStream) throws ParseException, IOException {

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader;
        try {
            reader = factory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLEvent.START_ELEMENT) {
                    String element = reader.getLocalName();
                    switch (element) {
                        case Dance.DANCE:
                            dance = new Dance();
                            dance.setPerformances(new ArrayList<>());
                            break;
                        case Dance.Element.PERFORMANCE:
                            performance = new Performance();
                            performance.setNumber(reader.getAttributeValue("", Performance.Attribute.NUMBER));
                            dance.getPerformances().add(performance);
                            break;
                        case Performance.Element.TYPE:
                            performance.setType(reader.getElementText());
                            break;
                        case Performance.Element.SCENE:
                            performance.setScene(reader.getElementText());
                            break;
                        case Performance.Element.NUMBER_OF_DANCERS:
                            performance.setNumberOfDancers(reader.getElementText());
                            break;
                        case Performance.Element.MUSIC:
                            performance.setMusic(reader.getElementText());
                            break;
                        case Performance.Element.DANCERS:
                            performance.setDancers(new ArrayList<>());
                            performance.setGroupName(reader.getAttributeValue("", Performance.Attribute.GROUP_NAME));
                            break;
                        case Dancer.DANCER:
                            dancer = new Dancer();
                            performance.getDancers().add(dancer);
                            break;
                        case Dancer.Element.NAME:
                            dancer.setName(reader.getElementText());
                            break;
                        case Dancer.Element.DESCRIPTION:
                            dancer.setDescription(reader.getElementText());
                            break;
                        case Dancer.Element.AGE:
                            dancer.setAge(Integer.parseInt(reader.getElementText()));
                            break;
                        case Dancer.Element.YEARS:
                            dancer.setYears(Integer.parseInt(reader.getElementText()));
                            break;
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return dance;
    }
}
