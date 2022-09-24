import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JUnitTester {
	static File f;
	
	@BeforeAll
	static void setUpBefore() throws IOException {
		f = new File("Erik's test file");
		FileWriter ike = new FileWriter(f);
		ike.write("this is erik's new test file");
		ike.close();
		
	}
	
	@AfterAll
	static void shutDownAfter() throws IOException {
		f.delete();
	}
	
	@Test
	static void testBlob() throws IOException {
		
		RBlob b = new RBlob("Erik's test file");
		
		String temp = "";
		File test = new File(".\\objects\\"+b.getSha1FileName());
		assertTrue(test.exists());
		
		BufferedReader mike = new BufferedReader(new FileReader(".\\objects\\"+b.getSha1FileName()));
		while (mike.ready())
			temp += (char)mike.read();
		mike.close();
		
		System.out.print(temp);
		assertTrue(!temp.equals("this is erik's new test file"));
		
	}
	
	@Test
	static void testIndex() throws IOException {
		FileWriter ike = new FileWriter(f);
		RIndex i = new RIndex();
		i.init();
		
		File testIndex = new File("index");
		assertTrue(testIndex.exists());
		
		File testObject = new File(".\\object\\");
		assertTrue(testObject.exists());
		
		i.add("something");
		File testBlob = new File(".\\object\\94e66df8cd09d410c62d9e0dc59d3a884e458e05");
		assertTrue(testBlob.exists());
		i.add("bar.txt");
		testBlob = new File(".\\object\\78c9a53e2f28b543ea62c8266acfdf36d5c63e61");
		assertTrue(testBlob.exists());
		i.remove("bar.txt");
		assertTrue(!testBlob.exists());
	}
	
	@Test
	static void testBlobSet() throws IOException {
		ArrayList<String> testList = new ArrayList<String>();
		RTreeHelper help = new RTreeHelper();
		String s = "";
		String temp = "blob : 94e66df8cd09d410c62d9e0dc59d3a884e458e05";
		testList.add(temp);
		s+=temp;
		temp = "blob : 78c9a53e2f28b543ea62c8266acfdf36d5c63e61";
		testList.add(temp);
		s+=temp;
		RTree set = new RTree(testList);
		File testFile = new File(".\\objects\\"+set.getSetName());
//		assertTrue(help.readContents(testFile).equals(s));
	}
	
	@Test
	void test() throws IOException {
		testBlob();
		testIndex();
		testBlobSet();
//		fail("Not yet implemented");
	}
	
}
