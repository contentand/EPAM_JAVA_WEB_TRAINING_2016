package com.daniilyurov.training.project.xml.parser;

import com.daniilyurov.training.project.xml.ParseException;
import com.daniilyurov.training.project.xml.model.Dance;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

//TODO: doc comments
public interface DanceParser {
    Dance parse(String filePath) throws ParseException, IOException;

    Dance parse(File file) throws ParseException, IOException;

    Dance parse(InputStream danceXmlStream) throws ParseException, IOException;
}
