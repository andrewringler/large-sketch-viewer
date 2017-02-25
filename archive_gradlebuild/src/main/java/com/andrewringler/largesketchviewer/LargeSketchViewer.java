package com.andrewringler.largesketchviewer;

import static java.lang.Math.round;

import processing.core.PApplet;

public class LargeSketchViewer {
	private final PApplet p;
	private ViewerWindowSketch pSmall;
	private int lastUpdateMillis;
	private int desiredMillisBetweenUpdates = round(1000f / 60f);
	
	public LargeSketchViewer(PApplet p, float frameRate) {
		this.p = p;
		// register post with main sketch to capture screen
		p.registerMethod("post", this);
		
		int buffer = 100;
		float smallWindowWidth = p.width;
		float smallWindowHeight = p.height;
		float ratio = (float) p.width / (float) p.height;
		if (smallWindowWidth > p.displayWidth) {
			// sketch is too wide, shrink it horizontally
			smallWindowWidth = p.displayWidth - buffer;
			smallWindowHeight = smallWindowWidth / ratio;
		}
		if (smallWindowHeight > p.displayHeight) {
			// sketch is too tall, shrink it
			smallWindowHeight = p.displayHeight - buffer;
			smallWindowWidth = smallWindowHeight * ratio;
		}
		
		pSmall = new ViewerWindowSketch(p, round(smallWindowWidth), round(smallWindowHeight));
		PApplet.runSketch(new String[] { "--window-color=#000000", ViewerWindowSketch.class.getCanonicalName() }, pSmall);
		
		// register with small window in case user closes it
		pSmall.registerMethod("dispose", this);
		
		if (frameRate > 0) {
			desiredMillisBetweenUpdates = round(1000f / frameRate);
		}
		lastUpdateMillis = p.millis();
	}
	
	public LargeSketchViewer(PApplet p) {
		this(p, 60);
	}
	
	// post – after draw() has exited (not safe to draw)
	public void post() {
		// limit update frequency to desired frame rate
		int millisSinceLastUpdate = p.millis() - lastUpdateMillis;
		if (millisSinceLastUpdate >= desiredMillisBetweenUpdates) {
			pSmall.update(p.getGraphics());
			lastUpdateMillis = p.millis();
		}
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