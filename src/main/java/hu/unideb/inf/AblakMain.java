package hu.unideb.inf;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JFormattedTextField;

import java.awt.SystemColor;
import java.awt.Cursor;

import javax.swing.SwingConstants;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Locale;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * A kezdő ablak.
 * 
 * @author Bokor Zsolt Levente
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AblakMain extends JFrame {
	
	/**
	 * Az {@link AblakMain} osztály konténerje.
	 */
	private JPanel contentPane;
	
	/**
	 * Az {@link Ablak9x9} frameje.
	 */
	static Ablak9x9 frame9x9 = new Ablak9x9();
	/**
	 * Az {@link AblakMain} frameje.
	 */
	static AblakMain frame= new AblakMain();
	/**
	 * Az {@link Ablak16x16} frameje.
	 */
	static Ablak16x16 frame16x16= new Ablak16x16();
	
	/**
	 * A main metodus.
	 * 
	 * @param args argumentumok
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame9x9.setVisible(false);
					frame.setVisible(true);
					frame16x16.setVisible(false);
					frame9x9.setSize(395, 445);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Az ablak legyártása.
	 */
	public AblakMain() {
		setTitle("Sudoku");
		setResizable(false);
		setBackground(new Color(240, 240, 240));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 450, 204);
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JFormattedTextField frmtdtxtflddvzljkASudoku = new JFormattedTextField();
		frmtdtxtflddvzljkASudoku.setFocusable(false);
		frmtdtxtflddvzljkASudoku.setSize(444, 27);
		frmtdtxtflddvzljkASudoku.setLocation(new Point(0, 26));
		frmtdtxtflddvzljkASudoku.setLocale(new Locale("hu"));
		frmtdtxtflddvzljkASudoku.setFont(new Font("Tahoma", Font.PLAIN, 22));
		frmtdtxtflddvzljkASudoku.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frmtdtxtflddvzljkASudoku.setCaretColor(SystemColor.activeCaption);
		frmtdtxtflddvzljkASudoku.setMinimumSize(new Dimension(24, 20));
		frmtdtxtflddvzljkASudoku.setBorder(null);
		frmtdtxtflddvzljkASudoku.setBackground(SystemColor.control);
		frmtdtxtflddvzljkASudoku.setHorizontalAlignment(SwingConstants.CENTER);
		frmtdtxtflddvzljkASudoku.setEditable(false);
		frmtdtxtflddvzljkASudoku.setText("Üdvözöljük a Sudoku játékban");
		contentPane.add(frmtdtxtflddvzljkASudoku);
		
		JFormattedTextField frmtdtxtfldDsvsgrhhrhrhehhh = new JFormattedTextField();
		frmtdtxtfldDsvsgrhhrhrhehhh.setEditable(false);
		frmtdtxtfldDsvsgrhhrhrhehhh.setFocusable(false);
		frmtdtxtfldDsvsgrhhrhrhehhh.setHorizontalAlignment(SwingConstants.CENTER);
		frmtdtxtfldDsvsgrhhrhrhehhh.setBorder(null);
		frmtdtxtfldDsvsgrhhrhrhehhh.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmtdtxtfldDsvsgrhhrhrhehhh.setBackground(SystemColor.control);
		frmtdtxtfldDsvsgrhhrhrhehhh.setText("Kérem válasszon a játéktípusok között:");
		frmtdtxtfldDsvsgrhhrhrhehhh.setBounds(10, 64, 424, 60);
		contentPane.add(frmtdtxtfldDsvsgrhhrhrhehhh);
		
		final JButton btnDecimalis = new JButton("Decimális");
		btnDecimalis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDecimalis.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				frame9x9.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnDecimalis.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDecimalis.setToolTipText("9x9-es tábla, decimális számrendszerben.");
		btnDecimalis.setBounds(30, 127, 146, 23);
		contentPane.add(btnDecimalis);
		
		JButton btnHexadecimlis = new JButton("Hexadecimális");
		btnHexadecimlis.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				frame16x16.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnHexadecimlis.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnHexadecimlis.setToolTipText("16x16-os tábla, hexadecimális számrendszerben.");
		btnHexadecimlis.setBounds(270, 127, 146, 23);
		contentPane.add(btnHexadecimlis);
	}
}
