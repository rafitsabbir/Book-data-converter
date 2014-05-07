package com.java.fileconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DataConverter {
	String filePath = "";

	public DataConverter(String fileName) {
		// TODO Auto-generated constructor stub
		filePath = "inputfiles/" + fileName;
	}

	public void convertData(String extention, String targrtFormat)
			throws ParserConfigurationException {
		// TODO Auto-generated method stub

		if (extention.equalsIgnoreCase("text")
				&& targrtFormat.equalsIgnoreCase("xml")) {
			try {
				try {
					convertTextToXml();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (extention.equalsIgnoreCase("xml")
				&& targrtFormat.equalsIgnoreCase("text")) {
			convertXmlToText();
		}
	}

	private void convertXmlToText() throws ParserConfigurationException {
		File fXmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = null;
		String txtString = "";

		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("book");

		System.out.println("Here is the output ...\n+++");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			/*System.out.println("\nCurrent Element :" + nNode.getNodeName());*/

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				String authorName = "";

				System.out.println("name : "
						+ eElement.getElementsByTagName("name").item(0)
								.getTextContent());

				for (int i = 0; i < eElement.getElementsByTagName("author")
						.getLength(); i++) {
					authorName += eElement.getElementsByTagName("author")
							.item(i).getTextContent().toString().trim()
							.replace("\n", "")
							+ ", ";
				}

				authorName = authorName.substring(0, authorName.length() - 2);

				System.out.println("authors : " + authorName);

				System.out.println("\npublished-date : "
						+ eElement.getElementsByTagName("published-date")
								.item(0).getTextContent() + "\n----");

				txtString += "name : "
						+ eElement.getElementsByTagName("name").item(0)
								.getTextContent()
						+ "\nauthors : "
						+ authorName
						+ "\n\npublished-date : "
						+ eElement.getElementsByTagName("published-date")
								.item(0).getTextContent();
			}
		}

		// save into txt format

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("outputfiles/output.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileWriter.write(txtString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void convertTextToXml() throws SAXException,
			ParserConfigurationException, IOException, TransformerException {
		// TODO Auto-generated method stub
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<book xmlns:b=\"http://example.com/programming/test/book\">";

		try (BufferedReader reader = new BufferedReader(
				new FileReader(filePath))) {
			String currentLine;

			while ((currentLine = reader.readLine()) != null) {
				if (currentLine.length() > 0) {
					// split using ":"
					String[] parts = currentLine.split(":");
					String part1 = parts[0]; // tag name
					String part2 = parts[1]; // value

					if (part1.trim().equalsIgnoreCase("authors")) {
						xmlString += "<" + part1.trim() + ">";

						// split multiple authors
						String[] author = part2.split(",");

						for (int i = 0; i < author.length; i++) {
							xmlString += "<author>" + author[i].trim()
									+ "\n</author>";
						}
						xmlString += "</" + part1.trim() + ">";
					} else {
						xmlString += "<" + part1.trim() + ">" + part2.trim()
								+ "\n</" + part1.trim() + ">";
					}
				}
			}
			xmlString += "</book>";

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Here is the output... \n ++++");
		System.out.println(xmlString + "\n ----");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(
				new StringReader(xmlString)));

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
