package hu.unideb.inf;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sudoku játékot ábrázoló osztály, amely megvalósítja a {@link Sudoku}
 * interfészt, hexadecimális számrendszerben.
 * 
 * @author Bokor Zsolt Levente
 * @version 1.0
 */
public class Sudoku16x16 implements Sudoku {
	/**
	 * Az osztály loggere.
	 */
	private static Logger logger = LoggerFactory.getLogger(Sudoku16x16.class);

	/**
	 * A sudoku tábla reprezentáló tömbje.
	 */
	private int[][] tabla = new int[16][16];

	/**
	 * A sudoku tábla mezőinek módosíthatóságát reprezentáló tömbje.
	 */
	private boolean[][] modosithato = new boolean[16][16];

	/**
	 * Konstruktor a játék létrehozásához.
	 */
	public Sudoku16x16() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				modosithato[i][j] = true;
			}
		}
		logger.debug("Letrejott az ures tabla.");
	}

	/**
	 * A paraméterül kapott sorból visszaadja a duplikatumok oszlopszámát.
	 * 
	 * @param sor
	 *            az adott sor indexe
	 * @return a paraméterül kapott sorbeli duplikatumok oszlopindexeinek
	 *         halmaza
	 */
	public Set<Integer> sorKipirosit(int sor) {
		Set<Integer> e = new HashSet<>();
		for (int i = 0; i < 15; i++) {
			for (int j = i + 1; j < 16; j++) {
				if (tabla[i][sor] != 0 && tabla[i][sor] == tabla[j][sor]) {
					e.add(i);
					e.add(j);
				}
			}
		}
		logger.debug("A(z) {}. sorban kijelolte pirossal a duplikatumokat.",
				sor + 1);
		return e;
	}

	/**
	 * A paraméterül kapott oszlopból visszaadja a duplikatumok sorszámát.
	 * 
	 * @param oszlop
	 *            az adott oszlop indexe
	 * @return a paraméterül kapott oszlopbeli duplikatumok sorindexeinek
	 *         halmaza
	 */
	public Set<Integer> oszlopKipirosit(int oszlop) {
		Set<Integer> e = new HashSet<>();
		for (int i = 0; i < 15; i++) {
			for (int j = i + 1; j < 16; j++) {
				if (tabla[oszlop][i] != 0
						&& tabla[oszlop][i] == tabla[oszlop][j]) {
					e.add(i);
					e.add(j);
				}
			}
		}
		logger.debug("A(z) {}. oszlopban kijelolte pirossal a duplikatumokat.",
				oszlop + 1);
		return e;
	}

	/**
	 * Egy adott négyzetből visszaadja a duplikatumok koordinátáit.
	 * 
	 * @param sor
	 *            a négyzet bármely mezőjének egy sorindexe
	 * @param oszlop
	 *            a négyzet bármely mezőjének egy oszlopindexe
	 * @return az adott négyzetben található duplikátumok {@code Koordinatak}-á
	 *         alakítva
	 * @see Koordinatak
	 */
	public Set<Koordinatak> negyzetKipirosit(int sor, int oszlop) {
		Set<Koordinatak> e = new HashSet<>();
		int kezdoSor = sor - sor % 4;
		int kezdoOszlop = oszlop - oszlop % 4;
		int utolsoSor = kezdoSor + 4;
		int utolsoOszlop = kezdoOszlop + 4;
		for (int i = kezdoSor; i < utolsoSor; i++) {
			for (int j = kezdoOszlop; j < utolsoOszlop; j++) {
				if (tabla[j][i] != 0) {
					for (int i2 = kezdoSor; i2 < utolsoSor; i2++) {
						for (int j2 = kezdoOszlop; j2 < utolsoOszlop; j2++) {
							if (i != i2 && j != j2) {
								if (tabla[j][i] == tabla[j2][i2]) {
									Koordinatak seged1 = new Koordinatak(j, i);
									Koordinatak seged2 = new Koordinatak(j2, i2);
									boolean bennevan1 = false;
									boolean bennevan2 = false;
									for (Koordinatak seged : e) {
										if (seged.equals(seged1))
											bennevan1 = true;
										if (seged.equals(seged2))
											bennevan2 = true;
									}

									if (!bennevan1) {
										e.add(seged1);
									}
									if (!bennevan2) {
										e.add(seged2);
									}
								}
							}
						}
					}
				}
			}
		}
		logger.debug(
				"Az adott ({}, {}), ({}, {}) koordinatak altal behatarolt negyzetben kijelolte pirossal a duplikatumokat.",
				kezdoSor + 1, kezdoOszlop + 1, kezdoSor + 4, kezdoOszlop + 4);
		return e;
	}

	/**
	 * Adott sor logikai érvényességét határozza meg.
	 * 
	 * @param sor
	 *            az adott sor indexe
	 * @return igazat ad vissza, ha nincsenek duplikátumok a sorban, a többi
	 *         esetben hamisat
	 */
	public boolean sorErvenyessege(int sor) {
		for (int i = 0; i < 15; i++) {
			for (int j = i + 1; j < 16; j++) {
				if (tabla[i][sor] == tabla[j][sor]) {
					logger.debug("A(z) {}. sor nem ervenyes.", sor + 1);
					return false;
				}
			}
		}
		logger.debug("A(z) {}. sor ervenyes.", sor + 1);
		return true;
	}

	/**
	 * Adott oszlop logikai érvényességét határozza meg.
	 * 
	 * @param oszlop
	 *            az adott oszlop indexe
	 * @return igazat ad vissza, ha nincsenek duplikátumok az oszlopban, a többi
	 *         esetben hamisat
	 */
	public boolean oszlopErvenyessege(int oszlop) {
		for (int i = 0; i < 15; i++) {
			for (int j = i + 1; j < 16; j++) {
				if (tabla[oszlop][i] == tabla[oszlop][j]) {
					logger.debug("A(z) {}. oszlop nem ervenyes.", oszlop + 1);
					return false;
				}
			}
		}
		logger.debug("A(z) {}. oszlop ervenyes.", oszlop + 1);
		return true;
	}

	/**
	 * Adott kis négyzet logikai érvényességét határozza meg.
	 * 
	 * @param sor
	 *            a négyzet bármely mezőjének egy sorindexe
	 * @param oszlop
	 *            a négyzet bármely mezőjének egy oszlopindexe
	 * @return igazat ad vissza, ha nincsenek duplikátumok az adott
	 *         kisnégyzetben, a többi esetben hamisat
	 */
	public boolean negyzetErvenyessege(int sor, int oszlop) {
		int utolsoSor = sor + 4;
		int utolsoOszlop = oszlop + 4;
		for (int i = sor; i < utolsoSor; i++) {
			for (int j = oszlop; j < utolsoOszlop; j++) {
				for (int i2 = sor; i2 < utolsoSor; i2++) {
					for (int j2 = oszlop; j2 < utolsoOszlop; j2++) {
						if (i != i2 && j != j2) {
							if (tabla[j][i] == tabla[j2][i2]) {
								logger.debug(
										"Az adott ({}, {}), ({}, {}) koordinatak altal behatarolt negyzet ervenytelen.",
										sor + 1, oszlop + 1, sor + 4,
										oszlop + 4);
								return false;
							}
						}
					}
				}
			}
		}
		logger.debug(
				"Az adott ({}, {}), ({}, {}) koordinatak altal behatarolt negyzet ervenyes.",
				sor + 1, oszlop + 1, sor + 4, oszlop + 4);
		return true;
	}

	/**
	 * A teljes tábla kitöltöttségét vizsgálja, logikai értékkel visszatérve.
	 * 
	 * @return igazat ad vissza, ha minden mező ki van töltve (nem 0), a többi
	 *         esetben hamisat
	 */
	public boolean kiToltveE() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (tabla[i][j] == 0) {
					logger.debug("A tabla meg nincs kitoltve.");
					return false;
				}
			}
		}
		logger.debug("A tabla ki van toltve.");
		return true;
	}

	/**
	 * Visszaadja a tábla logikai helyzetét megfelelően a játék szabályainak és
	 * logikájának.
	 * 
	 * @return igazat ad vissza, ha a kitöltött tábla megfelel a sudoku játék
	 *         minden szabályának, a többi esetben hamisat
	 */
	public boolean vegeE() {
		if (kiToltveE()) {
			for (int i = 0; i < 16; i++) {
				if (!sorErvenyessege(i)) {
					logger.debug(
							"A tabla meg nincs helyesen kitoltve, a(z) {}. sorban van a hiba.",
							i + 1);
					return false;
				}
				if (!oszlopErvenyessege(i)) {
					logger.debug(
							"A tabla meg nincs helyesen kitoltve, a(z) {}. oszlopban van a hiba.",
							i + 1);
					return false;
				}
				if (i % 4 == 0) {
					for (int j = 0; j < 16; j += 4) {
						if (!negyzetErvenyessege(i, j)) {
							logger.debug(
									"A tabla meg nincs helyesen kitoltve, a ({}, {}), ({}, {}) koordinatak altal behatarolt negyzetben van a hiba.",
									i + 1, j + 1, i + 4, j + 4);
							return false;
						}
					}
				}
			}
			logger.debug("A tabla helyesen van kitoltve.");
			return true;
		}
		logger.debug("A tabla nincs helyesen kitoltve, nincs minden mezo kitoltve.");
		return false;
	}

	/**
	 * Adott sor logikai ürességét vizsgálja meg.
	 * 
	 * @param sor
	 *            az adott sor indexe
	 * @return igazat ad vissza, ha az adott sor ütes (csak 0-at tartalmaz), a
	 *         többi esetben hamisat
	 */
	public boolean sorUresE(int sor) {
		for (int i = 0; i < 16; i++) {
			if (tabla[i][sor] != 0) {
				logger.debug("A(z) {}. sor nem ures.", sor + 1);
				return false;
			}
		}
		logger.debug("A(z) {}. sor ures.", sor + 1);
		return true;
	}

	/**
	 * Adott oszlop logikai ürességét vizsgálja meg.
	 * 
	 * @param oszlop
	 *            az adott oszlop indexe
	 * @return igazat ad vissza, ha az adott oszlop ütes (csak 0-at tartalmaz),
	 *         a többi esetben hamisat
	 */
	public boolean oszlopUresE(int oszlop) {
		for (int i = 0; i < 16; i++) {
			if (tabla[oszlop][i] != 0) {
				logger.debug("A(z) {}. oszlop nem ures.", oszlop + 1);
				return false;
			}
		}
		logger.debug("A(z) {}. oszlop ures.", oszlop + 1);
		return true;
	}

	/**
	 * Törli a tábla teljes tartalmát és újat hoz létre.
	 */
	public void tablaTorles() {
		tabla = new int[16][16];
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				modosithato[i][j] = true;
			}
		}
		logger.debug("Regi tabla torolve, uj letrehozva.");
	}

	/**
	 * Beír az adott pozícióba egy adott értéket.
	 * 
	 * @param sor
	 *            az adott sor indexe
	 * @param oszlop
	 *            az adott oszlop indexe
	 * @param szam
	 *            a beírandó szám
	 */
	public void szamBeirasa(int sor, int oszlop, int szam) {
		tabla[sor][oszlop] = szam;
		// modosithato[sor][oszlop]=false;
	}

	/**
	 * Egy adott mező értékét törli ki.
	 * 
	 * @param sor
	 *            az adott sor indexe
	 * @param oszlop
	 *            az adott oszlop indexe
	 */
	public void ertekTorol(int sor, int oszlop) {
		szamBeirasa(sor, oszlop, 0);
		// modosithato[sor][oszlop]=true;
	}

	/**
	 * Megváltoztatja egy adott mező logikai megváltoztathatóságát.
	 * 
	 * @param modosithato
	 *            az uj logikai módosíthatósági érték
	 * @param sor
	 *            az adott sor indexe
	 * @param oszlop
	 *            az adott oszlop indexe
	 */
	public void setModosithatosag(int sor, int oszlop, boolean modosithato) {
		this.modosithato[sor][oszlop] = modosithato;
	}

	/**
	 * Egy adott mező értékének lekérdezése.
	 * 
	 * @param sor
	 *            az adott sor indexe
	 * @param oszlop
	 *            az adott oszlop indexe
	 * @return a paraméterűl kapott sor- és oszlopindexel rendelkező mező értéke
	 */
	public int getErtek(int sor, int oszlop) {
		return tabla[oszlop][sor];
	}

	/**
	 * Egy adott mező logikai módosíthatóságának lekérdezése.
	 * 
	 * @param sor
	 *            az adott sor indexe
	 * @param oszlop
	 *            az adott oszlop indexe
	 * @return a paraméterűl kapott sor- és oszlopindexel rendelkező mező
	 *         logikai módosíthatóságának értéke
	 */
	public boolean modosithatoE(int sor, int oszlop) {
		return modosithato[oszlop][sor];
	}

	/**
	 * Az alapértelmezett kimenetre kiírja a tábla pillanatnyi helyzetét.
	 */
	public void kiir() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				System.out.print(tabla[j][i] + " ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

}
