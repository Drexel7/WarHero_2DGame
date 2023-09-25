package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs; // Obiect pentru gestionarea evenimentelor de la mouse
	private Game game; // Referință către obiectul jocului

	public GamePanel(Game game) throws Exception {
		mouseInputs = new MouseInputs(this); // Inițializarea gestionării evenimentelor de la mouse
		this.game = game; // Atribuirea referinței către obiectul jocului
		setPanelSize(); // Setarea dimensiunilor preferate ale panoului
		addKeyListener(new KeyboardInputs(this)); // Adăugarea gestionării evenimentelor de la tastatură
		addMouseListener(mouseInputs); // Adăugarea gestionării evenimentelor de la mouse
		addMouseMotionListener(mouseInputs); // Adăugarea gestionării evenimentelor de mișcare a mouse-ului
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT); // Definirea dimensiunilor preferate ale panoului
		setPreferredSize(size); // Setarea dimensiunilor preferate ale panoului
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Apelarea metodei paintComponent din clasa de bază pentru a pregăti desenarea
		game.render(g); // Desenarea conținutului jocului pe panou
	}

	public Game getGame() {
		return game; // Returnarea referinței către obiectul jocului
	}

	public DataBase DB = new DataBase(); // Instanță a clasei DataBase pentru stocarea datelor de joc

	public DataBase getDB() {
		return DB; // Returnarea instanței clasei DataBase
	}
}
