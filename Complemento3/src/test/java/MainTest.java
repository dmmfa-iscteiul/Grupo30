import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainTest {

	HtmlBuilder htmlBuider;
	Main main;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		htmlBuider = new HtmlBuilder(null, true, 2, 4);
				
	}

	@Test
	public void testMain() {
		
		String[] args = null;
		Main.main(args);
		
	}

}
