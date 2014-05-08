package com.java.fileconverter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class JsonToXmlConverter {

	@SuppressWarnings("unchecked")
	public void convert(String filePath) throws ParserConfigurationException,
			SAXException, IOException, TransformerException {
		// TODO Auto-generated method stub

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;
		boolean exitConversion = false;

		try {
			jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<booklist xmlns:b=\"http://example.com/programming/test/book\">";

		int lineCounter = 0;
		ArrayList<String> authorlist;

		for (Object bookObj : jsonArray) {
			JSONObject book = (JSONObject) bookObj;

			authorlist = new ArrayList<>();
			lineCounter++;

			if (lineCounter == 4) {
				xmlString += "</book>\n<book>";
				lineCounter = 0;
			}
			xmlString += "\n<book>";

			String name = (String) book.get("name");
			xmlString += "<name>" + name.trim() + "\n</name>";

			authorlist = (ArrayList<String>) book.get("authors");

			for (int i = 0; i < authorlist.size(); i++) {
				xmlString += "<author>" + authorlist.get(i).trim()
						+ "\n</author>";
			}

			String publishedDate = (String) book.get("publishedDate");
			xmlString += "<publishedDate>" + publishedDate.trim()
					+ "\n</publishedDate>";

			String isbn;
			try {
				isbn = (String) book.get("isbn");
				xmlString += "<isbn>" + isbn.trim() + "\n</isbn>";
			} catch (Exception e) {
				System.out
						.print("ISBN number is missing in next book. Conversion exiting...");
				exitConversion = true;
				break;
			}

			xmlString += "\n</book>";

		}

		xmlString += "\n</booklist>";

		if (!exitConversion) {

			System.out.println(xmlString);

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(
					xmlString)));

			// Write the parsed document to an xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(new File(
					"outputfiles/output.xml"));
			transformer.transform(source, result);

		}
	}

}
