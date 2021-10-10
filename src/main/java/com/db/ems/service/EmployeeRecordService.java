package com.db.ems.service;

import static com.db.ems.common.Constants.NOT_AVAILABLE;
import static com.db.ems.common.Constants.XML_ATTRIBUTE_ID;
import static com.db.ems.common.Constants.XML_TAG_EMPLOYEE;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.db.ems.common.Utils;
import com.db.ems.repository.FileStore;

/**
 * 
 * @author vijaysingh
 *
 * Service class to Utilize FileStore Repository to read and write to file
 * This class acts as bridge between data store and controllers.
 * 
 */
@Service
public class EmployeeRecordService implements RecordService {

	private static final Logger log = LoggerFactory.getLogger(EmployeeRecordService.class);

	private FileStore fileStore;
	private SequenceHandler seqHandler;

	public EmployeeRecordService(FileStore fileStore, SequenceHandler seqHandler) {
		this.fileStore = fileStore;
		this.seqHandler = seqHandler;
	}

	public boolean addRecord(String newEmployeeXml) {
		try {
			log.debug("Adding New Employee. Request Payload: " + newEmployeeXml);
			Document document = fileStore.read();
			addNewEmployee(document, newEmployeeXml);
			fileStore.write(document);
		} catch (Exception e) {
			log.error("Exception while adding new employee record. ", e);
			return false;
		}
		return true;
	}

	public boolean deleteRecordById(String id) {
		try {
			log.info("Deleting Employee. Request id: " + id);
			Document document = fileStore.read();
			deleteEmployeeById(document, id);
			fileStore.write(document);
		} catch (Exception e) {
			log.error("Exception while deleting employee record with id = " + id, e);
			return false;
		}
		return true;
	}

	public boolean deleteRecordsByProperty(String property, String value) {
		try {
			log.info("Deleting All Employees with " + property + ": " + value);
			Document document = fileStore.read();
			deleteEmployeesByProperty(document, property, value);
			fileStore.write(document);
		} catch (Exception e) {
			log.error("Exception while deleting employee records with " + property + ": " + value, e);
			return false;
		}
		return true;
	}

	public String getAllRecords() {
		Document document = fileStore.read();
		return Utils.getXmlString(document);
	}

	public String getRecordById(String id) {
		log.info("Reading Employees with id = " + id);
		Document document = fileStore.read();
		String record = NOT_AVAILABLE;
		try {
			record = getEmployeeById(document, id);
		} catch (Exception e) {
			log.error("Exception while reading employee record with id = " + id, e);
		}
		return record;
	}

	private void addNewEmployee(Document document, String payload) throws FactoryConfigurationError, XMLStreamException,
			JDOMException, IOException, SAXException, ParserConfigurationException {

		Element rootElement = document.getRootElement();

		Reader xml = new StringReader(payload);
		SAXBuilder saxBuilder = new SAXBuilder();
		Document newDocument = saxBuilder.build(xml);
		Element payloadRootElement = newDocument.getRootElement();

		payloadRootElement.setAttribute(XML_ATTRIBUTE_ID, seqHandler.getNextSequenceNumber());
		rootElement.addContent(payloadRootElement.detach());

	}

	private void deleteEmployeeById(Document document, String empId)
			throws FactoryConfigurationError, XMLStreamException, JDOMException, IOException {

		Element rootElement = document.getRootElement();
		List<Element> employeeElements = rootElement.getChildren(XML_TAG_EMPLOYEE);

		employeeElements.removeIf(
				employeeElement -> employeeElement.getAttribute(XML_ATTRIBUTE_ID).getValue().equalsIgnoreCase(empId));

	}

	private void deleteEmployeesByProperty(Document document, String property, String value) {
		Element rootElement = document.getRootElement();
		List<Element> employeeElements = rootElement.getChildren(XML_TAG_EMPLOYEE);

		employeeElements
				.removeIf(employeeElement -> employeeElement.getChild(property).getValue().equalsIgnoreCase(value));

	}

	private String getEmployeeById(Document document, String empId)
			throws FactoryConfigurationError, XMLStreamException, JDOMException, IOException {

		Element rootElement = document.getRootElement();
		List<Element> employeeElements = rootElement.getChildren(XML_TAG_EMPLOYEE);

		List<Element> result = employeeElements.stream().filter(
				employeeElement -> employeeElement.getAttribute(XML_ATTRIBUTE_ID).getValue().equalsIgnoreCase(empId))
				.collect(Collectors.toCollection(ArrayList::new));

		return Utils.getXmlString(result);
	}

}
