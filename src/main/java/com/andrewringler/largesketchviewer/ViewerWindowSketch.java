package com.andrewringler.largesketchviewer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class ViewerWindowSketch extends PApplet {
	private final int W;
	private final int H;
	private PImage img;
	
	public ViewerWindowSketch(PApplet parent, int width, int height) {
		parent.registerMethod("dispose", this);
		W = width;
		H = width;
	}
	
	public void settings() {
		size(W, H, "processing.awt.PGraphicsJava2D");
	}
	
	public void setup() {
		img = createImage(W, H, RGB);
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
	
	public void dispose() {
		// TODO this seems to still be spewing errors…
		exit();
	}
}
