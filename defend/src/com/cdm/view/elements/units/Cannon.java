package com.cdm.view.elements.units;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;
import com.cdm.gui.effects.SoundFX.Type;
import com.cdm.view.IRenderer;
import com.cdm.view.PolySprite;
import com.cdm.view.Position;
import com.cdm.view.elements.Element;
import com.cdm.view.elements.MathTools;
import com.cdm.view.elements.shots.SimpleShot;
import com.cdm.view.enemy.EnemyUnit;

public class Cannon extends RotatingUnit implements Element {

	private float coolingSpeed = 0.3f;
	private static final float HOT_PER_SHOT = 0.4f;
	private static final float TOO_HOT = 0.6f;
	private static List<Vector3> lines = null;
	private static List<Vector3> poly = null;
	private float shotFrequency = 0.5f;
	private float hot = 0.0f;

	private enum Mode {
		SHOOTING, COOLING
	};

	private Mode mode = Mode.SHOOTING;
	private float lastShot = 0.0f;
	private float maxDist = 3.0f;
	private double startingRadius = 0.4f;
	private float impact = 1;
	private static PolySprite shadow;

	public Cannon(Position p) {
		super(p);
		if (lines == null) {
			Vector3 a = new Vector3(-0.95f, 0.25f, 0);
			Vector3 b = new Vector3(-0.25f, 0.75f, 0);
			Vector3 c = new Vector3(0.25f, 0.75f, 0);
			Vector3 d = new Vector3(0.85f, 0.25f, 0);
			Vector3 d2 = new Vector3(0.85f, -0.25f, 0);
			Vector3 e = new Vector3(0.25f, -0.75f, 0);
			Vector3 f = new Vector3(-0.25f, -0.75f, 0);
			Vector3 g = new Vector3(-0.95f, -0.25f, 0);
			Vector3 h = new Vector3(-0.5f, -0.25f, 0);
			Vector3 i = new Vector3(-0.5f, 0.25f, 0);

			lines = Arrays.asList(new Vector3[] { a, b, b, c, c, d, d, d2, d2,
					e, e, f, f, g, g, h, h, i, i, a });
			poly = Arrays.asList(new Vector3[] { a, b, c, a, c, d, i, d, h, d,
					h, d2, g, f, d2, f, d2, e });
			if (shadow == null) {
				shadow = new PolySprite();
				shadow.drawClosedPolyWithBorder(new Vector3[] { a, b, c, d, d2,
						e, f, g, h, i }, INNER_PLAYER_COLOR, new Color(0, 0, 0, 0),
						0.4f);
				shadow.init();
			}
		}

	}

	@Override
	public void draw(IRenderer renderer) {
		super.draw(renderer);
		if (true)
			renderer.render(shadow, getPosition(), getSize(), getAngle(),
					GL10.GL_TRIANGLES);
		if (true) {
			INNER_PLAYER_COLOR.b = 0.6f - hot;
			renderer.drawPoly(getPosition(), poly, getAngle(), INNER_PLAYER_COLOR,
					getSize());
			renderer.drawLines(getPosition(), lines, getAngle(), OUTER_PLAYER_COLOR,
					getSize());
			if(getUnitLevel()>1)
				renderer.drawLines(getPosition(), lines, getAngle(), LEVEL2_PLAYER_COLOR,
						getSize()*1.1f);
			if(getUnitLevel()>2)
				renderer.drawLines(getPosition(), lines, getAngle(), LEVEL3_PLAYER_COLOR,
						getSize()*0.8f);

		}
	}

	void shoot(EnemyUnit enemy) {
		if (enemy != null) {

			if (lastShot > shotFrequency && mode.equals(Mode.SHOOTING)) {

				lastShot = 0.0f;
				Position startingPos = new Position(getPosition());
				float angle = getAngle();
				startingPos.x -= Math.cos(angle * MathTools.M_PI / 180.0f)
						* startingRadius;
				startingPos.y -= Math.sin(angle * MathTools.M_PI / 180.0f)
						* startingRadius;

				getLevel()
						.addShot(
								new SimpleShot(startingPos, anticipatePosition(
										startingPos, enemy, SimpleShot.SPEED),
										getLevel(), impact, enemy));
				getLevel().play(Type.SHOT2);
				hot += HOT_PER_SHOT;
				if (hot > TOO_HOT)
					mode = Mode.COOLING;
			}

		}
	}

	@Override
	protected EnemyUnit getEnemy() {
		EnemyUnit u = getLevel().getNextEnemy(getPosition());
		if (u == null)
			return null;
		if (getPosition().distance(u.getPosition()) > maxDist)
			return null;
		return u;
	}

	@Override
	public void move(float time) {
		super.move(time);
		hot -= time * coolingSpeed;
		if (hot < 0) {
			hot = 0;
			mode = Mode.SHOOTING;
		}

		EnemyUnit enemy = getEnemy();
		lastShot += time;
		if (ableToShoot) {
			shoot(enemy);
		}
	}

	protected float getMaxDist() {
		return maxDist;
	}

	@Override
	public int getZLayer() {
		return 0;
	}

	@Override
	protected void setValue(String key, Float value) {

		if ("distance".equals(key))
			maxDist = value;
		else if ("shot".equals(key))
			shotFrequency = value;
		else if ("cooling".equals(key))
			coolingSpeed = value;
		else if ("power".equals(key))
			impact = value;
	}


}
