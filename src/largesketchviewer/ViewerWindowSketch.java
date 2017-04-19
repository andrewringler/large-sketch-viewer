package largesketchviewer;

import processing.core.PApplet;
import processing.core.PFont;
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
	private final boolean rotate90deg;
	private final boolean flipLeftToRight;
	private final int imageWidth;
	private final int imageHeight;
	
	private PImage img;
	
	private PFont openSansSemiBold16;
	
	public ViewerWindowSketch(PApplet theParent, int canvasWidth, int canvasHeight, int imageWidth, int imageHeight, boolean rotate90deg, boolean flipLeftToRight) {
		this.theParent = theParent;
		this.w = canvasWidth;
		this.h = canvasHeight;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.rotate90deg = rotate90deg;
		this.flipLeftToRight = flipLeftToRight;
		
	}
	
	public void settings() {
		/*
		 * PGraphicsJava2D, seems stable,
		 * OpenGL renderers seem to cause various issues
		 * perhaps classloader conflicts?
		 * perhaps the two OpenGL renderers cannot run at the same time?
		 */
		size(w, h, "processing.awt.PGraphicsJava2D");
	}
	
	public void setup() {
		surface.setTitle(PREVIEW_WINDOW_TITLE);
		
		/*
		 * NOTE: we load our own fonts because sometimes
		 * loading two instances of PApplet causes Processing to not find
		 * the default fonts
		 */
		openSansSemiBold16 = loadFont("OpenSans-Semibold-16.vlw");
		
		img = createImage(imageWidth - (MARGIN * 2), imageHeight - (MARGIN * 2), RGB);
		noLoop();
		
		background(255);
		
		// Draw axis labels, original pixel dimensions of sketch
		// for reference
		fill(0);
		stroke(0);
		strokeWeight(1);
		textFont(openSansSemiBold16);
		
		// realized framerate
		textAlign(LEFT, TOP);
		text("fps: " + theParent.frameRate, MARGIN, TEXT_OFFSET);
		
		String widthText = theParent.width + "px";
		String heightText = theParent.height + "px";
		if (rotate90deg) {
			widthText = theParent.height + "px";
			heightText = theParent.width + "px";
		}
		
		textAlign(CENTER, BOTTOM);
		text(widthText, width / 2f, height - TEXT_OFFSET);
		float lineY = height - TEXT_OFFSET - (textSize / 2f);
		line(MARGIN, lineY, width / 2f - TEXT_BUFFER, lineY);
		line(width - MARGIN, lineY, width / 2f + TEXT_BUFFER, lineY);
		line(MARGIN, lineY - END_SIZE, MARGIN, lineY + END_SIZE);
		line(width - MARGIN, lineY - END_SIZE, width - MARGIN, lineY + END_SIZE);
		
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
		if (flipLeftToRight) {
			scale(-1, 1);
			translate(-width, 0);
		}
		translate(MARGIN, MARGIN);
		if (rotate90deg) {
			translate(img.height, 0);
			rotate(HALF_PI); // 90-deg clockwise
		}
		image(img, 0, 0);
	}
	
	public void update(PGraphics g) {
		if (g != null && img != null) {
			img.copy(g, 0, 0, g.width, g.height, 0, 0, img.width, img.height);
			redraw();
		}
	}
}
