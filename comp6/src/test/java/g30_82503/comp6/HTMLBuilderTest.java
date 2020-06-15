/**
 * 
 */
package g30_82503.comp6;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Catarina Afonso
 *
 */
public class HTMLBuilderTest {
	
	HTMLBuilder htmlBuilder;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		this.htmlBuilder= new HTMLBuilder();
	}
	

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link g30_82503.comp6.HTMLBuilder#htmlTableBuilder(java.lang.String, boolean, int, int)}.
	 */
	@Test
	void testHtmlTableBuilder() {
		htmlBuilder.htmlTableBuilder("teste1", true, 1, 1);
		htmlBuilder.htmlTableBuilder(null, false, 1, 1);		
		
	}

	/**
	 * Test method for {@link g30_82503.comp6.HTMLBuilder#addTableHeader(java.lang.String[])}.
	 */
	@Test
	void testAddTableHeader() {
		htmlBuilder.htmlTableBuilder(null, true, 1, 1);
		htmlBuilder.addTableHeader("vai", "dar", "erro");
		
		htmlBuilder.htmlTableBuilder(null, true, 1, 4);
		htmlBuilder.addTableHeader("não","vai", "dar", "erro");
		
	}

	/**
	 * Test method for {@link g30_82503.comp6.HTMLBuilder#addRowValues(java.lang.String[])}.
	 */
	@Test
	void testAddRowValues() {
		htmlBuilder.htmlTableBuilder(null, true, 2, 2);
		htmlBuilder.addTableHeader("1", "2");
		htmlBuilder.addRowValues("test", "addrows");
		htmlBuilder.addRowValues("test", "addrows");
	}

	/**
	 * Test method for {@link g30_82503.comp6.HTMLBuilder#preString(java.util.List)}.
	 */
	@Test
	void testPreString() {
		List<String> file= new ArrayList<String>();
		file.add("<novo ficheiro de teste>");
		file.add("ES21920");
		List<String> a = htmlBuilder.preString(file);
		
	}

	/**
	 * Test method for {@link g30_82503.comp6.HTMLBuilder#stringHtml(java.util.List)}.
	 */
	@Test
	void testStringHtml() {
		List<String> file= new ArrayList<String>();
		file.add("novo ficheiro de teste");
		file.add("ES21920");
		htmlBuilder.stringHtml(file);
		
	}

	/**
	 * Test method for {@link g30_82503.comp6.HTMLBuilder#buildTable(java.util.List, java.util.List)}.
	 */
	@Test
	void testBuildTable() {
		List<String> nFile1= new ArrayList<String>();
		List<String> oFile1= new ArrayList<String>();
		
		nFile1.add("1");
		nFile1.add("2");
		nFile1.add("3");
		
		oFile1.add("3");
		oFile1.add("2");
		oFile1.add("1");

		htmlBuilder.buildTable(nFile1,oFile1);
	}
	
	/**
	 * Test method for {@link g30_82503.comp6.HTMLBuilder#diffOcurrences(java.util.List, java.util.List)}.
	 * @throws IOException 
	 */
	@Test
	void testdiffOcurrences() throws IOException {
		List<String> nFile1= new ArrayList<String>();
		List<String> oFile1= new ArrayList<String>();
		
		nFile1.add("1");
		nFile1.add("2");
		nFile1.add("3");
		
		
		oFile1.add("1");
		oFile1.add("2");
		oFile1.add("1");
		
		htmlBuilder.diffOcurrences(oFile1, nFile1);
		nFile1.add("4");
		htmlBuilder.diffOcurrences(oFile1, nFile1);
		oFile1.add("4");
		oFile1.add("4");
		htmlBuilder.diffOcurrences(oFile1, nFile1);
		
		
		
	}

}
