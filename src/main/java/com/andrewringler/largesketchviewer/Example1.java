package com.andrewringler.largesketchviewer;

import processing.core.PApplet;

public class Example1 extends PApplet {
	float rectSize = 10;
	boolean rectGrowing = true;
	
	public Example1() {
		// DON'T CALL ANY PROCESSING CODE (PApplet) FROM HERE.		
	}
	
	public void settings() {
		size(1920, 1080, "processing.opengl.PGraphics3D");
	}
	
	public void setup() {
		background(0);
		new LargeSketchViewer(this);
	}
	
	public void draw() {
		int offset = 20;
		int size = 50;
		ellipseMode(CORNER);
		ellipse(offset, offset, size, size);
		ellipse(offset, height - offset - size, size, size);
		ellipse(width - offset - size, offset, size, size);
		ellipse(width - offset - size, height - offset - size, size, size);
		
		rectMode(CENTER);
		rect(width / 2f, height / 2f, rectSize, rectSize);
		rectSize += rectGrowing ? 0.8 : -0.8;
		if (rectSize < 10) {
			rectGrowing = true;
		}
		if (rectSize > width * 0.6) {
			rectGrowing = false;
		}
	}
	
	public static void main(String args[]) {
		// https://processing.org/tutorials/eclipse/
		PApplet.main(new String[] { "--window-color=#000000", Example1.class.getCanonicalName() });
	}
}
