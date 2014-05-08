package com.java.fileconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class ReadingInput {
	String filePath = "";
	String format = "";

	public ReadingInput(String fileName) {

		filePath = "inputfiles/" + fileName;

		try (BufferedReader reader = new BufferedReader(
				new FileReader(filePath))) {
			String currentLine;

			System.out.println("Reading input ...");
			System.out.println("++++");

			while ((currentLine = reader.readLine()) != null) {
				System.out.println(currentLine);
			}
			System.out.println("----");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String DetectFormat(String targrtFormat) {
		// TODO Auto-generated method stub
		File file = new File(filePath);

		try {
			String extention = Files.probeContentType(file.toPath());

			if (filePath.substring(filePath.length() - 4, filePath.length())
					.equalsIgnoreCase("json")) {
				format = "json";
			} else if (extention.contains("xml")) {
				format = "xml";
			} else if (extention.contains("text")) {
				format = "text";
			}
			System.out.println("Guessing text format ...");
			System.out.println("Book data is in " + format + " format");
			System.out.println("Converting to " + targrtFormat + " format");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return format;
	}
}
