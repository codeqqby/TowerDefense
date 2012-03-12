package com.cdm.view.elements;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;

public abstract class Unit implements Element {

	public enum UnitType {
		CANNON, ROCKET_THROWER, STUNNER, PHAZER
	};

	private Position pos, oldpos = new Position(1, 1, RefSystem.Level);
	private float size;
	private Level level;

	public Unit(Position p) {
		pos = p;
		size = 0.75f;
		level = null;
	}

	public abstract void move(float time);

	public abstract void draw(IRenderer renderer);

	@Override
	public void setPosition(Position p) {
		boolean modified = (!oldpos.alignToGrid().equals(p.alignToGrid()));
		if (modified && level != null) {
			level.removeMeFromGrid(oldpos, this);
		}
		pos = p;
		if (modified && level != null) {
			level.addMeToGrid(p, this);
		}
		oldpos.assignFrom(pos);

	}

	public Position getPosition() {
		return pos;
	}

	public void setSize(float f) {
		size = f;
	}

	public void setLevel(Level level) {
		this.level = level;
		level.addMeToGrid(pos, this);
	}

	public float getSize() {
		return size;
	}

}
