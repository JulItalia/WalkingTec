/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entradas;

/**
 *
 * @author roder
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static main.EstadoJuego.*;

import main.Juego;
import main.EstadoJuego;

public class KeyboardListener implements KeyListener {
	private Juego game;

	public KeyboardListener(Juego game) {
		this.game = game;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (EstadoJuego.gameState == EDIT)
			game.getEditor().keyPressed(e);
		else if (EstadoJuego.gameState == PLAYING)
			game.getPlaying().keyPressed(e);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
