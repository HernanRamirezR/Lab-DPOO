package uniandes.dpoo.taller4.interfaz;



import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Header extends JPanel 
{
	
	private JComboBox<String> sizeBox;
	private ButtonGroup lvls;
	
	public Header() {
		
		setBackground(new Color(0, 102, 255));


		JLabel sizeLbl = new JLabel("Tamaño:");
		sizeLbl.setForeground(Color.WHITE);
		add(sizeLbl);

		sizeBox = new JComboBox<String>();
		sizeBox.setModel( new DefaultComboBoxModel<String>(new String[] { "5x5", "7x7", "9x9", "11x11", "13x13", "15x15" }));
		sizeBox.setSelectedIndex(0);
		add(sizeBox);

		JLabel diffLbl = new JLabel("Dificultad:");
		diffLbl.setForeground(Color.WHITE);

		lvls = new ButtonGroup();

		add(diffLbl);

		String[] labels = { "Facil", "Medio", "Dificil" };
		JRadioButton[] groupedRadioButtons = new JRadioButton[labels.length];
		for (int i = 0; i < labels.length; i++) 
		{
			groupedRadioButtons[i] = new JRadioButton(labels[i]);
			groupedRadioButtons[i].setActionCommand("" + i);
			groupedRadioButtons[i].setBackground(new Color(0, 102, 255));
			groupedRadioButtons[i].setForeground(Color.WHITE);
			lvls.add(groupedRadioButtons[i]);
			add(groupedRadioButtons[i]);
		}
		groupedRadioButtons[0].setSelected(true);
		

	}

	public int getSizeGrid() {
		return 2 * sizeBox.getSelectedIndex() + 5;
	}

	public int getDifficultyGame() {
		int dif = Integer.parseInt(lvls.getSelection().getActionCommand()) + 1;
		return dif * 10 << dif;
	}

}
