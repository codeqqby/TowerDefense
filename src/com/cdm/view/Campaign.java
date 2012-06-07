package com.cdm.view;

import com.cdm.view.elements.Grid;
import com.cdm.view.elements.Level;
import com.cdm.view.elements.Grid.CellType;

public class Campaign {
	private int levelNo = 0;

	public Level getNextLevel(LevelScreen screen) {

		levelNo += 1;
		Level level = null;
		if (false) {
			if (levelNo == 1)
				level = new Level(20, 10, 5, screen);
			else
				level = new Level(50, 5, 2, screen);
		} else {
			int w = 20, h = 5, endY = 2;
			Grid grid = CampaignParser.getGrid(2);
			/*
			Grid grid = new Grid(w, h);
			grid.setEndy(endY);

			grid.getElement(2, 4).setCellType(CellType.BLOCK);
			grid.getElement(3, 4).setCellType(CellType.EMPTY);
*/
			level = new Level(grid, screen);
		}
		return level;
	}

	public void restart() {
		levelNo = 0;
	}

	public int getLevelNo() {
		return levelNo;
	}
}
