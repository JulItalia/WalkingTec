/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escenas;

/**
 *
 * @author roder
 */
import java.awt.Graphics;

public interface MetodosEscena {

	public void render(Graphics g);

	public void mouseClicked(int x, int y);

	public void mouseMoved(int x, int y);

	public void mousePressed(int x, int y);

	public void mouseReleased(int x, int y);

	public void mouseDragged(int x, int y);

}

