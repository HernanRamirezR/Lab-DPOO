package uniandes.dpoo.taller4.interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Setters extends JPanel implements ActionListener {
	
	private static final String[] KEYS = { "NUEVO", "REINICIAR", "TOP 10", "CAMBIAR JUGADOR" };
	private VentanaPrincipal parent;

	public Setters(VentanaPrincipal game) {
		this.parent = game;
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		 
		JButton[] menuBtn = new JButton[4];

		for (int i = 0; i < 4; i++) {
			add(Box.createRigidArea(new Dimension(0,50)));
			menuBtn[i] = new JButton(KEYS[i]);
			menuBtn[i].setForeground(new Color(0, 102, 255));
			menuBtn[i].setBackground(Color.WHITE);
			menuBtn[i].addActionListener(this);
			menuBtn[i].setAlignmentX(CENTER_ALIGNMENT);
			
			
			add(menuBtn[i]);
			
		}
		setBackground(new Color(0, 102, 255));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String lbl = e.getActionCommand();
		if (KEYS[0].equals(lbl)) {
			parent.newGame();
		} else if (KEYS[1].equals(lbl)) {
			parent.restart();
		} else if (KEYS[2].equals(lbl)) {
			parent.top10();
		} else if (KEYS[3].equals(lbl)) {
			parent.change();
		}
	}

}
