package com.db.ems.repository;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.db.ems.common.Utils;
import com.db.ems.config.AppProperties;

/**
 * 
 * @author vijaysingh
 *
 * Implementation of DataStore Interface to use file as storage.
 * 
 */
@Repository
public class FileStore implements DataStore<Document> {
	
	private static final Logger log = LoggerFactory.getLogger(FileStore.class);

	private String filePath;
	private SAXBuilder saxBuilder;
	private XMLOutputter xmlOutput;
	
	public FileStore(AppProperties props) {
		String resourceName = props.getProperty("employee.data.filename.xml");
		this.filePath = Utils.getFilePath(resourceName);
		this.saxBuilder = new SAXBuilder();
		this.xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		log.info("File loaded for reading and writing employee data is " + this.filePath);
	}
	
	@Override
	public void write(Document document) {
		try (FileOutputStream out = new FileOutputStream(filePath)) {
			xmlOutput.output(document, out);
		} catch (IOException e) {
			log.error("Exception while writing file: " + filePath, e);
		}
		log.info("Record written successfully.");
	}

	@Override
	public Document read() {
		Document document = null;
		try {
			document = saxBuilder.build(new FileReader(filePath));
		} catch (JDOMException | IOException e) {
			log.error("Exception while reading file: " + filePath, e);
		}
		return document;
	}

}
