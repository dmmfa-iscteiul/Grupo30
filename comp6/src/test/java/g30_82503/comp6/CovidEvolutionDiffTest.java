/**
 * 
 */
package g30_82503.comp6;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Repository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Catarina Afonso
 *
 */
public class CovidEvolutionDiffTest {
	
	CovidEvolutionDiff app;
	Repository repository;

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link g30_82503.comp6.CovidEvolutionDiff#writeFile(java.io.File, java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	void testWriteFile() throws IOException {
		File file = new File("teste.txt");
		String expectedInput="expectedString";
		app.writeFile(file, expectedInput);
	      
	      
	}

	/**
	 * Test method for {@link g30_82503.comp6.CovidEvolutionDiff#createFile(java.lang.String, java.util.List)}.
	 * @throws IOException 
	 */
	@Test
	void testCreateFile() throws IOException {
		String file="fileName";
		
		List<String> content = new ArrayList<String>(); 
		content.add("teste coverage");
		content.add("feito");
		app.createFile(file, content);
		
		
	}

	/**
	 * Test method for {@link g30_82503.comp6.CovidEvolutionDiff#main(java.lang.String[])}.
	 * @throws Exception 
	 */
	@Test
	void testMain() throws Exception {
		app.main(null);
	}

}
