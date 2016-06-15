package com.daniilyurov.training.project.xml.validator;

import com.daniilyurov.training.project.xml.ThreadUnsafe;

//TODO: doc comments
@ThreadUnsafe
public class DanceXmlValidator extends XmlValidator {

    public DanceXmlValidator() {
        setup("PROJECT_XmlParsing\\target\\classes\\dance.xsd");
    }

}
