package G30_82664.Covid_Query;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tests_RegionService {
	
	RegionService s;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		this.s = new RegionService();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetRegions() {
		assertFalse(s.getRegions().isEmpty());
	}

	@Test
	void testAddRegion() {
		s.addRegion(new Region("x"));
		assertFalse(s.getRegions().isEmpty());
	}

	@Test
	void testDeleteRegion() {
		Region r = new Region("x");
		s.addRegion(r);
		s.deleteRegion(r);
		assertTrue(s.getRegions().isEmpty());
	}

	@Test
	void testDeleteAll() {
		s.deleteAll();
		assertTrue(s.getRegions().isEmpty());
	}

}
