/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

/**
 *
 * @author roder
 */
import java.util.ArrayList;
import java.util.Arrays;

import eventos.Oleada;
import escenas.Jugando;

public class HoleadaManager {

	private Jugando playing;
	private ArrayList<Oleada> waves = new ArrayList<>();
	private int enemySpawnTickLimit = 60 * 1;
	private int enemySpawnTick = enemySpawnTickLimit;
	private int enemyIndex, waveIndex;
	private int waveTickLimit = 60 * 5;
	private int waveTick = 0;
	private boolean waveStartTimer, waveTickTimerOver;

	public HoleadaManager(Jugando playing) {
		this.playing = playing;
		createWaves();
	}

	public void update() {
		if (enemySpawnTick < enemySpawnTickLimit)
			enemySpawnTick++;

		if (waveStartTimer) {
			waveTick++;
			if (waveTick >= waveTickLimit) {
				waveTickTimerOver = true;
			}
		}

	}

	public void increaseWaveIndex() {
		waveIndex++;
		waveTick = 0;
		waveTickTimerOver = false;
		waveStartTimer = false;
	}

	public boolean isWaveTimerOver() {

		return waveTickTimerOver;
	}

	public void startWaveTimer() {
		waveStartTimer = true;
	}

	public int getNextEnemy() {
		enemySpawnTick = 0;
		return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
	}

	private void createWaves() {
            waves.add(new Oleada(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0))));
            waves.add(new Oleada(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0))));
            waves.add(new Oleada(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 1))));
            waves.add(new Oleada(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 1, 1))));
            waves.add(new Oleada(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 1, 1, 1, 1, 3, 3 ,3))));
            waves.add(new Oleada(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 3 ,3 ,3 ,3 ,3))));
            waves.add(new Oleada(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0,3, 1, 1, 1, 1, 1, 1, 3 ,3 ,3))));
            waves.add(new Oleada(new ArrayList<Integer>(Arrays.asList(0, 0,3, 0, 0, 0,3, 0, 0, 0, 0, 0,3, 1, 1, 1, 1, 1))));
            waves.add(new Oleada(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 1, 1, 1, 1,3, 3, 2))));
            waves.add(new Oleada(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 1, 1, 1, 1, 2, 3, 3 ,3 ,2))));
            waves.add(new Oleada(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2, 2, 2,2 ,2))));

            
    
             // Última oleada
    ArrayList<Integer> ultimaOleada = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2, 2, 2, 2, 2));

    // Crear nuevas oleadas con un enemigo adicional de cada tipo
    for (int i = 0; i < ultimaOleada.size(); i++) {
        ArrayList<Integer> nuevaOleada = new ArrayList<>(ultimaOleada);
        nuevaOleada.set(i, nuevaOleada.get(i) + 1); // Añadir un enemigo adicional de este tipo
        waves.add(new Oleada(nuevaOleada));
    }
        }


	public ArrayList<Oleada> getWaves() {
		return waves;
	}

	public boolean isTimeForNewEnemy() {
		return enemySpawnTick >= enemySpawnTickLimit;
	}

	public boolean isThereMoreEnemiesInWave() {
		return enemyIndex < waves.get(waveIndex).getEnemyList().size();
	}

	public boolean isThereMoreWaves() {
		return waveIndex + 1 < waves.size();
	}

	public void resetEnemyIndex() {
		enemyIndex = 0;
	}

	public int getWaveIndex() {
		return waveIndex;
	}

	public float getTimeLeft() {
		float ticksLeft = waveTickLimit - waveTick;
		return ticksLeft / 60.0f;
	}

	public boolean isWaveTimerStarted() {
		return waveStartTimer;
	}

	public void reset() {
		waves.clear();
		createWaves();
		enemyIndex = 0;
		waveIndex = 0;
		waveStartTimer = false;
		waveTickTimerOver = false;
		waveTick = 0;
		enemySpawnTick = enemySpawnTickLimit;
	}

}
