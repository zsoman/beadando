/**
 * 
 */
package hu.unideb.inf;

import static org.junit.Assert.*;

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

import ch.qos.logback.core.util.FileUtil;

/**
 * @author Zsolt
 * 
 */
public class TestSudoku16x16 {
	private Sudoku16x16 sudoku;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		sudoku = new Sudoku16x16();
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
		sudoku.szamBeirasa(0, 3, 5);
		sudoku.szamBeirasa(1, 3, 5);
		sudoku.szamBeirasa(2, 3, 1);
		sudoku.szamBeirasa(3, 3, 1);
		expected.add(0);
		expected.add(1);
		expected.add(2);
		expected.add(3);
		assertEquals(expected, sudoku.sorKipirosit(3));

		expected = new HashSet<Integer>();
		sudoku = new Sudoku16x16();
		sudoku.szamBeirasa(0, 3, 5);
		sudoku.szamBeirasa(1, 3, 5);
		sudoku.szamBeirasa(2, 3, 5);
		sudoku.szamBeirasa(15, 3, 5);
		expected.add(0);
		expected.add(1);
		expected.add(2);
		expected.add(15);
		assertEquals(expected, sudoku.sorKipirosit(3));

		expected = new HashSet<Integer>();
		sudoku = new Sudoku16x16();
		assertEquals(expected, sudoku.sorKipirosit(6));
	}

	@Test
	public void testOszlopKipirosit() {
		Set<Integer> expected = new HashSet<Integer>();
		sudoku.szamBeirasa(1, 5, 5);
		sudoku.szamBeirasa(1, 10, 5);
		sudoku.szamBeirasa(1, 2, 1);
		sudoku.szamBeirasa(1, 3, 1);
		expected.add(10);
		expected.add(5);
		expected.add(2);
		expected.add(3);
		assertEquals(expected, sudoku.oszlopKipirosit(1));

		expected = new HashSet<Integer>();
		sudoku = new Sudoku16x16();
		sudoku.szamBeirasa(2, 0, 5);
		sudoku.szamBeirasa(2, 2, 5);
		sudoku.szamBeirasa(10, 8, 5);
		expected.add(0);
		expected.add(2);
		assertEquals(expected, sudoku.oszlopKipirosit(2));

		expected = new HashSet<Integer>();
		sudoku = new Sudoku16x16();
		assertEquals(expected, sudoku.oszlopKipirosit(6));
	}

	@Test
	public void testNegyzetKipirosit() {
		Set<Koordinatak> expected = new HashSet<Koordinatak>();
		sudoku.szamBeirasa(0, 0, 7);
		sudoku.szamBeirasa(2, 1, 7);
		sudoku.szamBeirasa(0, 2, 7);
		expected.add(new Koordinatak(0, 0));
		expected.add(new Koordinatak(1, 2));
		expected.add(new Koordinatak(2, 0));
		Set<Koordinatak> actual = sudoku.negyzetKipirosit(0, 0);
		assertEquals(expected.size(), actual.size());

		expected = new HashSet<Koordinatak>();
		sudoku.szamBeirasa(4, 4, 7);
		sudoku.szamBeirasa(7, 7, 7);
		sudoku.szamBeirasa(6, 3, 7);
		expected.add(new Koordinatak(4, 4));
		expected.add(new Koordinatak(7, 7));
		actual = sudoku.negyzetKipirosit(6, 7);
		assertEquals(expected.size(), actual.size());

		sudoku = new Sudoku16x16();
		expected = new HashSet<Koordinatak>();
		actual = sudoku.negyzetKipirosit(4, 4);
		assertEquals(expected, actual);
	}

	@Test
	public void testSorErvenyessege() {
		assertEquals(false, sudoku.sorErvenyessege(5));
		sudoku.szamBeirasa(1, 6, 8);
		sudoku.szamBeirasa(1, 6, 1);
		assertEquals(false, sudoku.sorErvenyessege(1));

		for (int i = 0; i < 16; i++) {
			sudoku.szamBeirasa(i, 1, i);
		}
		assertEquals(true, sudoku.sorErvenyessege(1));
	}

	@Test
	public void testOszlopErvenyessege() {
		assertEquals(false, sudoku.oszlopErvenyessege(5));
		sudoku.szamBeirasa(7, 7, 1);
		sudoku.szamBeirasa(7, 6, 1);
		assertEquals(false, sudoku.oszlopErvenyessege(7));

		for (int i = 0; i < 16; i++) {
			sudoku.szamBeirasa(3, i, i);
		}
		assertEquals(true, sudoku.oszlopErvenyessege(3));
	}

	@Test
	public void testNegyzetErvenyessege() {
		assertEquals(false, sudoku.negyzetErvenyessege(2, 2));
		sudoku.szamBeirasa(3, 0, 1);
		sudoku.szamBeirasa(4, 1, 1);
		assertEquals(false, sudoku.negyzetErvenyessege(3, 3));

		int nr = 1;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				sudoku.szamBeirasa(j, i, nr);
				nr++;
			}
		}
		assertEquals(true, sudoku.negyzetErvenyessege(0, 0));
	}

	@Test
	public void testKitoltveE() {
		assertEquals(false, sudoku.kiToltveE());

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				sudoku.szamBeirasa(j, i, j + 1);
			}
		}
		// sudoku.kiir();
		assertEquals(true, sudoku.kiToltveE());

		sudoku.szamBeirasa(1, 5, 0);
		assertEquals(false, sudoku.kiToltveE());
	}

	@Test
	public void testVegeE() {
		assertEquals(false, sudoku.vegeE());

		sudoku.szamBeirasa(0, 0, 9);
		sudoku.szamBeirasa(1, 0, 3);
		sudoku.szamBeirasa(2, 0, 4);
		sudoku.szamBeirasa(3, 0, 2);
		sudoku.szamBeirasa(4, 0, 6);
		sudoku.szamBeirasa(5, 0, 11);
		sudoku.szamBeirasa(6, 0, 1);
		sudoku.szamBeirasa(7, 0, 5);
		sudoku.szamBeirasa(8, 0, 15);
		sudoku.szamBeirasa(9, 0, 13);
		sudoku.szamBeirasa(10, 0, 10);
		sudoku.szamBeirasa(11, 0, 12);
		sudoku.szamBeirasa(12, 0, 8);
		sudoku.szamBeirasa(13, 0, 14);
		sudoku.szamBeirasa(14, 0, 7);
		sudoku.szamBeirasa(15, 0, 16);
		sudoku.szamBeirasa(0, 1, 14);
		sudoku.szamBeirasa(1, 1, 13);
		sudoku.szamBeirasa(2, 1, 8);
		sudoku.szamBeirasa(3, 1, 1);
		sudoku.szamBeirasa(4, 1, 12);
		sudoku.szamBeirasa(5, 1, 15);
		sudoku.szamBeirasa(6, 1, 4);
		sudoku.szamBeirasa(7, 1, 7);
		sudoku.szamBeirasa(8, 1, 9);
		sudoku.szamBeirasa(9, 1, 5);
		sudoku.szamBeirasa(10, 1, 16);
		sudoku.szamBeirasa(11, 1, 11);
		sudoku.szamBeirasa(12, 1, 2);
		sudoku.szamBeirasa(13, 1, 3);
		sudoku.szamBeirasa(14, 1, 10);
		sudoku.szamBeirasa(15, 1, 6);
		sudoku.szamBeirasa(0, 2, 11);
		sudoku.szamBeirasa(1, 2, 6);
		sudoku.szamBeirasa(2, 2, 10);
		sudoku.szamBeirasa(3, 2, 5);
		sudoku.szamBeirasa(4, 2, 3);
		sudoku.szamBeirasa(5, 2, 13);
		sudoku.szamBeirasa(6, 2, 8);
		sudoku.szamBeirasa(7, 2, 16);
		sudoku.szamBeirasa(8, 2, 2);
		sudoku.szamBeirasa(9, 2, 7);
		sudoku.szamBeirasa(10, 2, 14);
		sudoku.szamBeirasa(11, 2, 4);
		sudoku.szamBeirasa(12, 2, 12);
		sudoku.szamBeirasa(13, 2, 9);
		sudoku.szamBeirasa(14, 2, 15);
		sudoku.szamBeirasa(15, 2, 1);
		sudoku.szamBeirasa(0, 3, 12);
		sudoku.szamBeirasa(1, 3, 16);
		sudoku.szamBeirasa(2, 3, 15);
		sudoku.szamBeirasa(3, 3, 7);
		sudoku.szamBeirasa(4, 3, 9);
		sudoku.szamBeirasa(5, 3, 2);
		sudoku.szamBeirasa(6, 3, 14);
		sudoku.szamBeirasa(7, 3, 10);
		sudoku.szamBeirasa(8, 3, 8);
		sudoku.szamBeirasa(9, 3, 3);
		sudoku.szamBeirasa(10, 3, 6);
		sudoku.szamBeirasa(11, 3, 1);
		sudoku.szamBeirasa(12, 3, 4);
		sudoku.szamBeirasa(13, 3, 5);
		sudoku.szamBeirasa(14, 3, 13);
		sudoku.szamBeirasa(15, 3, 11);
		sudoku.szamBeirasa(0, 4, 2);
		sudoku.szamBeirasa(1, 4, 15);
		sudoku.szamBeirasa(2, 4, 14);
		sudoku.szamBeirasa(3, 4, 3);
		sudoku.szamBeirasa(4, 4, 10);
		sudoku.szamBeirasa(5, 4, 16);
		sudoku.szamBeirasa(6, 4, 9);
		sudoku.szamBeirasa(7, 4, 6);
		sudoku.szamBeirasa(8, 4, 4);
		sudoku.szamBeirasa(9, 4, 12);
		sudoku.szamBeirasa(10, 4, 7);
		sudoku.szamBeirasa(11, 4, 13);
		sudoku.szamBeirasa(12, 4, 5);
		sudoku.szamBeirasa(13, 4, 11);
		sudoku.szamBeirasa(14, 4, 1);
		sudoku.szamBeirasa(15, 4, 8);
		sudoku.szamBeirasa(0, 5, 4);
		sudoku.szamBeirasa(1, 5, 1);
		sudoku.szamBeirasa(2, 5, 7);
		sudoku.szamBeirasa(3, 5, 9);
		sudoku.szamBeirasa(4, 5, 5);
		sudoku.szamBeirasa(5, 5, 3);
		sudoku.szamBeirasa(6, 5, 11);
		sudoku.szamBeirasa(7, 5, 13);
		sudoku.szamBeirasa(8, 5, 14);
		sudoku.szamBeirasa(9, 5, 10);
		sudoku.szamBeirasa(10, 5, 8);
		sudoku.szamBeirasa(11, 5, 16);
		sudoku.szamBeirasa(12, 5, 15);
		sudoku.szamBeirasa(13, 5, 2);
		sudoku.szamBeirasa(14, 5, 6);
		sudoku.szamBeirasa(15, 5, 12);
		sudoku.szamBeirasa(0, 6, 16);
		sudoku.szamBeirasa(1, 6, 5);
		sudoku.szamBeirasa(2, 6, 6);
		sudoku.szamBeirasa(3, 6, 12);
		sudoku.szamBeirasa(4, 6, 15);
		sudoku.szamBeirasa(5, 6, 1);
		sudoku.szamBeirasa(6, 6, 7);
		sudoku.szamBeirasa(7, 6, 8);
		sudoku.szamBeirasa(8, 6, 11);
		sudoku.szamBeirasa(9, 6, 2);
		sudoku.szamBeirasa(10, 6, 3);
		sudoku.szamBeirasa(11, 6, 9);
		sudoku.szamBeirasa(12, 6, 10);
		sudoku.szamBeirasa(13, 6, 4);
		sudoku.szamBeirasa(14, 6, 14);
		sudoku.szamBeirasa(15, 6, 13);
		sudoku.szamBeirasa(0, 7, 8);
		sudoku.szamBeirasa(1, 7, 10);
		sudoku.szamBeirasa(2, 7, 13);
		sudoku.szamBeirasa(3, 7, 11);
		sudoku.szamBeirasa(4, 7, 2);
		sudoku.szamBeirasa(5, 7, 14);
		sudoku.szamBeirasa(6, 7, 12);
		sudoku.szamBeirasa(7, 7, 4);
		sudoku.szamBeirasa(8, 7, 1);
		sudoku.szamBeirasa(9, 7, 15);
		sudoku.szamBeirasa(10, 7, 5);
		sudoku.szamBeirasa(11, 7, 6);
		sudoku.szamBeirasa(12, 7, 3);
		sudoku.szamBeirasa(13, 7, 7);
		sudoku.szamBeirasa(14, 7, 16);
		sudoku.szamBeirasa(15, 7, 9);
		sudoku.szamBeirasa(0, 8, 3);
		sudoku.szamBeirasa(1, 8, 4);
		sudoku.szamBeirasa(2, 8, 2);
		sudoku.szamBeirasa(3, 8, 14);
		sudoku.szamBeirasa(4, 8, 7);
		sudoku.szamBeirasa(5, 8, 9);
		sudoku.szamBeirasa(6, 8, 6);
		sudoku.szamBeirasa(7, 8, 15);
		sudoku.szamBeirasa(8, 8, 13);
		sudoku.szamBeirasa(9, 8, 16);
		sudoku.szamBeirasa(10, 8, 12);
		sudoku.szamBeirasa(11, 8, 10);
		sudoku.szamBeirasa(12, 8, 11);
		sudoku.szamBeirasa(13, 8, 1);
		sudoku.szamBeirasa(14, 8, 8);
		sudoku.szamBeirasa(15, 8, 5);
		sudoku.szamBeirasa(0, 9, 13);
		sudoku.szamBeirasa(1, 9, 12);
		sudoku.szamBeirasa(2, 9, 9);
		sudoku.szamBeirasa(3, 9, 6);
		sudoku.szamBeirasa(4, 9, 4);
		sudoku.szamBeirasa(5, 9, 5);
		sudoku.szamBeirasa(6, 9, 3);
		sudoku.szamBeirasa(7, 9, 1);
		sudoku.szamBeirasa(8, 9, 7);
		sudoku.szamBeirasa(9, 9, 11);
		sudoku.szamBeirasa(10, 9, 15);
		sudoku.szamBeirasa(11, 9, 8);
		sudoku.szamBeirasa(12, 9, 14);
		sudoku.szamBeirasa(13, 9, 16);
		sudoku.szamBeirasa(14, 9, 2);
		sudoku.szamBeirasa(15, 9, 10);
		sudoku.szamBeirasa(0, 10, 15);
		sudoku.szamBeirasa(1, 10, 11);
		sudoku.szamBeirasa(2, 10, 16);
		sudoku.szamBeirasa(3, 10, 8);
		sudoku.szamBeirasa(4, 10, 14);
		sudoku.szamBeirasa(5, 10, 10);
		sudoku.szamBeirasa(6, 10, 2);
		sudoku.szamBeirasa(7, 10, 12);
		sudoku.szamBeirasa(8, 10, 5);
		sudoku.szamBeirasa(9, 10, 1);
		sudoku.szamBeirasa(10, 10, 9);
		sudoku.szamBeirasa(11, 10, 3);
		sudoku.szamBeirasa(12, 10, 13);
		sudoku.szamBeirasa(13, 10, 6);
		sudoku.szamBeirasa(14, 10, 4);
		sudoku.szamBeirasa(15, 10, 7);
		sudoku.szamBeirasa(0, 11, 1);
		sudoku.szamBeirasa(1, 11, 7);
		sudoku.szamBeirasa(2, 11, 5);
		sudoku.szamBeirasa(3, 11, 10);
		sudoku.szamBeirasa(4, 11, 13);
		sudoku.szamBeirasa(5, 11, 8);
		sudoku.szamBeirasa(6, 11, 16);
		sudoku.szamBeirasa(7, 11, 11);
		sudoku.szamBeirasa(8, 11, 6);
		sudoku.szamBeirasa(9, 11, 14);
		sudoku.szamBeirasa(10, 11, 4);
		sudoku.szamBeirasa(11, 11, 2);
		sudoku.szamBeirasa(12, 11, 9);
		sudoku.szamBeirasa(13, 11, 15);
		sudoku.szamBeirasa(14, 11, 12);
		sudoku.szamBeirasa(15, 11, 3);
		sudoku.szamBeirasa(0, 12, 6);
		sudoku.szamBeirasa(1, 12, 2);
		sudoku.szamBeirasa(2, 12, 3);
		sudoku.szamBeirasa(3, 12, 4);
		sudoku.szamBeirasa(4, 12, 16);
		sudoku.szamBeirasa(5, 12, 7);
		sudoku.szamBeirasa(6, 12, 5);
		sudoku.szamBeirasa(7, 12, 9);
		sudoku.szamBeirasa(8, 12, 10);
		sudoku.szamBeirasa(9, 12, 8);
		sudoku.szamBeirasa(10, 12, 13);
		sudoku.szamBeirasa(11, 12, 15);
		sudoku.szamBeirasa(12, 12, 1);
		sudoku.szamBeirasa(13, 12, 12);
		sudoku.szamBeirasa(14, 12, 11);
		sudoku.szamBeirasa(15, 12, 14);
		sudoku.szamBeirasa(0, 13, 10);
		sudoku.szamBeirasa(1, 13, 9);
		sudoku.szamBeirasa(2, 13, 12);
		sudoku.szamBeirasa(3, 13, 13);
		sudoku.szamBeirasa(4, 13, 11);
		sudoku.szamBeirasa(5, 13, 6);
		sudoku.szamBeirasa(6, 13, 15);
		sudoku.szamBeirasa(7, 13, 14);
		sudoku.szamBeirasa(8, 13, 3);
		sudoku.szamBeirasa(9, 13, 4);
		sudoku.szamBeirasa(10, 13, 1);
		sudoku.szamBeirasa(11, 13, 7);
		sudoku.szamBeirasa(12, 13, 16);
		sudoku.szamBeirasa(13, 13, 8);
		sudoku.szamBeirasa(14, 13, 5);
		sudoku.szamBeirasa(15, 13, 2);
		sudoku.szamBeirasa(0, 14, 7);
		sudoku.szamBeirasa(1, 14, 14);
		sudoku.szamBeirasa(2, 14, 1);
		sudoku.szamBeirasa(3, 14, 15);
		sudoku.szamBeirasa(4, 14, 8);
		sudoku.szamBeirasa(5, 14, 12);
		sudoku.szamBeirasa(6, 14, 10);
		sudoku.szamBeirasa(7, 14, 2);
		sudoku.szamBeirasa(8, 14, 16);
		sudoku.szamBeirasa(9, 14, 9);
		sudoku.szamBeirasa(10, 14, 11);
		sudoku.szamBeirasa(11, 14, 5);
		sudoku.szamBeirasa(12, 14, 6);
		sudoku.szamBeirasa(13, 14, 13);
		sudoku.szamBeirasa(14, 14, 3);
		sudoku.szamBeirasa(15, 14, 4);
		sudoku.szamBeirasa(0, 15, 5);
		sudoku.szamBeirasa(1, 15, 8);
		sudoku.szamBeirasa(2, 15, 11);
		sudoku.szamBeirasa(3, 15, 16);
		sudoku.szamBeirasa(4, 15, 1);
		sudoku.szamBeirasa(5, 15, 4);
		sudoku.szamBeirasa(6, 15, 13);
		sudoku.szamBeirasa(7, 15, 3);
		sudoku.szamBeirasa(8, 15, 12);
		sudoku.szamBeirasa(9, 15, 6);
		sudoku.szamBeirasa(10, 15, 2);
		sudoku.szamBeirasa(11, 15, 14);
		sudoku.szamBeirasa(12, 15, 7);
		sudoku.szamBeirasa(13, 15, 10);
		sudoku.szamBeirasa(14, 15, 9);
		sudoku.szamBeirasa(15, 15, 15);

		assertEquals(true, sudoku.vegeE());

		sudoku.szamBeirasa(0, 8, 8);
		assertEquals(false, sudoku.vegeE());

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
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

		sudoku.szamBeirasa(4, 8, 1);
		assertEquals(false, sudoku.oszlopUresE(4));
	}

	@Test
	public void testTablaTorles() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				sudoku.szamBeirasa(j, i, j + 1);
				sudoku.setModosithatosag(i, j, false);
			}
		}
		sudoku.tablaTorles();
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (sudoku.getErtek(i, j) != 0
						|| sudoku.modosithatoE(i, j) != true)
					fail();
			}
		}

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				sudoku.szamBeirasa(j, i, j + 1);
				sudoku.setModosithatosag(i, j, false);
			}
		}
		sudoku.tablaTorles();
		sudoku.szamBeirasa(1, 1, 1);
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
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
		sudoku.szamBeirasa(0, 0, 1);
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
		sudoku.setModosithatosag(1, 8, false);
		assertEquals(false, sudoku.modosithatoE(8, 1));

		sudoku.setModosithatosag(1, 8, true);
		assertEquals(true, sudoku.modosithatoE(8, 1));
	}

	@Test
	public void testGetErtek() {
		sudoku.szamBeirasa(0, 0, 1);
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
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
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
