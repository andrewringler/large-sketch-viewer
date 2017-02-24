package com.andrewringler.largesketchviewer;

import processing.core.PApplet;

public class Example1 extends PApplet {
	public Example1() {
		// DON'T CALL ANY PROCESSING CODE (PApplet) FROM HERE.		
	}
	
	public void settings() {
		size(600, 600, "processing.opengl.PGraphics3D");
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
	}
	
	public static void main(String args[]) {
		// https://processing.org/tutorials/eclipse/
		PApplet.main(new String[] { "--bgcolor=#000000", Example1.class.getCanonicalName() });
	}
}
