/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enemigos;

/**
 *
 * @author roder
 */
import static ayudita.Constantes.Enemies.WOLF;

import managers.EnemigoManager;

public class Lobo extends Enemigo {

	public Lobo(float x, float y, int ID, EnemigoManager em) {
		super(x, y, ID, WOLF, em);
	}

}
