package com.daniilyurov.training.project.xml.validator;

import com.daniilyurov.training.project.xml.ThreadUnsafe;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//TODO: doc comments
@ThreadUnsafe
public abstract class XmlValidator {
    protected Validator validator;

    public void validate(InputStream xmlInput) throws IOException, SAXException {
        this.validator.validate(new SAXSource(new InputSource(xmlInput))); //TODO: error output where what
    }

    protected void setup(String schemaLocation) {
        File schemaFile = new File(schemaLocation);

        try {
            FileInputStream input = new FileInputStream(schemaFile);
            InputSource inputSource = new InputSource(input);
            Source source = new SAXSource(inputSource);
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = factory.newSchema(source);
            this.validator = schema.newValidator();
        } catch (IOException | SAXException e) {
            throw new IllegalStateException(e);
        }
    }
}
