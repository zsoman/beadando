package hu.unideb.inf;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * A {@link Sudoku16x16} osztályhoz tartozó grafikus felület, melyet a felhasználó használ játék közben. 
 * 
 * @author Bokor Zsolt Levente
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Ablak16x16 extends JFrame {

	/**
	 * Az {@link Ablak16x16} osztály konténerje.
	 */
	private JPanel contentPane;
	
	/**
	 * A mezők táblája.
	 * @see SzamTabla16x16
	 */
	static SzamTabla16x16[][] szamTabla = new SzamTabla16x16[16][16];
	
	/**
	 * a {@code Sudoku16x16} osztály egy példánya.
	 */
	static Sudoku16x16 jatek = new Sudoku16x16();
	
	/**
	 * Az osztály loggere.
	 */
	private static Logger logger = LoggerFactory.getLogger(Ablak16x16.class);

	/**
	 * A {@link Ablak16x16} konstruktora.
	 * <p>
	 * Létrehozza az osztály ablakát, melyben jellennek meg a mezők is a játék számára.
	 */
	public Ablak16x16() {
		setResizable(false);
		setTitle("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 535, 585);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("Fájl");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("Új");
		mntmNew.setMnemonic('U');
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("Megnyomta a Uj-t");
				Object[] lehetosegek = { "intermediate16x16",
						"challenging16x16", "tough16x16", "superTough16x16" };
				String s = (String) JOptionPane.showInputDialog(getFrames()[0],
						"Válassz nehézségi szintet", "Új játék",
						JOptionPane.PLAIN_MESSAGE, null, lehetosegek, "easy");
				Random r = new Random(new Date().getTime());
				int id = r.nextInt(1);
				StringBuilder sb = new StringBuilder();
				sb.append("/").append(s).append(".xml");
				logger.debug("A(z) {} fajlbol a(z) {}. tablat fogja beolvasni",
						sb.toString(), id);
				try {
					SudokuXML16x16.betoltTabla(jatek, Ablak16x16.class
							.getResource(sb.toString()).toString(), id);
					GUISegedletek16x16.kirajzolTabla();
				} catch (NullPointerException e) {
					logger.error("Hianyzik a(z) {} XML fajl", sb.toString());
					JOptionPane.showMessageDialog(null,
							"Hiányzik a bemeneti fájl!",
							"Hiba", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnFile.add(mntmNew);

		JMenuItem mntmSave = new JMenuItem("Mentés");
		mntmSave.setMnemonic('S');
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("Megnyomta a Mentes-t");
				JFileChooser fc = new JFileChooser();
				UIManager.put("FileChooser.openDialogTitleText", "Mentés");
				UIManager.put("FileChooser.lookInLabelText", "Keresés:");
				UIManager.put("FileChooser.openButtonText", "Mentés");
				UIManager.put("FileChooser.cancelButtonText", "Bezárás");
				UIManager.put("FileChooser.fileNameLabelText", "Fájl neve");
				UIManager
						.put("FileChooser.filesOfTypeLabelText", "Fájl típusa");
				UIManager.put("FileChooser.openButtonToolTipText",
						"Fájl mentése");
				UIManager.put("FileChooser.cancelButtonToolTipText", "Bezár");
				UIManager.put("FileChooser.fileNameHeaderText", "Fájl neve");
				UIManager.put("FileChooser.upFolderToolTipText",
						"Vissza a mappában");
				UIManager.put("FileChooser.homeFolderToolTipText", "Desktop");
				UIManager.put("FileChooser.newFolderToolTipText",
						"Új mappa létrehozása");
				UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
				UIManager.put("FileChooser.newFolderButtonText",
						"Új mappa létrehozása");
				UIManager.put("FileChooser.renameFileButtonText",
						"Fájl újranevezése");
				UIManager.put("FileChooser.deleteFileButtonText",
						"Fájl törlése");
				UIManager.put("FileChooser.filterLabelText", "Fájl típusok");
				UIManager.put("FileChooser.detailsViewButtonToolTipText",
						"Részletek");
				UIManager.put("FileChooser.fileSizeHeaderText", "Méret");
				UIManager.put("FileChooser.fileDateHeaderText",
						"Megváltoztatás dátuma");
				FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
						"xml fajlok (*.xml)", "xml");
				fc.setFileFilter(xmlfilter);
				SwingUtilities.updateComponentTreeUI(fc);
				fc.setAcceptAllFileFilterUsed(false);
				fc.showOpenDialog(getParent());
				try {
					File f = fc.getSelectedFile();
					logger.debug(
							"A kovetkezo fajlt valasztotta ki: {} a mentes celjanak",
							f.getName());
					SudokuXML16x16.lementTabla(jatek, f.getAbsolutePath()+".xml");
				} catch (NullPointerException e) {
					logger.error("Nem lehet a megadott fajlba menteni");
				}
			}
		});
		mnFile.add(mntmSave);

		JMenuItem mntmLoad = new JMenuItem("Betöltés");
		mntmLoad.setMnemonic('O');
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("Megnyomta a Betoltes-t");
				JFileChooser fc = new JFileChooser();
				UIManager.put("FileChooser.openDialogTitleText", "Betöltés");
				UIManager.put("FileChooser.lookInLabelText", "Keresés:");
				UIManager.put("FileChooser.openButtonText", "Betöltés");
				UIManager.put("FileChooser.cancelButtonText", "Bezárás");
				UIManager.put("FileChooser.fileNameLabelText", "Fájl neve");
				UIManager
						.put("FileChooser.filesOfTypeLabelText", "Fájl típusa");
				UIManager.put("FileChooser.openButtonToolTipText",
						"Fájl mentése");
				UIManager.put("FileChooser.cancelButtonToolTipText", "Bezár");
				UIManager.put("FileChooser.fileNameHeaderText", "Fájl neve");
				UIManager.put("FileChooser.upFolderToolTipText",
						"Vissza a mappában");
				UIManager.put("FileChooser.homeFolderToolTipText", "Desktop");
				UIManager.put("FileChooser.newFolderToolTipText",
						"Új mappa létrehozása");
				UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
				UIManager.put("FileChooser.newFolderButtonText",
						"Új mappa létrehozása");
				UIManager.put("FileChooser.renameFileButtonText",
						"Fájl újranevezése");
				UIManager.put("FileChooser.deleteFileButtonText",
						"Fájl törlése");
				UIManager.put("FileChooser.filterLabelText", "Fájl típusok");
				UIManager.put("FileChooser.detailsViewButtonToolTipText",
						"Részletek");
				UIManager.put("FileChooser.fileSizeHeaderText", "Méret");
				UIManager.put("FileChooser.fileDateHeaderText",
						"Megváltoztatás dátuma");
				FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
						"xml fajlok (*.xml)", "xml");
				fc.setFileFilter(xmlfilter);
				SwingUtilities.updateComponentTreeUI(fc);
				fc.setAcceptAllFileFilterUsed(false);
				fc.showOpenDialog(getParent());
				try {
					File f = fc.getSelectedFile();
					if (f.exists()) {
						logger.debug(
								"A kovetkezo fajlt valasztotta ki: {} betoltesre",
								f.getName());
						SudokuXML16x16.betoltTabla(jatek, f.getAbsolutePath(),
								0);
						GUISegedletek16x16.kirajzolTabla();
					}
				} catch (NullPointerException e) {
					logger.error("Nem lehet a megadott fajlbol beolvasni");
				}
			}
		});
		mnFile.add(mntmLoad);

		JMenuItem mntmExit = new JMenuItem("Kilépés");
		mntmExit.setMnemonic('K');
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("Kilepett");
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Sugó");
		mnHelp.setMnemonic('H');
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("Rólunk");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://www.inf.unideb.hu/"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		mntmAbout.setMnemonic('R');
		mnHelp.add(mntmAbout);
		
		JMenuItem mntmSudoku = new JMenuItem("Sudokuról");
		mntmSudoku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://hu.wikipedia.org/wiki/Sz%C3%BAdoku"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		mntmSudoku.setMnemonic('D');
		mnHelp.add(mntmSudoku);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridBagLayout());
		setContentPane(contentPane);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				gbc.gridx = i;
				gbc.gridy = j;
				szamTabla[i][j] = new SzamTabla16x16(i, j);
				if (i % 4 == 3 && j % 4 == 3 && i != 15 && j != 15) {
					gbc.insets = new Insets(0, 0, 5, 5);
				} else if (i % 4 == 3 && i != 15) {
					gbc.insets = new Insets(0, 0, 0, 5);
				} else if (j % 4 == 3 && j != 15) {
					gbc.insets = new Insets(0, 0, 5, 0);
				} else {
					gbc.insets = new Insets(0, 0, 0, 0);
				}
				contentPane.add(szamTabla[i][j], gbc);
			}
		}
		logger.debug("Letrehozta a szamtablakat, es hozzaadta a contentPane-hez");
	}
}
