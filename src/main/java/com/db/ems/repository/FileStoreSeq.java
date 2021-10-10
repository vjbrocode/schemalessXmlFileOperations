package com.db.ems.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.db.ems.common.Utils;
import com.db.ems.config.AppProperties;

@Repository
public class FileStoreSeq implements DataStore<String> {

	private static final Logger log = LoggerFactory.getLogger(FileStoreSeq.class);

	private String filePath;

	public FileStoreSeq(AppProperties props) {
		String resourceName = props.getProperty("employee.data.filename.sequence");
		this.filePath = Utils.getFilePath(resourceName);
		log.info("File loaded for reading and writing sequence is " + this.filePath);
	}

	@Override
	public void write(String currentSequenceNum) {
		try {
			int nextVal = Integer.parseInt(currentSequenceNum) + 1;
			FileWriter myWriter = new FileWriter(filePath);
			myWriter.write(String.valueOf(nextVal));
			myWriter.close();
		} catch (NumberFormatException e) {
			log.warn("Exception while parsing file number data: " + filePath, e);
			writeNumberDataToFile();
		} catch (IOException e) {
			log.error("Exception while writing file: " + filePath, e);
		}
	}

	@Override
	public String read() {
		String data = "";
		try {
			File myObj = new File(filePath);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				data = myReader.nextLine().trim();
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			log.error("Exception while reading file: " + filePath, e);
		}
		return data;
	}
	
	private void writeNumberDataToFile() {
		try {
			log.info("AUTO ERROR HANDLING: Writing number 1 in file: " + filePath);
			FileWriter myWriter = new FileWriter(filePath);
			myWriter.write("1");
			myWriter.close();
		} catch (IOException ex) {
			log.error("Exception while writing file: " + filePath, ex);
		}
	}

}
