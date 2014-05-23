package hu.unideb.inf;

import static org.junit.Assert.assertEquals;

import hu.unideb.inf.Sudoku;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class TestSudokuXML9x9 {
	private Sudoku9x9 sudoku;

	@Before
	public void setUp() throws Exception {
		sudoku = new Sudoku9x9();
	}

	@After
	public void tearDown() throws Exception {
		sudoku = null;
	}

	@Test
	public void testLementTabla() {
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
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku.getErtek(i, j) != 0) {
					sudoku.setModosithatosag(j, i, false);
				}
			}
		}
		try {
			File f = new File("temporary.xml");
			f.createNewFile();
			SudokuXML9x9.lementTabla(sudoku, f.getAbsolutePath());
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document actual = builder.parse(f.getAbsolutePath());
			Document expected = builder.parse(TestSudokuXML9x9.class
					.getResource("/proba9x9.xml").toString());
			XMLUnit.setIgnoreWhitespace(true);
			assertEquals(XMLUnit.compareXML(expected, actual).similar(), true);
			f.delete();
		} catch (IOException | SAXException | ParserConfigurationException e) {
			assert false;
		}
	}

	@Test
	public void testBetoltTabla() {
		int[][] e = new int[9][9];
		e[0][0] = 6;
		e[0][1] = 7;
		e[0][2] = 9;
		e[0][3] = 5;
		e[0][4] = 4;
		e[0][5] = 3;
		e[0][6] = 8;
		e[0][7] = 2;
		e[0][8] = 1;
		e[1][0] = 2;
		e[1][1] = 3;
		e[1][2] = 5;
		e[1][3] = 8;
		e[1][4] = 7;
		e[1][5] = 1;
		e[1][6] = 4;
		e[1][7] = 9;
		e[1][8] = 6;
		e[2][0] = 8;
		e[2][1] = 4;
		e[2][2] = 1;
		e[2][3] = 9;
		e[2][4] = 6;
		e[2][5] = 2;
		e[2][6] = 5;
		e[2][7] = 7;
		e[2][8] = 3;
		e[3][0] = 7;
		e[3][1] = 1;
		e[3][2] = 8;
		e[3][3] = 3;
		e[3][4] = 5;
		e[3][5] = 4;
		e[3][6] = 9;
		e[3][7] = 6;
		e[3][8] = 2;
		e[4][0] = 3;
		e[4][1] = 5;
		e[4][2] = 2;
		e[4][3] = 6;
		e[4][4] = 9;
		e[4][5] = 8;
		e[4][6] = 7;
		e[4][7] = 1;
		e[4][8] = 4;
		e[5][0] = 9;
		e[5][1] = 6;
		e[5][2] = 4;
		e[5][3] = 2;
		e[5][4] = 1;
		e[5][5] = 7;
		e[5][6] = 3;
		e[5][7] = 8;
		e[5][8] = 5;
		e[6][0] = 1;
		e[6][1] = 2;
		e[6][2] = 3;
		e[6][3] = 4;
		e[6][4] = 8;
		e[6][5] = 9;
		e[6][6] = 6;
		e[6][7] = 5;
		e[6][8] = 7;
		e[7][0] = 4;
		e[7][1] = 9;
		e[7][2] = 6;
		e[7][3] = 7;
		e[7][4] = 2;
		e[7][5] = 5;
		e[7][6] = 1;
		e[7][7] = 3;
		e[7][8] = 8;
		e[8][0] = 5;
		e[8][1] = 8;
		e[8][2] = 7;
		e[8][3] = 1;
		e[8][4] = 3;
		e[8][5] = 6;
		e[8][6] = 2;
		e[8][7] = 4;
		e[8][8] = 9;

		SudokuXML9x9.betoltTabla(sudoku,
				TestSudokuXML9x9.class.getResource("/proba9x9.xml").toString(),
				0);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				assertEquals(e[i][j], sudoku.getErtek(i, j));
			}
		}
	}

}
