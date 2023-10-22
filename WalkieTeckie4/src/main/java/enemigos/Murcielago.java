/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enemigos;

/**
 *
 * @author roder
 */
import static ayudita.Constantes.Enemies.BAT;

import managers.EnemigoManager;

public class Murcielago extends Enemigo {

	public Murcielago(float x, float y, int ID, EnemigoManager em) {
		super(x, y, ID, BAT,em);
		
	}

}
