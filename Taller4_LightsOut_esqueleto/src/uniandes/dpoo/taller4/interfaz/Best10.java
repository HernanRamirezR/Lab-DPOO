package uniandes.dpoo.taller4.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

import uniandes.dpoo.taller4.modelo.RegistroTop10;

@SuppressWarnings("serial")
public class Best10 extends JDialog 
{
	public Best10(RegistroTop10[] registroTop10s) {
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JList<RegistroTop10> list = new JList<RegistroTop10>();
		list.setBackground(Color.LIGHT_GRAY);
		list.setListData(registroTop10s);
		list.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof RegistroTop10) {
					RegistroTop10 nextTop = (RegistroTop10) value;
					for (int i = 0; i < registroTop10s.length; i++) {
						if (registroTop10s[i].equals(nextTop)) {
							setText(String.format("%3d) %5s ==> %04d", i + 1, nextTop.darNombre(), nextTop.darPuntos()));
							switch (i+1) {
							case 1:
								setForeground(new Color(255, 215, 0));
								setBackground(Color.BLACK);
								break;
							case 2:
								setForeground(new Color(192, 192, 192));
								setBackground(Color.BLACK);
								break;
							case 3:
								setForeground(new Color(176, 141, 87));
								setBackground(Color.BLACK);
								break;
							case 4:
							case 5:
								setForeground(Color.WHITE);
								setBackground(Color.DARK_GRAY);
								break;
							default:
								setForeground(Color.BLACK);
								setBackground(Color.GRAY);
							}
						}
					}

				}
				return c;
			}

		});
		scrollPane.setViewportView(list);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
