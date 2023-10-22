/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

/**
 *
 * @author roder
 */
import static ayudita.Constantes.Towers.*;

public class Torre {

	private int x, y, id, towerType, cdTick, dmg;
	private float range, cooldown;
	private int tier;

	public Torre(int x, int y, int id, int towerType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.towerType = towerType;
		tier = 1;
		setDefaultDmg();
		setDefaultRange();
		setDefaultCooldown();
	}

	public void update() {
		cdTick++;
	}

	public void upgradeTower() {
		this.tier++;

		switch (towerType) {
		case ARCHER:
			dmg += 2;
			range += 20;
			cooldown -= 5;
			break;
		case CANNON:
			dmg += 5;
			range += 20;
			cooldown -= 15;
			break;
		case WIZARD:
			range += 20;
			cooldown -= 10;
			break;
		}
	}

	public boolean isCooldownOver() {

		return cdTick >= cooldown;
	}

	public void resetCooldown() {
		cdTick = 0;
	}

	private void setDefaultCooldown() {
		cooldown = ayudita.Constantes.Towers.GetDefaultCooldown(towerType);

	}

	private void setDefaultRange() {
		range = ayudita.Constantes.Towers.GetDefaultRange(towerType);

	}

	private void setDefaultDmg() {
		dmg = ayudita.Constantes.Towers.GetStartDmg(towerType);

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTowerType() {
		return towerType;
	}

	public void setTowerType(int towerType) {
		this.towerType = towerType;
	}

	public int getDmg() {
		return dmg;
	}

	public float getRange() {
		return range;
	}

	public float getCooldown() {
		return cooldown;
	}

	public int getTier() {
		return tier;
	}

}
