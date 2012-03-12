package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.cdm.view.IRenderer;
import com.cdm.view.Position;
import com.cdm.view.Position.RefSystem;
import com.cdm.view.Selector;
import com.cdm.view.elements.Unit.UnitType;
import com.cdm.view.enemy.EnemyPlayer;

public class Level {
	private List<Unit> units = new ArrayList<Unit>();
	private Grid grid;
	private Selector selector = null;
	private EnemyPlayer player;

	public Level(int w, int h) {
		grid = new Grid(w, h);
		player = new EnemyPlayer();
		player.setLevel(this);
		add(new Rocket(new Position(3, 3, RefSystem.Level)));
		add(new SmallShip(new Position(1, 1, RefSystem.Level)));
	}

	public void add(Position pos, UnitType type) {
		units.add(Elements.getElementBy(type, pos));
	}

	public void hover(Position pos) {
		if (pos.screenPos()) {
			pos = pos.toLevelPos().alignToGrid();
		}
		selector = new Selector(pos);
	}

	public void stopHover() {
		selector = null;
	}

	public void move(float time) {
		if(time>0.1f)
			time=0.1f;
		player.addTime(time);
		for (Unit unit : units) {
			unit.move(time);
		}
	}

	public void draw(IRenderer renderer) {
		for (Unit unit : units) {
			unit.draw(renderer);
		}
		if (selector != null)
			selector.draw(renderer);

	}

	public void add(Unit dragElement) {
		Position lpos = dragElement.getPosition().toLevelPos().alignToGrid();
		if (grid.get((int) lpos.x, (int) lpos.y).isEmpty() || dragElement instanceof EnemyUnit) {

			dragElement.setLevel(this);
			dragElement.setPosition(lpos);
			units.add(dragElement);
		} else {
			System.out.println("NOT EMPTY!");
		}
	}

	public void removeMeFromGrid(Position p, Unit unit) {
		int x0 = Math.round(p.x);
		int y0 = Math.round(p.y);

		List<Element> l = grid.get(x0, y0);
		if (l != null)
			l.remove(unit);
		else {
			System.out.println("NOT FOUND" + x0 + " " + y0);
		}
	}

	public void addMeToGrid(Position p, Unit unit) {
		int x0 = Math.round(p.x);
		int y0 = Math.round(p.y);

		List<Element> l = grid.get(x0, y0);
		if (l != null)
			l.add(unit);
	}

	public boolean hasEnemies() {
		for (Unit unit : units) {
			if (unit instanceof EnemyUnit)
				return true;
		}
		return false;
	}

	public Position getEnemyStartPosition() {
		return new Position(0, 3, RefSystem.Level);
	}

}
