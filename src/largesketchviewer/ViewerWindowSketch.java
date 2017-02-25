package largesketchviewer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class ViewerWindowSketch extends PApplet {
	private static final String PREVIEW_WINDOW_TITLE = "Preview Window";
	private static final int MARGIN = 40;
	private static final int TEXT_OFFSET = 10;
	private static final int TEXT_BUFFER = 55;
	private static final int END_SIZE = 5;
	
	private final PApplet theParent;
	private final int w;
	private final int h;
	private final int textSize = 16;
	private PImage img;
	
	public ViewerWindowSketch(PApplet theParent, int w, int h) {
		this.theParent = theParent;
		this.w = w;
		this.h = h;
	}
	
	public void settings() {
		size(w, h, "processing.awt.PGraphicsJava2D");
		//		size(w, h, "processing.javafx.PGraphicsFX2D");
		//		size(w, h, "processing.opengl.PGraphics2D");
	}
	
	public void setup() {
		surface.setTitle(PREVIEW_WINDOW_TITLE);
		img = createImage(w - (MARGIN * 2), h - (MARGIN * 2), RGB);
		noLoop();
		
		background(255);
		
		// Draw axis labels, original pixel dimensions of sketch
		// for reference
		fill(0);
		stroke(0);
		strokeWeight(1);
		textSize(textSize);
		
		// realized framerate
		textAlign(LEFT, TOP);
		text("fps: " + theParent.frameRate, MARGIN, TEXT_OFFSET);
		
		textAlign(CENTER, BOTTOM);
		text(theParent.width + "px", width / 2f, height - TEXT_OFFSET);
		float lineY = height - TEXT_OFFSET - (textSize / 2f);
		line(MARGIN, lineY, width / 2f - TEXT_BUFFER, lineY);
		line(width - MARGIN, lineY, width / 2f + TEXT_BUFFER, lineY);
		line(MARGIN, lineY - END_SIZE, MARGIN, lineY + END_SIZE);
		line(width - MARGIN, lineY - END_SIZE, width - MARGIN, lineY + END_SIZE);
		
		String heightText = theParent.height + "px";
		pushMatrix();
		textAlign(CENTER, CENTER);
		translate(TEXT_OFFSET + textSize / 2f, height / 2f);
		rotate(-HALF_PI);
		text(heightText, 0, 0);
		popMatrix();
		float lineX = TEXT_OFFSET + textSize / 2f;
		line(lineX, MARGIN, lineX, height / 2f - TEXT_BUFFER);
		line(lineX, height - MARGIN, lineX, height / 2f + TEXT_BUFFER);
		line(lineX - END_SIZE, MARGIN, lineX + END_SIZE, MARGIN);
		line(lineX - END_SIZE, height - MARGIN, lineX + END_SIZE, height - MARGIN);
	}
	
	public void draw() {
		image(img, MARGIN, MARGIN);
	}
	
	public void update(PGraphics g) {
		if (g != null && img != null) {
			img.copy(g, 0, 0, g.width, g.height, 0, 0, img.width, img.height);
			redraw();
		}
	}
}
