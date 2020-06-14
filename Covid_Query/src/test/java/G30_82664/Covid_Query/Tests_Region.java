package G30_82664.Covid_Query;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tests_Region {
	
	Region r;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		this.r = new Region("x");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetName() {
		assertEquals(r.getName(), "x");
	}

	@Test
	void testSetName() {
		r.setName("y");
		assertEquals(r.getName(), "y");
	}

	@Test
	void testToString() {
		assertEquals(r.toString(), "Region [name=x]");
	}

}
