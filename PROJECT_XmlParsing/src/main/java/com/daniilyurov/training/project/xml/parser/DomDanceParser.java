package com.daniilyurov.training.project.xml.parser;

import com.daniilyurov.training.project.xml.ParseException;
import com.daniilyurov.training.project.xml.ThreadUnsafe;
import com.daniilyurov.training.project.xml.model.Dance;
import com.daniilyurov.training.project.xml.model.Dancer;
import com.daniilyurov.training.project.xml.model.Performance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//TODO:add comment
@ThreadUnsafe
public class DomDanceParser implements DanceParser {

    private final String DANCE_NS = "http://www.daniilyurov.com/training/project/xml/dance";
    private final String DANCER_NS = "http://www.daniilyurov.com/training/project/xml/dancer";

    private DocumentBuilder builder;
    private Document document;

    public DomDanceParser() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        builder = factory.newDocumentBuilder();
    }

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
        setDocumentFromXmlStream(inputStream);
        return getDanceInstanceFromDocument();
    }

    private void setDocumentFromXmlStream(InputStream danceXmlStream) throws IOException, ParseException {
        try {
            document = builder.parse(danceXmlStream);
        } catch (SAXException e) {
            throw new ParseException(e);
        }
    }

    private Dance getDanceInstanceFromDocument() {
        Dance dance = new Dance();
        dance.setPerformances(getPerformanceListFromDocument());
        return dance;
    }

    private List<Performance> getPerformanceListFromDocument() {
        List<Performance> performanceList = new ArrayList<>();
        populatePerformanceListWithPerformancesFromDocument(performanceList);
        return performanceList;
    }

    private void populatePerformanceListWithPerformancesFromDocument(List<Performance> performanceList) {
        NodeList performanceNodes = document.getElementsByTagNameNS(DANCE_NS, Dance.Element.PERFORMANCE);
        for (int index = 0; index < performanceNodes.getLength(); index++) {
            performanceList.add(getPerformanceInstanceFromNode(performanceNodes, index));
        }
    }

    private Performance getPerformanceInstanceFromNode(NodeList performanceNodes, int index) {
        Element performanceElement = (Element) performanceNodes.item(index);
        Element dancersElement = (Element) performanceElement.getElementsByTagNameNS(DANCER_NS,
                Performance.Element.DANCERS).item(0);
        return getPerformance(performanceElement, dancersElement);
    }

    private Performance getPerformance(Element performanceElement, Element dancersElement) {
        Performance performance = new Performance();
        setAttributes(performanceElement, dancersElement, performance);
        setElements(performanceElement, dancersElement, performance);
        return performance;
    }

    private void setElements(Element performanceElement, Element dancersElement, Performance performance) {
        performance.setType(getTextFromElement(performanceElement, Performance.Element.TYPE, DANCE_NS));
        performance.setScene(getTextFromElement(performanceElement, Performance.Element.SCENE, DANCE_NS));
        performance.setNumberOfDancers(getTextFromElement(performanceElement,
                Performance.Element.NUMBER_OF_DANCERS, DANCE_NS));
        performance.setMusic(getTextFromElement(performanceElement, Performance.Element.MUSIC, DANCE_NS));
        performance.setDancers(getDancers(dancersElement));
    }

    private void setAttributes(Element performanceElement, Element dancersElement, Performance performance) {
        performance.setNumber(performanceElement.getAttribute(Performance.Attribute.NUMBER));
        performance.setGroupName(getGroupName(dancersElement, Performance.Attribute.GROUP_NAME));
    }

    private String getGroupName(Element dancersElement, String groupName) {
        String result = dancersElement.getAttribute(groupName);
        return result.equals("") ? null : result;
    }

    private List<Dancer> getDancers(Element dancersElement) {
        List<Dancer> result = new ArrayList<>();
        populateWithDancers(result, dancersElement);
        return result;
    }

    private void populateWithDancers(List<Dancer> result, Element dancersElement) {
        NodeList dancers = dancersElement.getElementsByTagNameNS(DANCER_NS, Dancer.DANCER);
        for (int index = 0; index < dancers.getLength(); index++) {
            result.add(getParsedDancerInstance(dancers, index));
        }
    }

    private Dancer getParsedDancerInstance(NodeList dancers, int index) {
        Element dancerElement = (Element) dancers.item(index);
        return getDancer(dancerElement);
    }

    private Dancer getDancer(Element dancerElement) {
        Dancer dancer = new Dancer();
        setElements(dancerElement, dancer);
        return dancer;
    }

    private void setElements(Element dancerElement, Dancer dancer) {
        dancer.setName(getTextFromElement(dancerElement, Dancer.Element.NAME, DANCER_NS));
        dancer.setDescription(getTextFromElement(dancerElement, Dancer.Element.DESCRIPTION, DANCER_NS));
        dancer.setAge(getIntFromElement(dancerElement, Dancer.Element.AGE, DANCER_NS));
        dancer.setYears(getIntFromElement(dancerElement, Dancer.Element.YEARS, DANCER_NS));
    }

    private int getIntFromElement(Element dancerElement, String childElementName, String childElementNamespace) {
        String ageString = getTextFromElement(dancerElement, childElementName, childElementNamespace);
        return Integer.parseInt(ageString == null ? "0" : ageString);
    }

    private String getTextFromElement(Element parent, String childElementName, String childElementNamespace) {
        Element element = (Element) parent.getElementsByTagNameNS(childElementNamespace, childElementName).item(0);
        if (element == null) return null;
        return element.getTextContent();
    }
}
