package zahlLesen;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Diese Klasse steuert den Zeilen-Einlese-Automaten und generiert ihm eine graphische Oberflaeche.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class DezZahlEinlesen {
	
	/**Programmfenster*/
	private JFrame frame1 = new JFrame("Zahl einlesen");
	/**Textfeld mit Einlesestring*/
	private JTextField eingabe = new JTextField("-42.195");
	/**Button zum Auswerten*/
	private JButton auswerten = new JButton("Auswerten");
	/**Zustand des Automaten*/
	private JLabel zustandLabel = new JLabel();
	/**Erlaeuterung des Zustands*/
	private JLabel zustandErlLabel = new JLabel();
	/**Button zum Reseten*/
	private JButton reset = new JButton("Reset");
	
	public DezZahlEinlesen() {
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setPreferredSize(new Dimension(200,200));
		frame1.setMinimumSize(new Dimension(200,200));
		frame1.setMaximumSize(new Dimension(300,300));
		frame1.setResizable(true);
		Container cp = frame1.getContentPane();
		cp.setLayout(new GridBagLayout());
		
		eingabe.setHorizontalAlignment(SwingConstants.CENTER);
		zustandLabel.setHorizontalAlignment(SwingConstants.CENTER);
		zustandLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		zustandErlLabel.setHorizontalAlignment(SwingConstants.CENTER);
		auswerten.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				String eingabeStr = eingabe.getText();
			    char[] zeichenArr = eingabeStr.toCharArray();
			    int zustand = 0;
			    for(int i=0; i<zeichenArr.length;i++) {
			    	zustand = f(zustand, zeichenArr[i]);
			    }
			    zustandLabel.setText(String.valueOf(zustand));
			    if(zustand==2 || zustand==4) {
			    	zustandErlLabel.setText("akzeptiert");
			    } else if(zustand==5) {
			    	zustandErlLabel.setText("ungültige Zeichen");
			    } else {
			    	zustandErlLabel.setText("nicht akzeptiert");
			    }
			}
		});
		
		reset.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				eingabe.setText("");
				zustandLabel.setText("");
				zustandErlLabel.setText("");
			}
		});
		
		cp.add(eingabe, new GridBagFelder(0,0,1,1,1,0.1));
		cp.add(auswerten, new GridBagFelder(0,1,1,1,1,0.2));
		cp.add(zustandLabel, new GridBagFelder(0,2,1,1,1,0.4));
		cp.add(zustandErlLabel, new GridBagFelder(0,3,1,1,1,0.1));
		cp.add(reset, new GridBagFelder(0,4,1,1,1,0.2));
		
		frame1.pack();
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
	
	/**
	 * Diese Methode ist die Uebergangsfunktion des Automaten, welche fuer jedes Zeichen aus einem Zustand heraus
	 * einen neuen Zustand findet.
	 * @param zustand Der bisherige Zustand.
	 * @param zeichen Das einzulesende Zeichen.
	 * @return Gibt einen neuen Zustand zurueck.
	 */
	private int f(int zustand, char zeichen) {
		int zustandneu = 0;
		switch(zustand) {
		case 0:
			if(Character.isDigit(zeichen)) {
				zustandneu = 2;
			} else if(zeichen=='+' || zeichen=='-') {
				zustandneu = 1;
			} else {
				zustandneu = 5;
			}
			break;
		case 1:
			if(Character.isDigit(zeichen)) {
				zustandneu = 2;
			} else {
				zustandneu = 5;
			}
			break;
		case 2:
			if(Character.isDigit(zeichen)) {
				zustandneu = 2;
			} else if(zeichen=='.') {
				zustandneu = 3;
			} else {
				zustandneu = 5;
			}
			break;
		case 3:
		case 4:
			if(Character.isDigit(zeichen)) {
				zustandneu = 4;
			} else {
				zustandneu = 5;
			}
			break;
		default:
			zustandneu = 5;
			break;
		}
		
		return zustandneu;
	}

	public static void main(String[] args) {
		new DezZahlEinlesen();
	}
}