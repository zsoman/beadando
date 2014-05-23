package hu.unideb.inf;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * {@link Sudoku9x9} mezőt reprezentáló osztály mely kibővíti a
 * {@link JTextField} osztályt.
 * 
 * @author Bokor Zsolt Levente
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SzamTabla16x16 extends JTextField {

	/**
	 * A mező sorindexe.
	 */
	private int sor;

	/**
	 * A mező oszlopindexe.
	 */
	private int oszlop;

	/**
	 * A mező színét reprezentáló egész szám.
	 */
	private int piros;

	/**
	 * A {@link SzamTabla9x9} osztály konstruktora amely létrehoz az
	 * {@link Ablak9x9} grafikus felületen egy adott mezőt.
	 * <p>
	 * Ez az osztály minden egyes mező esetén példányosítódik a konstruktorán
	 * keresztül, {@code 9x9}-es tábla esetén {@code 81} szer, ekkor állítódik
	 * be minden mező esetén az adott mező jellemzői.
	 * 
	 * @param sor
	 *            a sor indexe
	 * @param oszlop
	 *            az oszlop indexe
	 */
	public SzamTabla16x16(int sor, int oszlop) {
		super();
		this.sor = sor;
		this.oszlop = oszlop;
		this.piros = 0;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setHorizontalAlignment(JTextField.CENTER);
		this.setFont(new Font("serif", Font.PLAIN, 24));
		this.getCaret().setBlinkRate(0);
		this.getCaret().setVisible(false);
		this.setCaretColor(this.getBackground());
		this.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a)
					throws BadLocationException {
				String megengedett = "123456789ABCDEFGabcdefg";
				String karakterek = "ABCDEFGabcdefg";
				if (getLength() + str.length() <= 2) {
					if (megengedett.contains(str)) {
						if (karakterek.contains(str)) {
							String str2 = atalakit(str);
							System.out.println(str2
									+ "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
							super.insertString(offs, str2, a);
							Ablak16x16.jatek.szamBeirasa(getSor(), getOszlop(),
									Integer.parseInt(str2));
							GUISegedletek16x16.pirositSor(getOszlop());
							GUISegedletek16x16.pirositOszlop(getSor());
							GUISegedletek16x16.pirositNegyzet(getOszlop(),
									getSor());
						} else {
							super.insertString(offs, str, a);
							Ablak16x16.jatek.szamBeirasa(getSor(), getOszlop(),
									Integer.parseInt(str));
							GUISegedletek16x16.pirositSor(getOszlop());
							GUISegedletek16x16.pirositOszlop(getSor());
							GUISegedletek16x16.pirositNegyzet(getOszlop(),
									getSor());
						}
						if (Ablak16x16.jatek.vegeE()) {
							JOptionPane.showMessageDialog(getParent(),
									"Gratulálok, megoldottad a játékot!");
						}
					} else {
						String seged;
						if (Ablak16x16.jatek.getErtek(getOszlop(), getSor()) == 0) {
							seged = "";
							Ablak16x16.jatek.ertekTorol(getOszlop(), getSor());
						} else {
							seged = Integer.toString(Ablak16x16.jatek.getErtek(
									getOszlop(), getSor()));
						}
						super.insertString(offs, seged, a);
					}
				}
				setSelectedTextColor(getForeground());
				setSelectionColor(getBackground());
				selectAll();
			}
		});

		this.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (isEditable()) {
						Ablak16x16.jatek.szamBeirasa(getSor(), getOszlop(), 0);
						selectAll();
					}
					GUISegedletek16x16.pirositSor(getOszlop());
					GUISegedletek16x16.pirositOszlop(getSor());
					GUISegedletek16x16.pirositNegyzet(getOszlop(), getSor());
					setSelectedTextColor(getForeground());
				} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (getSor() < 15) {
						Ablak16x16.szamTabla[getSor() + 1][getOszlop()]
								.requestFocus();
					} else {
						Ablak16x16.szamTabla[0][getOszlop()].requestFocus();
					}
				} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
					if (getSor() > 0) {
						Ablak16x16.szamTabla[getSor() - 1][getOszlop()]
								.requestFocus();
					} else {
						Ablak16x16.szamTabla[15][getOszlop()].requestFocus();
					}
				} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					if (getOszlop() < 15) {
						Ablak16x16.szamTabla[getSor()][getOszlop() + 1]
								.requestFocus();
					} else {
						Ablak16x16.szamTabla[getSor()][0].requestFocus();
					}
				} else if (arg0.getKeyCode() == KeyEvent.VK_UP) {
					if (getOszlop() > 0) {
						Ablak16x16.szamTabla[getSor()][getOszlop() - 1]
								.requestFocus();
					} else {
						Ablak16x16.szamTabla[getSor()][15].requestFocus();
					}
				}
			}
		});
		this.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				if (isEditable()) {
					setBackground(Color.green);
				}
				setCaretColor(getBackground());
				setSelectionColor(getBackground());
			}

			@Override
			public void focusLost(FocusEvent e) {
				super.focusLost(e);
				if (isEditable()) {
					setBackground(Color.white);
					setSelectionColor(getBackground());
				}
			}
		});
	}

	/**
	 * Adott stringből, mely egy karakterből épül fel átalakítja számmá melyet
	 * reprezentál a sudoku játékban.
	 * <p>
	 * A: 10 B: 11 C: 12 D: 13 E: 14 F: 15 G: 16
	 * 
	 * @param str egy karakterből felépülő karakterlánc mely átkonvertálandó
	 * @return az adott karakter által reprezentált számot adja vissza
	 */
	private String atalakit(String str) {
		switch (str.charAt(0)) {
		case 'A':
			return "10";
		case 'B':
			return "11";
		case 'C':
			return "12";
		case 'D':
			return "13";
		case 'E':
			return "14";
		case 'F':
			return "15";
		case 'G':
			return "16";
		case 'a':
			return "10";
		case 'b':
			return "11";
		case 'c':
			return "12";
		case 'd':
			return "13";
		case 'e':
			return "14";
		case 'f':
			return "15";
		case 'g':
			return "16";
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Visszaadja a mező színét reprezentáló egész számot.
	 * <p>
	 * 0 - fekete 1 - piros sorbeli duplikátum miatt 2 - piros oszlopbeli
	 * duplikátum miatt 3 - piros négyzetbeli duplikátum miatt
	 * 
	 * @return 0-3 intervallumbeli színt ábrázoló szám
	 */
	public int getPiros() {
		return piros;
	}

	/**
	 * Beállítja a mező színét reprezentáló egész számot.
	 * <p>
	 * 0 - fekete 1 - piros sorbeli duplikátum miatt 2 - piros oszlopbeli
	 * duplikátum miatt 3 - piros négyzetbeli duplikátum miatt
	 * 
	 * @param piros
	 *            0-3 intervallumbeli színt ábrázoló szám
	 */
	public void setPiros(int piros) {
		this.piros = piros;
		if (piros > 0) {
			this.setForeground(Color.red);
		} else {
			this.setForeground(Color.black);
		}
	}

	/**
	 * Visszaadja a mező sorindexét.
	 * 
	 * @return a mező sorindexe
	 */
	public int getSor() {
		return sor;
	}

	/**
	 * Visszaadja a mező oszlopindexét.
	 * 
	 * @return a mező oszlopindexe
	 */
	public int getOszlop() {
		return oszlop;
	}
}
