package com.java.fileconverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

public class XmlToJsonConverter {

	public void convert(String filePath) {
		// TODO Auto-generated method stub
		File fXmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document doc = null;
		boolean exitConversion = false;

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

		List<Book> bookList = new ArrayList<>();
		Gson gson = new Gson();
		ArrayList<String> authorlist;
		Book bookInfo;

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			bookInfo = new Book();
			authorlist = new ArrayList<>();

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				try {
					if (eElement.getElementsByTagName("isbn").item(0)
							.getTextContent() != null) {
					}
				} catch (NullPointerException n) {
					System.out
							.print("ISBN number is missing in next book. Conversion exiting...");
					exitConversion = true;
					break;
				}

				for (int i = 0; i < eElement.getElementsByTagName("author")
						.getLength(); i++) {
					authorlist.add(eElement.getElementsByTagName("author")
							.item(i).getTextContent().toString().trim()
							.replace("\n", ""));
				}

				bookInfo.setName(eElement.getElementsByTagName("name").item(0)
						.getTextContent().trim().replace("\n", "")
						.replace("\t", ""));
				bookInfo.setAuthors(authorlist);
				bookInfo.setPublishedDate(eElement
						.getElementsByTagName("published-date").item(0)
						.getTextContent().trim().replace("\n", "")
						.replace("\t", ""));
				bookInfo.setIsbn(eElement.getElementsByTagName("isbn").item(0)
						.getTextContent().trim().replace("\n", "")
						.replace("\t", ""));

				bookList.add(bookInfo);
			}
		}

		String json = gson.toJson(bookList);

		try {
			if (!exitConversion) {
				// write converted json data to a file
				FileWriter writer = new FileWriter("outputfiles/output.json");
				writer.write(json);
				writer.close();

				System.out.println(json);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
