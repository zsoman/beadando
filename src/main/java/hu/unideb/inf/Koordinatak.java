/**
 * 
 */
package hu.unideb.inf;

/**
 * Kétdimenzós koordinátarendszerben ábrazolandó koordináták implementációja.
 * 
 * @author Bokor Zsolt Levente
 * @version 1.0
 */
public class Koordinatak {
	/**
	 * A pont {@code x} tengelyre vett vetülete.
	 */
	private int x;
	/**
	 * A pont {@code y} tengelyre vett vetülete.
	 */
	private int y;
	
	/**
	 * Az {@code x} koordináta lekérdező metódusa.
	 * 
	 * @return az {@code x} koordináta értékét adja vissza
	 */
	public int getX() {
		return x;
	}

	/**
	 * Az {@code x} koordináta beállító metódusa.
	 *
	 * @param x az {@code x} koordináta új értéke
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Az {@code y} koordináta lekérdező metódusa.
	 * 
	 * @return az {@code y} koordináta értékét adja vissza
	 */
	public int getY() {
		return y;
	}

	/**
	 * Az {@code y} koordináta beállító metódusa.
	 *
	 * @param y az {@code y} koordináta új értéke
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * A {@code Koordinatak} osztály konstruktora, felépíti a koordináta struktúrát.
	 * 
	 * @param x a koordináta {@code x} értéke
	 * @param y a koordináta {@code y} értéke
	 */
	public Koordinatak(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Az aktuális objektumot hasonlítja össze egy másik objektummal.
	 * 
	 * @param obj az aktuális objektumot hasonlítjuk össze ezzel az objektummal
	 * @return visszaadja az összehasonlítás logikai értékét
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass().equals(Koordinatak.class)) {
			Koordinatak o = (Koordinatak) obj;
			if (this.x == o.x && this.y == o.y) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Az aktuális {@code Koordinatak} objektum karakterlánc reprezentációját hozza létre.
	 * 
	 *  @return a létrehozott karakterlánc reprezentációt adja vissza
	 */
	@Override
	public String toString() {
		return "( " + x + ", " + y + " )";
	}

}
