package hu.unideb.inf;

import java.awt.Color;
import java.awt.Font;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@code Sudoku16x16} játék és az {@link Ablak16x16} grafikus felhasználói
 * felület komunikációját implementáló osztály.
 * 
 * @author Bokor Zsolt Levente
 * @version 1.0
 */
public class GUISegedletek16x16 {

	/**
	 * Az osztály loggere.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(GUISegedletek16x16.class);

	/**
	 * A {@link GUISegedletek16x16} osztáy konstruktora.
	 * <p>
	 * Szerkezete miatt letiltja az alapértelmezett konstruktort így emiatt nem
	 * lehet példányosítani.
	 */
	private GUISegedletek16x16() {
	}

	/**
	 * Beállítja az {@link Ablak16x16} grafikus felület mezőit a játék aktuális
	 * állapotának elegettéve.
	 */
	public static void kirajzolTabla() {
		torolTabla();
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				int seged = Ablak16x16.jatek.getErtek(i, j);
				if (seged != 0) {
					Ablak16x16.szamTabla[j][i].setText(Integer.toString(seged));
					if (!Ablak16x16.jatek.modosithatoE(i, j)) {
						Ablak16x16.szamTabla[j][i]
								.setBackground(Color.LIGHT_GRAY);
						Ablak16x16.szamTabla[j][i].setFont(new Font("serif",
								Font.BOLD, 24));
						Ablak16x16.szamTabla[j][i].setEditable(false);
					}
				}
			}
		}
		logger.debug("A beolvasott tábla kirajzolva a grafikus felületre.");
	}

	/**
	 * Beállítja az {@link Ablak16x16} grafikus felület mezőit a kezdőállapotba.
	 */
	private static void torolTabla() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				Ablak16x16.szamTabla[i][j].setEditable(true);
				Ablak16x16.szamTabla[i][j].setText("");
				Ablak16x16.szamTabla[i][j].setPiros(0);
				Ablak16x16.szamTabla[i][j].setBackground(Color.white);
			}
		}
		logger.debug("A tábla kiürítve.");
	}

	/**
	 * Az {@link Ablak16x16} grafikus felület táblájának adott sorában pirosra
	 * váltja a duplikátumok betüszínét.
	 * 
	 * @param sor
	 *            a grafikus felhasználói felület táblájának a sora
	 */
	public static void pirositSor(int sor) {
		for (int i = 0; i < 16; i++) {
			if (Ablak16x16.szamTabla[i][sor].getPiros() == 1) {
				Ablak16x16.szamTabla[i][sor].setPiros(0);
			}
		}
		for (Integer i : Ablak16x16.jatek.sorKipirosit(sor)) {
			Ablak16x16.szamTabla[i][sor].setPiros(1);
		}
		logger.debug(
				"Kipirositotta a grafikus feluletben a {}. sorban a duplikatumokat.",
				sor + 1);
	}

	/**
	 * Az {@link Ablak16x16} grafikus felület táblájának adott oszlopában
	 * pirosra váltja a duplikátumok betüszínét.
	 * 
	 * @param oszlop
	 *            a grafikus felhasználói felület táblájának az oszlopa
	 */
	public static void pirositOszlop(int oszlop) {
		for (int i = 0; i < 16; i++) {
			if (Ablak16x16.szamTabla[oszlop][i].getPiros() == 2) {
				Ablak16x16.szamTabla[oszlop][i].setPiros(0);
			}
		}
		for (Integer i : Ablak16x16.jatek.oszlopKipirosit(oszlop)) {
			Ablak16x16.szamTabla[oszlop][i].setPiros(2);
		}
		logger.debug(
				"Kipirositotta a grafikus feluletben a {}. oszlopban a duplikatumokat.",
				oszlop + 1);
	}

	/**
	 * Az {@link Ablak16x16} grafikus felület táblájának adott négyzetében pirosra
	 * váltja a duplikátumok betüszínét.
	 * 
	 * @param sor
	 *            a grafikus felhasználói felület négyzetéhez tartozó sor index
	 * @param oszlop
	 *            a grafikus felhasználói felület négyzetéhez tartozó oszlop
	 *            indexe
	 */
	public static void pirositNegyzet(int sor, int oszlop) {
		int kezdoSor = sor - sor % 4;
		int kezdoOszlop = oszlop - oszlop % 4;
		int utolsoSor = kezdoSor + 4;
		int utolsoOszlop = kezdoOszlop + 4;
		for (int i = kezdoSor; i < utolsoSor; i++) {
			for (int j = kezdoOszlop; j < utolsoOszlop; j++) {
				if (Ablak16x16.szamTabla[j][i].getPiros() == 4) {
					Ablak16x16.szamTabla[j][i].setPiros(0);
				}
			}
		}
		for (Koordinatak k : Ablak16x16.jatek.negyzetKipirosit(sor, oszlop)) {
			Ablak16x16.szamTabla[k.getX()][k.getY()].setPiros(4);
		}
		logger.debug(
				"Kipirositotta a grafikus feluletben a ({} - {}) ({} - {}) negyzetben a duplikatumokat.",
				kezdoSor + 1, kezdoOszlop + 1, utolsoSor, utolsoOszlop);
	}
}
