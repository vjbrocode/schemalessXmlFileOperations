package com.db.ems.common;

import static com.db.ems.common.Constants.NOT_AVAILABLE;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.util.ResourceUtils;

public class Utils {
	
	private static XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
	
	public static String getFilePath(String resourceName) {
		String filePath = NOT_AVAILABLE;
		try {
			File file = ResourceUtils.getFile(resourceName);
			filePath = file.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}
	
	public static String getXmlString(Document document) {
		return xmlOutput.outputString(document);
	}
	
	public static String getXmlString(Element element) {
		return xmlOutput.outputString(element);
	}
	
	public static String getXmlString(List<Element> elements) {
		return xmlOutput.outputString(elements);
	}
	
	public static String wrapRootTag(List<Element> elements, String rootTag) {
		Element root = new Element(rootTag);
		root.addContent(elements);
		return getXmlString(root);
	}
}
