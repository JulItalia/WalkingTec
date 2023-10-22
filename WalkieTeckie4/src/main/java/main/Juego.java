/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

/**
 *
 * @author roder
 */

import javax.swing.JFrame;
import ayudita.CargarGuardar;
import managers.TileManager;
import escenas.Edicion;
import escenas.GameOver;
import escenas.Menu;
import escenas.Jugando;
import escenas.Ajustes;

public class Juego extends JFrame implements Runnable {

	private PantallaJuego gameScreen;
	private Thread gameThread;

	private final double FPS_SET = 120.0;
	private final double UPS_SET = 60.0;

	// Classes
	private Render render;
	private Menu menu;
	private Jugando playing;
	private Ajustes settings;
	private Edicion editing;
	private GameOver gameOver;

	private TileManager tileManager;

	public Juego() {

		CargarGuardar.CreateFolder();

		createDefaultLevel();
		initClasses();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Walking Tec");
		add(gameScreen);
		pack();
		setVisible(true);

	}

	private void createDefaultLevel() {
		int[] arr = new int[400];
		for (int i = 0; i < arr.length; i++)
			arr[i] = 0;

		CargarGuardar.CreateLevel(arr);

	}

	private void initClasses() {
		tileManager = new TileManager();
		render = new Render(this);
		gameScreen = new PantallaJuego(this);
		menu = new Menu(this);
		playing = new Jugando(this);
		settings = new Ajustes(this);
		editing = new Edicion(this);
		gameOver = new GameOver(this);

	}

	private void start() {
		gameThread = new Thread(this) {
		};

		gameThread.start();
	}

	private void updateGame() {
		switch (EstadoJuego.gameState) {
		case EDIT:
			editing.update();
			break;
		case MENU:
			break;
		case PLAYING:
			playing.update();
			break;
		case SETTINGS:
			break;
		default:
			break;
		}
	}

	public static void main(String[] args) {

		Juego game = new Juego();
		game.gameScreen.initInputs();
		game.start();

	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();

		int frames = 0;
		int updates = 0;

		long now;

		while (true) {
			now = System.nanoTime();

			// Render
			if (now - lastFrame >= timePerFrame) {
				repaint();
				lastFrame = now;
				frames++;
			}

			// Update
			if (now - lastUpdate >= timePerUpdate) {
				updateGame();
				lastUpdate = now;
				updates++;
			}


		}

	}

	// Getters and setters
	public Render getRender() {
		return render;
	}

	public Menu getMenu() {
		return menu;
	}

	public Jugando getPlaying() {
		return playing;
	}

	public Ajustes getSettings() {
		return settings;
	}

	public Edicion getEditor() {
		return editing;
	}

	public GameOver getGameOver() {
		return gameOver;
	}

	public TileManager getTileManager() {
		return tileManager;
	}

}