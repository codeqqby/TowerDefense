package com.cdm.view.enemy;

public enum EnemyType {
	SMALL_SHIP, TANK, BIG_SHIP, BIG_SHIP2, ROCKET;

	public static final Float STRENGTH_THRESHOLD = 1.1f;

	public static EnemyType random() {
		double r = Math.random();
		if (r < 0.4)
			return ROCKET;
		else if (r < 0.5)
			return TANK;
		else if (r < 0.75)
			return BIG_SHIP;
		else
			return SMALL_SHIP;
	}

	public float getStrength() {
		switch (this) {
		case SMALL_SHIP:
			return 1.0f;
		case TANK:
			return 1.5f;
		case BIG_SHIP:
			return 2.5f;
		case ROCKET:
			return 3.5f;
		}
		return 0.0f;
	}
}
