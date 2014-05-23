/**
 * 
 */
package hu.unideb.inf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Zsolt
 * 
 */
public class TestSudoku9x9 {
	private Sudoku9x9 sudoku;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		sudoku = new Sudoku9x9();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDownAfter() throws Exception {
		sudoku = null;
	}

	@Test
	public void testSorKipirosit() {
		Set<Integer> expected = new HashSet<Integer>();
		sudoku.szamBeirasa(0,3,5);
		sudoku.szamBeirasa(1,3,5);
		sudoku.szamBeirasa(2,3,1);
		expected.add(0);
		expected.add(1);
		sudoku.kiir();
		assertEquals(expected, sudoku.sorKipirosit(3));

		expected = new HashSet<Integer>();
		sudoku = new Sudoku9x9();
		sudoku.szamBeirasa(0,3,5);
		sudoku.szamBeirasa(1,3,5);
		sudoku.szamBeirasa(2,3,5);
		expected.add(0);
		expected.add(1);
		expected.add(2);
		assertEquals(expected, sudoku.sorKipirosit(3));

		expected = new HashSet<Integer>();
		sudoku = new Sudoku9x9();
		assertEquals(expected, sudoku.sorKipirosit(6));
	}

	@Test
	public void testOszlopKipirosit() {
		Set<Integer> expected = new HashSet<Integer>();
		sudoku.szamBeirasa( 1,5,5);
		sudoku.szamBeirasa( 1,3,5);
		sudoku.szamBeirasa( 1,2,1);
		expected.add(5);
		expected.add(3);
		assertEquals(expected, sudoku.oszlopKipirosit(1));

		expected = new HashSet<Integer>();
		sudoku = new Sudoku9x9();
		sudoku.szamBeirasa(2,0, 5);
		sudoku.szamBeirasa(2,2, 5);
		sudoku.szamBeirasa(2,8, 5);
		expected.add(0);
		expected.add(2);
		expected.add(8);
		assertEquals(expected, sudoku.oszlopKipirosit(2));

		expected = new HashSet<Integer>();
		sudoku = new Sudoku9x9();
		assertEquals(expected, sudoku.oszlopKipirosit(6));
	}

	@Test
	public void testNegyzetKipirosit() {
		sudoku.szamBeirasa(0, 0, 1);
		sudoku.szamBeirasa(1, 2, 1);
		sudoku.szamBeirasa(2, 0, 1);
		Set<Koordinatak> expected = new HashSet<>();
		Set<Koordinatak>	actual = sudoku.negyzetKipirosit(0, 0);
		expected.add(new Koordinatak(0, 0));
		expected.add(new Koordinatak(1, 2));
		expected.add(new Koordinatak(2, 0));
		assertEquals(expected.size(), actual.size());
		int db = 0;
		for(Koordinatak seged1 : expected ) {
			for(Koordinatak seged2 : actual) {
				if (seged1.equals(seged2)) 
					db++;
			}
		}
		assertEquals(actual.size(), db);
		sudoku=new Sudoku9x9();
		expected = new HashSet<>();
		actual = sudoku.negyzetKipirosit(0, 0);
		assertEquals(expected, actual);
	}

	@Test
	public void testSorErvenyessege() {
		assertEquals(false, sudoku.sorErvenyessege(5));
		sudoku.szamBeirasa(1, 6, 8);
		sudoku.szamBeirasa(1, 6, 1);
		assertEquals(false, sudoku.sorErvenyessege(1));

		for (int i = 0; i < 9; i++) {
			sudoku.szamBeirasa(i, 1, i);
		}
		assertEquals(true, sudoku.sorErvenyessege(1));
	}

	@Test
	public void testOszlopErvenyessege() {
		assertEquals(false, sudoku.oszlopErvenyessege(5));
		sudoku.szamBeirasa(7,7,1);
		sudoku.szamBeirasa(7,6,1);
		assertEquals(false, sudoku.oszlopErvenyessege(7));

		for (int i = 0; i < 9; i++) {
			sudoku.szamBeirasa(3,i, i);
		}
		assertEquals(true, sudoku.oszlopErvenyessege(3));
	}

	@Test
	public void testNegyzetErvenyessege() {
		assertEquals(false, sudoku.negyzetErvenyessege(2, 2));
		sudoku.szamBeirasa(3,0,1);
		sudoku.szamBeirasa(4,1,1);
		assertEquals(false, sudoku.negyzetErvenyessege(3, 3));

		int nr = 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sudoku.szamBeirasa(j,i,nr);
				nr++;
			}
		}
		// sudoku.kiir();
		assertEquals(true, sudoku.negyzetErvenyessege(0, 0));
	}

	@Test
	public void testKitoltveE() {
		assertEquals(false, sudoku.kiToltveE());

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudoku.szamBeirasa(j,i,j+1);
			}
		}
		// sudoku.kiir();
		assertEquals(true, sudoku.kiToltveE());

		sudoku.szamBeirasa(1,5,0);
		assertEquals(false, sudoku.kiToltveE());
	}

	@Test
	public void testVegeE() {
		assertEquals(false, sudoku.vegeE());

		sudoku.szamBeirasa(0, 0, 6);
		sudoku.szamBeirasa(1, 0, 7);
		sudoku.szamBeirasa(2, 0, 9);
		sudoku.szamBeirasa(3, 0, 5);
		sudoku.szamBeirasa(4, 0, 4);
		sudoku.szamBeirasa(5, 0, 3);
		sudoku.szamBeirasa(6, 0, 8);
		sudoku.szamBeirasa(7, 0, 2);
		sudoku.szamBeirasa(8, 0, 1);
		sudoku.szamBeirasa(0, 1, 2);
		sudoku.szamBeirasa(1, 1, 3);
		sudoku.szamBeirasa(2, 1, 5);
		sudoku.szamBeirasa(3, 1, 8);
		sudoku.szamBeirasa(4, 1, 7);
		sudoku.szamBeirasa(5, 1, 1);
		sudoku.szamBeirasa(6, 1, 4);
		sudoku.szamBeirasa(7, 1, 9);
		sudoku.szamBeirasa(8, 1, 6);
		sudoku.szamBeirasa(0, 2, 8);
		sudoku.szamBeirasa(1, 2, 4);
		sudoku.szamBeirasa(2, 2, 1);
		sudoku.szamBeirasa(3, 2, 9);
		sudoku.szamBeirasa(4, 2, 6);
		sudoku.szamBeirasa(5, 2, 2);
		sudoku.szamBeirasa(6, 2, 5);
		sudoku.szamBeirasa(7, 2, 7);
		sudoku.szamBeirasa(8, 2, 3);
		sudoku.szamBeirasa(0, 3, 7);
		sudoku.szamBeirasa(1, 3, 1);
		sudoku.szamBeirasa(2, 3, 8);
		sudoku.szamBeirasa(3, 3, 3);
		sudoku.szamBeirasa(4, 3, 5);
		sudoku.szamBeirasa(5, 3, 4);
		sudoku.szamBeirasa(6, 3, 9);
		sudoku.szamBeirasa(7, 3, 6);
		sudoku.szamBeirasa(8, 3, 2);
		sudoku.szamBeirasa(0, 4, 3);
		sudoku.szamBeirasa(1, 4, 5);
		sudoku.szamBeirasa(2, 4, 2);
		sudoku.szamBeirasa(3, 4, 6);
		sudoku.szamBeirasa(4, 4, 9);
		sudoku.szamBeirasa(5, 4, 8);
		sudoku.szamBeirasa(6, 4, 7);
		sudoku.szamBeirasa(7, 4, 1);
		sudoku.szamBeirasa(8, 4, 4);
		sudoku.szamBeirasa(0, 5, 9);
		sudoku.szamBeirasa(1, 5, 6);
		sudoku.szamBeirasa(2, 5, 4);
		sudoku.szamBeirasa(3, 5, 2);
		sudoku.szamBeirasa(4, 5, 1);
		sudoku.szamBeirasa(5, 5, 7);
		sudoku.szamBeirasa(6, 5, 3);
		sudoku.szamBeirasa(7, 5, 8);
		sudoku.szamBeirasa(8, 5, 5);
		sudoku.szamBeirasa(0, 6, 1);
		sudoku.szamBeirasa(1, 6, 2);
		sudoku.szamBeirasa(2, 6, 3);
		sudoku.szamBeirasa(3, 6, 4);
		sudoku.szamBeirasa(4, 6, 8);
		sudoku.szamBeirasa(5, 6, 9);
		sudoku.szamBeirasa(6, 6, 6);
		sudoku.szamBeirasa(7, 6, 5);
		sudoku.szamBeirasa(8, 6, 7);
		sudoku.szamBeirasa(0, 7, 4);
		sudoku.szamBeirasa(1, 7, 9);
		sudoku.szamBeirasa(2, 7, 6);
		sudoku.szamBeirasa(3, 7, 7);
		sudoku.szamBeirasa(4, 7, 2);
		sudoku.szamBeirasa(5, 7, 5);
		sudoku.szamBeirasa(6, 7, 1);
		sudoku.szamBeirasa(7, 7, 3);
		sudoku.szamBeirasa(8, 7, 8);
		sudoku.szamBeirasa(0, 8, 5);
		sudoku.szamBeirasa(1, 8, 8);
		sudoku.szamBeirasa(2, 8, 7);
		sudoku.szamBeirasa(3, 8, 1);
		sudoku.szamBeirasa(4, 8, 3);
		sudoku.szamBeirasa(5, 8, 6);
		sudoku.szamBeirasa(6, 8, 2);
		sudoku.szamBeirasa(7, 8, 4);
		sudoku.szamBeirasa(8, 8, 9);

		sudoku.kiir();
		assertEquals(true, sudoku.vegeE());

		sudoku.szamBeirasa(0, 8, 8);
		assertEquals(false, sudoku.vegeE());

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudoku.szamBeirasa(j , i, j+1);
			}
		}
		assertEquals(false, sudoku.vegeE());
	}

	@Test
	public void testSorUresE() {
		assertEquals(true, sudoku.sorUresE(0));

		sudoku.szamBeirasa(1, 8, 8);
		assertEquals(false, sudoku.sorUresE(8));
	}

	@Test
	public void testOszlopUresE() {
		assertEquals(true, sudoku.oszlopUresE(6));

		sudoku.szamBeirasa(4,8,1);
		assertEquals(false, sudoku.oszlopUresE(4));
	}

	@Test
	public void testTablaTorles() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudoku.szamBeirasa(j,i, j+1);
				sudoku.setModosithatosag(i, j,false);
			}
		}
		sudoku.tablaTorles();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku.getErtek(i, j) != 0
						|| sudoku.modosithatoE(i, j) != true)
					fail();
			}
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudoku.szamBeirasa(j , i, j+1);
				sudoku.setModosithatosag(i, j,false);
			}
		}
		sudoku.tablaTorles();
		sudoku.szamBeirasa(1, 1, 1);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku.getErtek(i, j) != 0
						|| sudoku.modosithatoE(i, j) != true)
					if (i == 1 && j == 1) {
						assertEquals(1, sudoku.getErtek(i, j));
					} else {
						fail();
					}
			}
		}
	}

	@Test
	public void testSzamBeirasa() {
		sudoku.szamBeirasa(0, 0,1);
		assertEquals(1, sudoku.getErtek(0, 0));
	}

	@Test
	public void testErtekTorol() {
		sudoku.szamBeirasa(1, 0, 0);
		sudoku.ertekTorol(0, 0);
		assertEquals(0, sudoku.getErtek(0, 0));
	}

	@Test
	public void testSetModosithatosag() {
		sudoku.setModosithatosag(1,8,false);
		assertEquals(false, sudoku.modosithatoE(8, 1));

		sudoku.setModosithatosag(1,8,true);
		assertEquals(true, sudoku.modosithatoE(8, 1));
	}

	@Test
	public void testGetErtek() {
		sudoku.szamBeirasa(0, 0,1);
		assertEquals(1, sudoku.getErtek(0, 0));
	}

	@Test
	public void testModosithatoE() {
		sudoku.setModosithatosag(0, 0, false);
		assertEquals(false, sudoku.modosithatoE(0, 0));

		sudoku.setModosithatosag(0, 0, true);
		assertEquals(true, sudoku.modosithatoE(0, 0));
	}

	@Test
	public void testKiir() {
		try {
			PrintStream miVolt = System.out;
			File actual = new File("actual.txt");
			actual.createNewFile();
			System.setOut(new PrintStream(actual));
			sudoku.kiir();
			System.setOut(miVolt);
			PrintWriter p = new PrintWriter(new File("expected.txt"));
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					p.write(0 + " ");
				}
				p.write("\n");
			}
			p.write("\n");
			p.close();
			File expected = new File("expected.txt");
			assertEquals("The files differ!",
					FileUtils.readFileToString(expected, "utf-8"),
					FileUtils.readFileToString(actual, "utf-8"));
			actual.delete();
			expected.delete();
		} catch (IOException e) {
			assert false;
		}
	}
}
