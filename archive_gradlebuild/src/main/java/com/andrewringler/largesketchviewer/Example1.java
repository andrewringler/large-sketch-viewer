package com.andrewringler.largesketchviewer;

import processing.core.PApplet;

public class Example1 extends PApplet {
	float rectSize = 10;
	boolean rectGrowing = true;
	
	public Example1() {
		// DON'T CALL ANY PROCESSING CODE (PApplet) FROM HERE.		
	}
	
	public void settings() {
		//		size(1920, 1080, "processing.opengl.PGraphics3D");
		size(1920, 1080, "processing.awt.PGraphicsJava2D");
	}
	
	public void setup() {
		frameRate(60);
		background(0);
		new LargeSketchViewer(this, 15); // slow frame rate for performance
		//		new LargeSketchViewer(this);
	}
	
	public void draw() {
		background(0);
		
		int offset = 20;
		int size = 50;
		ellipseMode(CORNER);
		ellipse(offset, offset, size, size);
		ellipse(offset, height - offset - size, size, size);
		ellipse(width - offset - size, offset, size, size);
		ellipse(width - offset - size, height - offset - size, size, size);
		
		rectMode(CENTER);
		rect(width / 2f, height / 2f, rectSize, rectSize);
		rectSize += rectGrowing ? 4 : -4;
		if (rectSize < 10) {
			rectGrowing = true;
		}
		if (rectSize > height * 0.6) {
			rectGrowing = false;
		}
	}
	
	public static void main(String args[]) {
		// https://processing.org/tutorials/eclipse/
		PApplet.main(new String[] { "--window-color=#000000", Example1.class.getCanonicalName() });
	}
}