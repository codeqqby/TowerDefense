package com.cdm.view.enemy;

public enum EnemyType {
	SMALL_SHIP, TANK, BIG_SHIP, BIG_SHIP2, ROCKET, TRUCK, TANK2, ROTOR, BUG;

	public static final Float STRENGTH_THRESHOLD = 1.1f;
	public static final Float FACTOR = 0.4f;

	public static EnemyType random() {
		double r = Math.random();

		return EnemyType.values()[((int) (r * 500)) % EnemyType.values().length];
		
	}

	public float getEnergy(int levelNo) {
		return getStrength(levelNo);
	}

	public float getStrength(int waveNo) {
		float s = 1.0f;
		switch (this) {
		case SMALL_SHIP:
			s = 1.0f;
			break;
		case BUG:
		case TANK:
			s = 1.5f;
			break;
		case BIG_SHIP:
			s = 2.5f;
			break;
		case ROCKET:
			s = 13.5f;
			break;
		case TRUCK:
			s = 10.5f;
			break;
		case TANK2:
			s = 10.0f;
			break;
		case ROTOR:
			s = 12.5f;
			break;
		}
		return s * (1.0f + (waveNo - 1) * FACTOR);
	}
}
