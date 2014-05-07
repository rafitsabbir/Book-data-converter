package com.java.fileconverter;

import javax.xml.parsers.ParserConfigurationException;

public class Convert {
	public static void main(String args[]) {

		ReadingInput readingInput;
		DataConverter dataconverter;

		String fileName = "";
		String targrtFormat = "";
		String extention = "";

		try {
			fileName = args[0];
			targrtFormat = args[1];

		} catch (Exception e) {
			e.printStackTrace();
		}

		// reading input file data
		readingInput = new ReadingInput(fileName);
		//detect extention
		extention = readingInput.DetectFormat(targrtFormat);

		// convert data
		dataconverter = new DataConverter(fileName);
		try {
			dataconverter.convertData(extention, targrtFormat);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

}
