package com.andrewringler.largesketchviewer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class ViewerWindowSketch extends PApplet {
	private final int w;
	private final int h;
	private PImage img;
	
	public ViewerWindowSketch(int w, int h) {
		this.w = w;
		this.h = h;
	}
	
	public void settings() {
		size(w, h, "processing.awt.PGraphicsJava2D");
	}
	
	public void setup() {
		surface.setTitle("Large Sketch Viewer");
		img = createImage(w, h, RGB);
		noLoop();
	}
	
	public void draw() {
		image(img, 0, 0);
	}
	
	public void update(PGraphics g) {
		if (g != null && img != null) {
			img.copy(g, 0, 0, g.width, g.height, 0, 0, img.width, img.height);
			redraw();
		}
	}
}
