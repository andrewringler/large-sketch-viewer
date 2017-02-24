package com.andrewringler.largesketchviewer;

import processing.core.PApplet;

public class LargeSketchViewer {
	private final PApplet p;
	private ViewerWindowSketch pSmall;
	
	public LargeSketchViewer(PApplet p) {
		this.p = p;
		p.registerMethod("draw", this);
		pSmall = new ViewerWindowSketch(200, 200);
		PApplet.runSketch(new String[] { "--bgcolor=#000000", ViewerWindowSketch.class.getCanonicalName() }, pSmall);
		
		// register with small window in case user closes it
		pSmall.registerMethod("dispose", this);
	}
	
	//	post – after draw() has exited (not safe to draw)
	public void draw() {
		pSmall.update(p.getGraphics());
	}
	
	/*
	 * small window is shutting down, we need to
	 * manually stop the main sketch now too
	 */
	public void dispose() {
		p.noLoop(); // prevents 3D thread errors during shutdown
		p.exit();
	}
}