package com.java.fileconverter.test;

import org.junit.BeforeClass;
import org.junit.Test;

import com.java.fileconverter.Convert;

public class ConvertTest {

	static String[] args = new String[2] ;
	static String[] args2 = new String[2] ;
	static String[] args3 = new String[2] ;

	@BeforeClass
	public static void testSetup() {
		args[0] = "input.txt";
		args[1] = "xml";
		
		args2[0] = "input.xml";
		args2[1] = "text";
	}

	@SuppressWarnings("static-access")
	@Test
	public void testMain() {
		Convert convert = new Convert();
		
		convert.main(args);
		convert.main(args2);
		convert.main(args3);
	}

}
