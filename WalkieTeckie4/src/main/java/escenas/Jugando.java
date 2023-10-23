/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escenas;

/**
 *
 * @author roder
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enemigos.Enemigo;
import ayudita.CargarGuardar;
import main.Juego;
import managers.EnemigoManager;
import managers.ProyectilManager;
import managers.TorreManager;
import managers.HoleadaManager;
import objetos.PathPoint;
import objetos.Torre;
import ui.BarraAccion;
import static ayudita.Constantes.Tiles.GRASS_TILE;

public class Jugando extends EscenaJuego implements MetodosEscena {

	private int[][] lvl;

	private BarraAccion actionBar;
	private int mouseX, mouseY;
	private EnemigoManager enemyManager;
	private TorreManager towerManager;
	private ProyectilManager projManager;
	private HoleadaManager waveManager;
	private PathPoint start, end;
	private Torre selectedTower;
	private int goldTick;
	private boolean gamePaused;

	public Jugando(Juego game) {
		super(game);
		loadDefaultLevel();

		actionBar = new BarraAccion(0, 640, 640, 160, this);
		enemyManager = new EnemigoManager(this, start, end);
		towerManager = new TorreManager(this);
		projManager = new ProyectilManager(this);
		waveManager = new HoleadaManager(this);
	}

	private void loadDefaultLevel() {
		lvl = CargarGuardar.GetLevelData();
		ArrayList<PathPoint> points = CargarGuardar.GetLevelPathPoints();
		start = points.get(0);
		end = points.get(1);
	}

	public void setLevel(int[][] lvl) {
		this.lvl = lvl;
	}

	public void update() {
		if (!gamePaused) {
			updateTick();
			waveManager.update();

			
			goldTick++;
			if (goldTick % (60 * 3) == 0)
				actionBar.addGold(1);

			if (isAllEnemiesDead()) {
				if (isThereMoreWaves()) {
					waveManager.startWaveTimer();
					if (isWaveTimerOver()) {
						waveManager.increaseWaveIndex();
						enemyManager.getEnemies().clear();
						waveManager.resetEnemyIndex();

					}
				}
			}

			if (isTimeForNewEnemy()) {
				if (!waveManager.isWaveTimerOver())
					spawnEnemy();
			}

			enemyManager.update();
			towerManager.update();
			projManager.update();
		}

	}

	private boolean isWaveTimerOver() {

		return waveManager.isWaveTimerOver();
	}

	private boolean isThereMoreWaves() {
		return waveManager.isThereMoreWaves();

	}

	private boolean isAllEnemiesDead() {

		if (waveManager.isThereMoreEnemiesInWave())
			return false;

		for (Enemigo e : enemyManager.getEnemies())
			if (e.isAlive())
				return false;

		return true;
	}

	private void spawnEnemy() {
		enemyManager.spawnEnemy(waveManager.getNextEnemy());
	}

	private boolean isTimeForNewEnemy() {
		if (waveManager.isTimeForNewEnemy()) {
			if (waveManager.isThereMoreEnemiesInWave())
				return true;
		}

		return false;
	}

	public void setSelectedTower(Torre selectedTower) {
		this.selectedTower = selectedTower;
	}

	@Override
	public void render(Graphics g) {

		drawLevel(g);
		actionBar.draw(g);
		enemyManager.draw(g);
		towerManager.draw(g);
		projManager.draw(g);

		drawSelectedTower(g);
		drawHighlight(g);

	}

	private void drawHighlight(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(mouseX, mouseY, 32, 32);

	}

	private void drawSelectedTower(Graphics g) {
		if (selectedTower != null)
			g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX, mouseY, null);
	}

	private void drawLevel(Graphics g) {

		for (int y = 0; y < lvl.length; y++) {
			for (int x = 0; x < lvl[y].length; x++) {
				int id = lvl[y][x];
				if (isAnimation(id)) {
					g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
				} else
					g.drawImage(getSprite(id), x * 32, y * 32, null);
			}
		}
	}

	public int getTileType(int x, int y) {
		int xCord = x / 32;
		int yCord = y / 32;

		if (xCord < 0 || xCord > 19)
			return 0;
		if (yCord < 0 || yCord > 19)
			return 0;

		int id = lvl[y / 31][x / 32];
		return game.getTileManager().getTile(id).getTileType();
	}

	@Override
	public void mouseClicked(int x, int y) {
		
		if (y >= 640)
			actionBar.mouseClicked(x, y);
		else {
			
			if (selectedTower != null) {
				
				if (isTileGrass(mouseX, mouseY)) {
					if (getTowerAt(mouseX, mouseY) == null) {
						towerManager.addTower(selectedTower, mouseX, mouseY);

						removeGold(selectedTower.getTowerType());

						selectedTower = null;

					}
				}
			} else {
				
				Torre t = getTowerAt(mouseX, mouseY);
				actionBar.displayTower(t);
			}
		}
	}

	private void removeGold(int towerType) {
		actionBar.payForTower(towerType);

	}

	public void upgradeTower(Torre displayedTower) {
		towerManager.upgradeTower(displayedTower);

	}

	public void removeTower(Torre displayedTower) {
		towerManager.removeTower(displayedTower);
	}

	private Torre getTowerAt(int x, int y) {
		return towerManager.getTowerAt(x, y);
	}

	private boolean isTileGrass(int x, int y) {
		int id = lvl[y / 32][x / 32];
		int tileType = game.getTileManager().getTile(id).getTileType();
		return tileType == GRASS_TILE;
	}

	public void shootEnemy(Torre t, Enemigo e) {
		projManager.newProjectile(t, e);

	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			selectedTower = null;
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (y >= 640)
			actionBar.mouseMoved(x, y);
		else {
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y >= 640)
			actionBar.mousePressed(x, y);

	}

	@Override
	public void mouseReleased(int x, int y) {
		actionBar.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {

	}

	public void rewardPlayer(int enemyType) {
		actionBar.addGold(ayudita.Constantes.Enemies.GetReward(enemyType));
	}

	public TorreManager getTowerManager() {
		return towerManager;
	}

	public EnemigoManager getEnemyManger() {
		return enemyManager;
	}

	public HoleadaManager getWaveManager() {
		return waveManager;
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public void removeOneLife() {
		actionBar.removeOneLife();
	}

	public void resetEverything() {

		actionBar.resetEverything();

		
		enemyManager.reset();
		towerManager.reset();
		projManager.reset();
		waveManager.reset();

		mouseX = 0;
		mouseY = 0;

		selectedTower = null;
		goldTick = 0;
		gamePaused = false;

	}

}
