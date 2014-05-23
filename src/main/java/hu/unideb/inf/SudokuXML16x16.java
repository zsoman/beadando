package hu.unideb.inf;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * {@code Sudoku16x16} játékok számára biztosítja azon metódusokat melyek
 * megvalósítják a mentést és a betöltést.
 * 
 * @author Bokor Zsolt Levente
 * @version 1.0
 */
public class SudokuXML16x16 {

	/**
	 * Az osztály loggere.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(SudokuXML16x16.class);

	/**
	 * Az osztáy konstruktora.
	 * <p>
	 * Szerkezete miatt letiltja az alapértelmezett konstruktort így emiatt nem
	 * lehet példányosítani.
	 */
	private SudokuXML16x16() {
	}

	/**
	 * Biztosítja a megkezdett {@link Sudoku16x16} játék mentését.
	 * <p>
	 * Létrehozza az XML fájlt és lementi a paraméterül kapott elérési
	 * utvonalra.
	 * 
	 * @param s
	 *            aktuális {@code Sudoku16x16} játék
	 * @param cel
	 *            az XML fájl elérési útvonala ahova mentik
	 */
	public static void lementTabla(Sudoku16x16 s, String cel) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element rootElement = doc.createElement("save");
			doc.appendChild(rootElement);

			Element sudoku = doc.createElement("sudoku");
			rootElement.appendChild(sudoku);
			sudoku.setAttribute("id", "0");

			for (int i = 0; i < 16; i++) {
				if (!s.sorUresE(i)) {
					Element row = doc.createElement("row");
					row.setAttribute("index", Integer.toString(i));
					for (int j = 0; j < 16; j++) {
						int seged = s.getErtek(i, j);
						if (seged != 0) {
							Element column = doc.createElement("column");
							column.setAttribute("changeable",
									Boolean.toString(s.modosithatoE(i, j)));
							column.setAttribute("index", Integer.toString(j));
							column.setTextContent(Integer.toString(seged));
							logger.debug("Sor: {} Oszlop: {} Ertek:{}", i, j,
									seged);
							row.appendChild(column);
						}
					}
					sudoku.appendChild(row);
				}
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(cel));
			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerException e) {
			logger.error("Hiba az XML fájl letrehozasaban.");
			JOptionPane.showMessageDialog(null, "Nem megfelelő célfájl.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Biztosítja egy korábban lementett {@link Sudoku16x16} játék betöltését.
	 * <p>
	 * A cél mappában találhatő XML fájlból létrehozza és betölti a játékot. Új
	 * játék esetén az {@code id} véletlenszerű egy adott intervallumból,
	 * lementett játék esetén {@code id} 0 indexű.
	 * 
	 * @param s
	 *            aktuális {@code Sudoku16x16} játék
	 * @param forras
	 *            az XML fájl elérési útvonala, ahonnan betöltődik
	 * @param id
	 *            a beolvasásra kiválasztott tábla azonosítója
	 */
	public static void betoltTabla(Sudoku16x16 s, String forras, int id) {
		s.tablaTorles();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder parser = dbf.newDocumentBuilder();
			Document doc = parser.parse(forras);
			NodeList sudoku = doc.getElementsByTagName("sudoku");
			NodeList rows = sudoku.item(id).getChildNodes();
			for (int i = 0; i < rows.getLength(); i++) {
				if (rows.item(i).getNodeName().equals("row")) {
					int sor = Integer.parseInt(rows.item(i).getAttributes()
							.item(0).getNodeValue());
					NodeList columns = rows.item(i).getChildNodes();
					for (int j = 0; j < columns.getLength(); j++) {
						if (columns.item(j).getNodeName().equals("column")) {
							int oszlop;
							boolean valtoztathato;
							if (columns.item(j).getAttributes().item(0)
									.getNodeName().equals("index")) {
								oszlop = Integer
										.parseInt(columns.item(j)
												.getAttributes().item(0)
												.getNodeValue());
								valtoztathato = Boolean.parseBoolean(columns
										.item(j).getAttributes().item(1)
										.getNodeValue());
							} else {
								oszlop = Integer
										.parseInt(columns.item(j)
												.getAttributes().item(1)
												.getNodeValue());
								valtoztathato = Boolean.parseBoolean(columns
										.item(j).getAttributes().item(0)
										.getNodeValue());
							}
							int ertek = Integer.parseInt(columns.item(j)
									.getFirstChild().getNodeValue());
							s.szamBeirasa(oszlop, sor, ertek);
							logger.debug("Sor: {} Oszlop: {} Ertek: {}", sor,
									oszlop, ertek);
							s.setModosithatosag(oszlop, sor, valtoztathato);
						}
					}
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error("Hiba az XML fájl letrehozasaban.");
			JOptionPane.showMessageDialog(null, "Nem megfelelő célfájl.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
