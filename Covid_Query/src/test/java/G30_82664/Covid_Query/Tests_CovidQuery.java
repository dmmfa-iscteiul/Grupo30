package G30_82664.Covid_Query;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tests_CovidQuery {
	
	private CovidQuery c;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		this.c = new CovidQuery();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetNumberOf() {
		assertNotNull(c.getNumberOf("x", "y"));
	}

	@Test
	void testGetRegions() {
		assertNotNull(c.getRegions());
	}

	@Test
	void testGetTotalOr() {
		assertNotNull(c.getTotalOr("x", "y"));
	}

	@Test
	void testGetRegionsWhere() {
		assertNotNull(c.getRegionsWhere("x", ">", "z"));
		assertNotNull(c.getRegionsWhere("Algarve", ">", "10"));
	}

}
