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
public class SzamTabla9x9 extends JTextField {

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
	public SzamTabla9x9(int sor, int oszlop) {
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
				String megengedett = "123456789";
				if (getLength() + str.length() <= 1) {
					if (megengedett.contains(str)) {
						super.insertString(offs, str, a);
						Ablak9x9.jatek.szamBeirasa(getSor(), getOszlop(),
								Integer.parseInt(str));
						GUISegedletek9x9.pirositSor(getOszlop());
						GUISegedletek9x9.pirositOszlop(getSor());
						GUISegedletek9x9.pirositNegyzet(getOszlop(), getSor());
						if (Ablak9x9.jatek.vegeE()) {
							JOptionPane.showMessageDialog(getParent(),
									"Gratulálok, megoldottad a játékot!");
						}
					} else {
						String seged;
						if (Ablak9x9.jatek.getErtek(getOszlop(), getSor()) == 0) {
							seged = "";
							Ablak9x9.jatek.ertekTorol(getOszlop(), getSor());
						} else {
							seged = Integer.toString(Ablak9x9.jatek.getErtek(
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
						Ablak9x9.jatek.szamBeirasa(getSor(), getOszlop(), 0);
						selectAll();
					}
					GUISegedletek9x9.pirositSor(getOszlop());
					GUISegedletek9x9.pirositOszlop(getSor());
					GUISegedletek9x9.pirositNegyzet(getOszlop(), getSor());
					setSelectedTextColor(getForeground());
				} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (getSor() < 8) {
						Ablak9x9.szamTabla[getSor() + 1][getOszlop()]
								.requestFocus();
					} else {
						Ablak9x9.szamTabla[0][getOszlop()].requestFocus();
					}
				} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
					if (getSor() > 0) {
						Ablak9x9.szamTabla[getSor() - 1][getOszlop()]
								.requestFocus();
					} else {
						Ablak9x9.szamTabla[8][getOszlop()].requestFocus();
					}
				} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					if (getOszlop() < 8) {
						Ablak9x9.szamTabla[getSor()][getOszlop() + 1]
								.requestFocus();
					} else {
						Ablak9x9.szamTabla[getSor()][0].requestFocus();
					}
				} else if (arg0.getKeyCode() == KeyEvent.VK_UP) {
					if (getOszlop() > 0) {
						Ablak9x9.szamTabla[getSor()][getOszlop() - 1]
								.requestFocus();
					} else {
						Ablak9x9.szamTabla[getSor()][8].requestFocus();
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
