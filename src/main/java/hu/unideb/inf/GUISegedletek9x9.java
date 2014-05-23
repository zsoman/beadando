package hu.unideb.inf;

import java.awt.Color;
import java.awt.Font;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A {@code Sudoku9x9} játék és az {@link Ablak9x9} grafikus felhasználói
 * felület komunikációját implementáló osztály.
 * 
 * @author Bokor Zsolt Levente
 * @version 1.0
 */
public class GUISegedletek9x9 {

	/**
	 * Az osztály loggere.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(GUISegedletek9x9.class);

	/**
	 * A {@link GUISegedletek9x9} osztáy konstruktora.
	 * <p>
	 * Szerkezete miatt letiltja az alapértelmezett konstruktort így emiatt nem
	 * lehet példányosítani.
	 */
	private GUISegedletek9x9() {
	}

	/**
	 * Beállítja az {@link Ablak9x9} grafikus felület mezőit a játék aktuális
	 * állapotának elegettéve.
	 */
	public static void kirajzolTabla() {
		torolTabla();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int seged = Ablak9x9.jatek.getErtek(i, j);
				if (seged != 0) {
					Ablak9x9.szamTabla[j][i].setText(Integer.toString(seged));
					if (!Ablak9x9.jatek.modosithatoE(i, j)) {
						Ablak9x9.szamTabla[j][i]
								.setBackground(Color.LIGHT_GRAY);
						Ablak9x9.szamTabla[j][i].setFont(new Font("serif",
								Font.BOLD, 24));
						Ablak9x9.szamTabla[j][i].setEditable(false);
					}
				}
			}
		}
		logger.debug("A beolvasott tábla kirajzolva a grafikus felületre.");
	}

	/**
	 * Beállítja az {@link Ablak9x9} grafikus felület mezőit a kezdőállapotba.
	 */
	private static void torolTabla() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Ablak9x9.szamTabla[i][j].setEditable(true);
				Ablak9x9.szamTabla[i][j].setText("");
				Ablak9x9.szamTabla[i][j].setPiros(0);
				Ablak9x9.szamTabla[i][j].setBackground(Color.white);
			}
		}
		logger.debug("A tábla kiürítve.");
	}

	/**
	 * Az {@link Ablak9x9} grafikus felület táblájának adott sorában pirosra
	 * váltja a duplikátumok betüszínét.
	 * 
	 * @param sor
	 *            a grafikus felhasználói felület táblájának a sora
	 */
	public static void pirositSor(int sor) {
		for (int i = 0; i < 9; i++) {
			if (Ablak9x9.szamTabla[i][sor].getPiros() == 1) {
				Ablak9x9.szamTabla[i][sor].setPiros(0);
			}
		}
		for (Integer i : Ablak9x9.jatek.sorKipirosit(sor)) {
			Ablak9x9.szamTabla[i][sor].setPiros(1);
		}
		logger.debug(
				"Kipirositotta a grafikus feluletben a {}. sorban a duplikatumokat.",
				sor + 1);
	}

	/**
	 * Az {@link Ablak9x9} grafikus felület táblájának adott oszlopában pirosra
	 * váltja a duplikátumok betüszínét.
	 * 
	 * @param oszlop
	 *            a grafikus felhasználói felület táblájának az oszlopa
	 */
	public static void pirositOszlop(int oszlop) {
		for (int i = 0; i < 9; i++) {
			if (Ablak9x9.szamTabla[oszlop][i].getPiros() == 2) {
				Ablak9x9.szamTabla[oszlop][i].setPiros(0);
			}
		}
		for (Integer i : Ablak9x9.jatek.oszlopKipirosit(oszlop)) {
			Ablak9x9.szamTabla[oszlop][i].setPiros(2);
		}
		logger.debug(
				"Kipirositotta a grafikus feluletben a {}. oszlopban a duplikatumokat.",
				oszlop + 1);
	}

	/**
	 * Az {@link Ablak9x9} grafikus felület táblájának adott négyzetében pirosra
	 * váltja a duplikátumok betüszínét.
	 * 
	 * @param sor
	 *            a grafikus felhasználói felület négyzetéhez tartozó sor index
	 * @param oszlop
	 *            a grafikus felhasználói felület négyzetéhez tartozó oszlop
	 *            indexe
	 */
	public static void pirositNegyzet(int sor, int oszlop) {
		int kezdoSor = sor - sor % 3;
		int kezdoOszlop = oszlop - oszlop % 3;
		int utolsoSor = kezdoSor + 3;
		int utolsoOszlop = kezdoOszlop + 3;
		for (int i = kezdoSor; i < utolsoSor; i++) {
			for (int j = kezdoOszlop; j < utolsoOszlop; j++) {
				if (Ablak9x9.szamTabla[j][i].getPiros() == 3) {
					Ablak9x9.szamTabla[j][i].setPiros(0);
				}
			}
		}
		for (Koordinatak k : Ablak9x9.jatek.negyzetKipirosit(sor, oszlop)) {
			Ablak9x9.szamTabla[k.getX()][k.getY()].setPiros(3);
		}
		logger.debug(
				"Kipirositotta a grafikus feluletben a ({} - {}) ({} - {}) negyzetben a duplikatumokat.",
				kezdoSor + 1, kezdoOszlop + 1, utolsoSor, utolsoOszlop);
	}
}
