import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HtmlBuilderTest {

	
	HtmlBuilder html;
	File file = new File ("teste.htm");
	String text = "teste";
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		html = new HtmlBuilder (null, true, 2,4);
	}

	@Test
	public void testHtmlBuilder() {
		html = new HtmlBuilder(null, true, 2,4);
	}
	
	@Test
	public void testHtmlBuilderFail() {
		html = new HtmlBuilder("Teste Header", true, 2,4);
	}
	

	@Test
	public void testAddTableHeader() {
		html.addTableHeader("Article Title", "Journal", "Publication Year", "Authors");
	}
	
	@Test
	public void testAddTableHeaderFail() {
		html.addTableHeader("Article Title", "Journal", "Publication Year", "Authors","Falhar");
	}

	@Test
	public void testAddRowValues() {
		html.addRowValues("1","2","3","4");
	}
	
	@Test
	public void testAddRowValuesFail() {
		html.addRowValues("1","2","3","4","5");
	}
	

	@Test
	public void testBuild() {
		html.build();
	}

	@Test
	public void testWriteToFile() {
		try {
			html.writeToFile(file, text);
		} catch (IOException e) {}
	}

}
