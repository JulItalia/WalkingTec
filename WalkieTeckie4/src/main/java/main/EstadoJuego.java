/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author roder
 */
public enum EstadoJuego {

	PLAYING, MENU, SETTINGS, EDIT, GAME_OVER;

	public static EstadoJuego gameState = MENU;

	public static void SetGameState(EstadoJuego state) {
		gameState = state;
	}

}

