package com.cdm.view.elements;

import com.cdm.gui.effects.SoundFX;
import com.cdm.gui.effects.SoundFX.Type;
import com.cdm.view.enemy.EnemyUnit;

public class PlayerState {

	private int health = 3;
	private int money = 10;
	private int points = 0;
	private int bonus = 0;

	public int getHealth() {
		if (health > 0)
			return health;
		return 0;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public void reset() {
		setHealth(3);
		setMoney(10);
		setBonus(0);
		setPoints(0);
	}

	public void enemyDestroyed(EnemyUnit enemyUnit) {
		money += enemyUnit.getMoney();
		points += enemyUnit.getPoints();
		bonus += enemyUnit.getBonus();
		if (bonus == 50) {
			health += 1;
			SoundFX.play(Type.WIN);

		}
		if (bonus == 75) {
			health += 1;
			SoundFX.play(Type.WIN);
		}
		if (bonus == 100) {
			health += 1;
			SoundFX.play(Type.WIN);
		}
		if (bonus == 125) {
			health += 1;
			SoundFX.play(Type.WIN);
		}
		if (bonus == 150) {
			health += 1;
			SoundFX.play(Type.WIN);
		}
		if (bonus == 175) {
			health += 1;
			SoundFX.play(Type.WIN);
		}
		if (bonus == 200) {
			health += 1;
			SoundFX.play(Type.WIN);
		}

	}

	public void hurt() {
		SoundFX.play(Type.HURT);
		health -= 1;
		if (health < 1)
			SoundFX.play(Type.LOOSE);

	}

	public boolean gameover() {
		return health < 1;
	}
}
