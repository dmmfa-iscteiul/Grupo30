package covid_p4;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HtmlCovidTableTest {
	HtmlCovidTable html_table;
	GitHubGetData gData;
	File PATH_HTML = new File("C:\\Users\\Utilizador\\eclipse-workspace_3\\ComplementoEsII\\covid_p4.html");
	String REMOTE_URL="https://github.com/vbasto-iscte/ESII1920.git";
	File PATH = new File("C:\\Users\\Utilizador\\eclipse-workspace_3\\ComplementoEsII\\data");
	String REMOTE_URL_GRAPH="http://visualdataweb.de/webvowl/#iri=https://github.com/vbasto-iscte/ESII1920/raw/master/covid19spreading.rdf ";
	String wordToChangeLink = ("master");
	String fileName = ("covid19spreading.rdf");
	

	@BeforeEach
	void setUp() throws Exception {
		html_table = new HtmlCovidTable(5,PATH_HTML);
		gData = new GitHubGetData(REMOTE_URL,PATH,REMOTE_URL_GRAPH, wordToChangeLink, fileName);
	
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testHtmlCovidTable() {
		html_table = new HtmlCovidTable(5,PATH_HTML);
	}

	@Test
	void testAddTitleInformation() {
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("File Timestamp");
		headers.add("File Name");
		headers.add("File Tag");
		headers.add("Description");
		headers.add("Spread Visualization Link");
		html_table.createFile(headers, gData.getArraysInformation());
//		html_table.addTitleInformation(headers);
	}

	@Test
	void testAddSubInformation() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("test1");
		test.add("test2");
		test.add("test3");
		test.add("test4");
		test.add("test5");
		html_table.addSubInformation(test);
	}

}
