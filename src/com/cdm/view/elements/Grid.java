package com.cdm.view.elements;

import java.util.ArrayList;
import java.util.List;

public class Grid {
	private List<Element>[] cells;
	private int w, h;

	@SuppressWarnings("unchecked")
	public Grid(int pw, int ph) {
		w = pw;
		h = ph;
		cells = new List[w * h];
	}

	@SuppressWarnings("unchecked")
	public List<Element> get(int x, int y) {
		List<Element> l = null;
		if (x >= 0 && x < w && y >= 0 && y < h) {
			if ((l = cells[x + y * w]) == null) {
				l = cells[x + y * w] = new ArrayList<Element>();
			}
		}
		return l;
	}
}
