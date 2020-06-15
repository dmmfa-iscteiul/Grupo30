package monitoring_tool;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

public class CMSmonitoringTest {
	String cmsURL="http://192.168.99.100:8000";
	CMSmonitoring o;
	String username = "es2iscteiultest@gmail.com";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		o= new CMSmonitoring();
	}

	@Test
	public void testCMSmonitoring() {
		o= new CMSmonitoring();
		
	}

	@Test
	public void testTestAvailabilitypage() {
		o.testAvailabilitypage(cmsURL);
	}

	@Test
	public void testTestLogin() {
		o.testLogin();
		o.testLogin();
	}

	@Test
	public void testSendEmail() {
		o.sendEmail(username);
	
	}

	@Test
	public void testRunTests() throws InterruptedException {
		o.runTests();
	}

	@Test
	public void testWriteFile() throws IOException {
		o.writeFile(new File("teste.html"), "testeContent");
	}

//	@Test
//	public void testMain() throws InterruptedException, IOException {
//		o.main(null);
//	}

}
