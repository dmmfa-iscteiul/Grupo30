package monitoring_tool;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TableBuilderTest {
	TableBuilder tb;
	int oioi;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tb=new TableBuilder();
	}

	@Test
	public void testHtmlTableBuilder() {
		tb.htmlTableBuilder("headerTest", true, 6, 2);
		tb.htmlTableBuilder(null, false, 6, 2);
	}

	@Test
	public void testAddTableHeader() {
		tb.htmlTableBuilder("headerTest", true, 2, 2);
		tb.addTableHeader("uptime", "downtime");
		tb.addTableHeader("oioi", "mekie", "de-me20");

	}

	@Test
	public void testAddRowValues() {
		tb.htmlTableBuilder("headerTest", true, 2, 2);
		tb.addRowValues("oioi", "xauxau");
		tb.addRowValues("xauxau", "xauxau", "byebye");
		tb.addRowValues("oioi", "xauxau");
	}

	@Test
	public void testBuildTable() {
		tb.buildTable();
	}

}
