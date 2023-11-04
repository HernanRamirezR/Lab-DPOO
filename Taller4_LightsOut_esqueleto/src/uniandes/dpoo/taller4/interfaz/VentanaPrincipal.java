package uniandes.dpoo.taller4.interfaz;



import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.*;


import java.io.File;
import uniandes.dpoo.taller4.modelo.*;


@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame 
{
	
	//Atributos lógicos
	private String usuario;
	private Tablero game; 
	private Top10 top10;
	private static final File DATA = new File("data/top10.csv");

	//Atributos Gráficos
	private JPanel contentPane;
	private Board board;
	private Header sizeDifficulty;
	private Footer stats;
	private Setters menu;
	

	public VentanaPrincipal() {
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 102, 255));
		contentPane.setBorder(new EmptyBorder(5, 0, 5, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	
		//Cargar Datos
		this.top10 = new Top10();
		top10.cargarRecords(DATA);
		
		//Crear Panel superior- dificultad y tamaño
		sizeDifficulty = new Header();
		contentPane.add(sizeDifficulty, BorderLayout.NORTH);
		
		
		JPanel container = new JPanel();
		container.setBackground(new Color(0, 102, 255));
		contentPane.add(container, BorderLayout.CENTER);
		container.setLayout(new BorderLayout(0, 0)); 	
		
		menu = new Setters(this);
		container.add(menu, BorderLayout.EAST);
		
		usuario = getUser();
		stats = new Footer(usuario);
		contentPane.add(stats, BorderLayout.SOUTH);
		
		
		board = new Board(this, 1);
		container.add(board, BorderLayout.WEST);
		
		

        //Configura la ventana
        setTitle("LightsOut");
		setSize(700, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
	
	private String getUser() {
		String name = JOptionPane.showInputDialog(null, "Ingrese su nickname", "Lights Out Nickname",
				JOptionPane.PLAIN_MESSAGE);

		return name == null || name.isBlank() ? "INVITADO" : name.replaceAll("\\s", "");
	}
	
	public boolean isOn(int i, int j) {
		if (board.getSquare() == 1)
			return true;
		return game.darTablero()[i][j];
	}
	
	public void newGame() 
	{
		int cell = sizeDifficulty.getSizeGrid();
		int diff = sizeDifficulty.getDifficultyGame();

		game = new Tablero(cell);
		board.setSquare(cell);

		game.desordenar(diff);
		board.repaint();
	}

	public void restart() {
		game.reiniciar();
		board.repaint();
	}


	public void top10() {
		new Best10(top10.darRegistros().stream().toArray(RegistroTop10[]::new));
	}

	
	public void change() {
		stats.setJugadas(0);
		stats.setPlayer(getUser());
		newGame();
	}
	
	public void play(int[] cells) {
		if (game != null) {
			game.jugar(cells[0], cells[1]);
			stats.setJugadas(game.darJugadas());
			if (game.tableroIluminado()) {
				int puntaje = game.calcularPuntaje();
				String lbl = "GANASTE CON " + puntaje;
				if (top10.esTop10(puntaje)) {
					lbl += "\nY PERTENECES AL TOP";
					//top10.agregarRegistro(playerName, puntaje);
				}
				JOptionPane.showMessageDialog(null, lbl);
			}
		}
	}
	
	
	
	
	public static void main(String[] args) 
	{
		new VentanaPrincipal();

	}

}
