/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

/**
 *
 * @author roder
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemigos.Enemigo;
import ayudita.CargarGuardar;
import objetos.Torre;
import escenas.Jugando;

public class TorreManager {

	private Jugando playing;
	private BufferedImage[] towerImgs;
	private ArrayList<Torre> towers = new ArrayList<>();
	private int towerAmount = 0;

	public TorreManager(Jugando playing) {
		this.playing = playing;
		loadTowerImgs();
	}

	private void loadTowerImgs() {
		BufferedImage atlas = CargarGuardar.getSpriteAtlas();
		towerImgs = new BufferedImage[3];
		for (int i = 0; i < 3; i++)
			towerImgs[i] = atlas.getSubimage((4 + i) * 32, 32, 32, 32);
	}

	public void addTower(Torre selectedTower, int xPos, int yPos) {
		towers.add(new Torre(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
	}

	public void removeTower(Torre displayedTower) {
		for (int i = 0; i < towers.size(); i++)
			if (towers.get(i).getId() == displayedTower.getId())
				towers.remove(i);
	}

	public void upgradeTower(Torre displayedTower) {
		for (Torre t : towers)
			if (t.getId() == displayedTower.getId())
				t.upgradeTower();
	}

	public void update() {
		for (Torre t : towers) {
			t.update();
			attackEnemyIfClose(t);
		}
	}

	private void attackEnemyIfClose(Torre t) {
		for (Enemigo e : playing.getEnemyManger().getEnemies()) {
			if (e.isAlive())
				if (isEnemyInRange(t, e)) {
					if (t.isCooldownOver()) {
						playing.shootEnemy(t, e);
						t.resetCooldown();
					}
				} else {
					// we do nothing
				}
		}

	}

	private boolean isEnemyInRange(Torre t, Enemigo e) {
		int range = ayudita.Utilidades.GetHypoDistance(t.getX(), t.getY(), e.getX(), e.getY());
		return range < t.getRange();
	}

	public void draw(Graphics g) {
		for (Torre t : towers)
			g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(), null);
	}

	public Torre getTowerAt(int x, int y) {
		for (Torre t : towers)
			if (t.getX() == x)
				if (t.getY() == y)
					return t;
		return null;
	}

	public BufferedImage[] getTowerImgs() {
		return towerImgs;
	}

	public void reset() {
		towers.clear();
		towerAmount = 0;
	}

}
