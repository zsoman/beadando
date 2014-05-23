/**
 * Sudoku interface csomagja.
 */
package hu.unideb.inf;

import java.util.Set;

/**
 * A sudoku osztályok fölött álló {@code Sudoku} interfésze.
 * 
 * @author Bokor Zsolt Levente
 * @version 1.0
 */
public interface Sudoku {
	/**
	 * A paraméterül kapott sorból visszaadja a duplikatumok oszlopszámát.
	 * 
	 * @param sor az adott sor indexe
	 * @return a paraméterül kapott sorbeli duplikatumok oszlopindexeinek halmaza
	 */
	public Set<Integer> sorKipirosit(int sor);

	/**
	 * A paraméterül kapott oszlopból visszaadja a duplikatumok sorszámát.
	 * 
	 * @param oszlop az adott oszlop indexe
	 * @return a paraméterül kapott oszlopbeli duplikatumok sorindexeinek halmaza
	 */
	public Set<Integer> oszlopKipirosit(int oszlop);

	/**
	 * Egy adott négyzetből visszaadja a duplikatumok koordinátáit.
	 * 
	 * @param sor a négyzet bármely mezőjének egy sorindexe
	 * @param oszlop a négyzet bármely mezőjének egy oszlopindexe
	 * @return az adott négyzetben található duplikátumok {@code Koordinatak}-á alakítva
	 * @see Koordinatak
	 */
	public Set<Koordinatak> negyzetKipirosit(int sor, int oszlop);

	/**
	 * Adott sor logikai érvényességét határozza meg.
	 * 
	 * @param sor az adott sor indexe
	 * @return igazat ad vissza, ha nincsenek duplikátumok a sorban, a többi esetben hamisat
	 */
	public boolean sorErvenyessege(int sor);
	
	/**
	 * Adott oszlop logikai érvényességét határozza meg.
	 * 
	 * @param oszlop az adott oszlop indexe
	 * @return igazat ad vissza, ha nincsenek duplikátumok az oszlopban, a többi esetben hamisat
	 */
	public boolean oszlopErvenyessege(int oszlop);

	/**
	 * Adott kis négyzet logikai érvényességét határozza meg.
	 * 
	 * @param sor a négyzet bármely mezőjének egy sorindexe
	 * @param oszlop a négyzet bármely mezőjének egy oszlopindexe
	 * @return igazat ad vissza, ha nincsenek duplikátumok az adott kisnégyzetben, a többi esetben hamisat
	 */
	public boolean negyzetErvenyessege(int sor, int oszlop);

	/**
	 * A teljes tábla kitöltöttségét vizsgálja, logikai értékkel visszatérve.
	 * 
	 * @return igazat ad vissza, ha minden mező ki van töltve (nem 0), a többi esetben hamisat
	 */
	public boolean kiToltveE();
	
	/**
	 * Visszaadja a tábla logikai helyzetét megfelelően a játék szabályainak és logikájának.
	 * 
	 * @return igazat ad vissza, ha a kitöltött tábla megfelel a sudoku játék minden szabályának,
	 * 			a többi esetben hamisat
	 */
	public boolean vegeE();
	
	/**
	 * Adott sor logikai ürességét vizsgálja meg.
	 * 
	 * @param sor az adott sor indexe
	 * @return igazat ad vissza, ha az adott sor ütes (csak 0-at tartalmaz), a többi esetben hamisat
	 */
	public boolean sorUresE(int sor);

	/**
	 * Adott oszlop logikai ürességét vizsgálja meg.
	 * 
	 * @param oszlop az adott oszlop indexe
	 * @return igazat ad vissza, ha az adott oszlop ütes (csak 0-at tartalmaz), a többi esetben hamisat
	 */
	public boolean oszlopUresE(int oszlop);

	// public void teljesTablaModosithatosaga();
	
	/**
	 * Törli a tábla teljes tartalmát és újat hoz létre.
	 */
	public void tablaTorles();

	/**
	 * Beír az adott pozícióba egy adott értéket.
	 * 
	 * @param szam a beírandó szám
	 * @param sor az adott sor indexe
	 * @param oszlop az adott oszlop indexe
	 */
	public void szamBeirasa(int sor, int oszlop, int szam);

	/**
	 * Megváltoztatja egy adott mező logikai megváltoztathatóságát.
	 * 
	 * @param modositas az uj logikai módosíthatósági érték
	 * @param sor az adott sor indexe
	 * @param oszlop az adott oszlop indexe
	 */
	public void setModosithatosag(int sor, int oszlop, boolean modositas);

	/**
	 * Egy adott mező értékét törli ki.
	 * 
	 * @param sor az adott sor indexe
	 * @param oszlop az adott oszlop indexe
	 */
	public void ertekTorol(int sor, int oszlop);

	/**
	 * Egy adott mező értékének lekérdezése.
	 * 
	 * @param sor az adott sor indexe
	 * @param oszlop az adott oszlop indexe
	 * @return a paraméterűl kapott sor- és oszlopindexel rendelkező mező értéke
	 */
	public int getErtek(int sor, int oszlop);

	/**
	 * Egy adott mező logikai módosíthatóságának lekérdezése.
	 * 
	 * @param sor az adott sor indexe
	 * @param oszlop az adott oszlop indexe
	 * @return a paraméterűl kapott sor- és oszlopindexel rendelkező mező logikai módosíthatóságának értéke
	 */
	public boolean modosithatoE(int sor, int oszlop);

	/**
	 * Az alapértelmezett kimenetre kiírja a tábla pillanatnyi helyzetét.
	 */
	public void kiir();
}
