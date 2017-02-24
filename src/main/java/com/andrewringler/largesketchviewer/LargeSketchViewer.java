package com.andrewringler.largesketchviewer;

import processing.core.PApplet;

public class LargeSketchViewer {
	private final PApplet p;
	private ViewerWindowSketch pSmall;
	
	public LargeSketchViewer(PApplet p) {
		this.p = p;
		p.registerMethod("draw", this);
		pSmall = new ViewerWindowSketch(p, 200, 200);
		PApplet.runSketch(new String[] { "--bgcolor=#000000", ViewerWindowSketch.class.getCanonicalName() }, pSmall);
	}
	
	//	post – after draw() has exited (not safe to draw)
	public void draw() {
		pSmall.update(p.getGraphics());
	}
}