package com.andrewringler.largesketchviewer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class ViewerWindowSketch extends PApplet {
	private final int W;
	private final int H;
	private PImage img1;
	private PImage img2;
	boolean drawing1 = true;
	
	public ViewerWindowSketch(PApplet parent, int width, int height) {
		parent.registerMethod("dispose", this);
		W = width;
		H = width;
	}
	
	public void settings() {
		size(W, H, "processing.awt.PGraphicsJava2D");
	}
	
	public void setup() {
		img1 = createImage(W, H, RGB);
		img2 = createImage(W, H, RGB);
	}
	
	public void draw() {
		if (drawing1) {
			image(img1, 0, 0);
		} else {
			image(img2, 0, 0);
		}
	}
	
	public void update(PGraphics g) {
		if (g != null && img1 != null && img2 != null) {
			/*
			 * write into the image buffer that we are not
			 * currently reading from in our draw loop
			 */
			if (drawing1) {
				img2.copy(g, 0, 0, g.width, g.height, 0, 0, img2.width, img2.height);
			} else {
				img1.copy(g, 0, 0, g.width, g.height, 0, 0, img1.width, img1.height);
			}
			/*
			 * notify our draw loop it should now read from
			 * the other image buffer
			 */
			drawing1 = !drawing1;
		}
	}
	
	public void dispose() {
		// TODO this seems to still be spewing errors…
		exit();
	}
}
