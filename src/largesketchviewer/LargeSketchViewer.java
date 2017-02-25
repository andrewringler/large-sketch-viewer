package largesketchviewer;

import static java.lang.Math.round;

import processing.core.PApplet;

/**
 * @example Empty
 */
public class LargeSketchViewer {
	public final static String VERSION = "##library.prettyVersion##";
	
	private final PApplet theParent;
	
	private ViewerWindowSketch pSmall;
	private int lastUpdateMillis;
	private int desiredMillisBetweenUpdates = round(1000f / 60f);
	
	int myVariable = 0;
	
	/**
	 * Create a new small screen version of your sketch. Call one time
	 * in the setup() method of your sketch to initialize this library.
	 * 
	 * @example Empty
	 * @param theParent
	 *            - your sketch, usually pass `this`
	 * @param frameRate
	 *            - target frameRate of your preview window, typically this will be lower than your sketch frame
	 *            rate for performance reasons
	 */
	public static LargeSketchViewer smallPreview(PApplet theParent, float frameRate) {
		return new LargeSketchViewer(theParent, frameRate);
	}
	
	/**
	 * Create a new small screen version of your sketch. Call one time
	 * in the setup() method of your sketch to initialize this library.
	 * 
	 * @example Empty
	 * @param theParent
	 *            - your sketch, usually pass this
	 */
	public static LargeSketchViewer smallPreview(PApplet theParent) {
		return new LargeSketchViewer(theParent, 60);
	}
	
	private LargeSketchViewer(PApplet theParent, float frameRate) {
		this.theParent = theParent;
		// register post with main sketch to capture screen
		theParent.registerMethod("post", this);
		
		int buffer = 100;
		float smallWindowWidth = theParent.width;
		float smallWindowHeight = theParent.height;
		float ratio = (float) theParent.width / (float) theParent.height;
		if (smallWindowWidth > theParent.displayWidth) {
			// sketch is too wide, shrink it horizontally
			smallWindowWidth = theParent.displayWidth - buffer;
			smallWindowHeight = smallWindowWidth / ratio;
		}
		if (smallWindowHeight > theParent.displayHeight) {
			// sketch is too tall, shrink it
			smallWindowHeight = theParent.displayHeight - buffer;
			smallWindowWidth = smallWindowHeight * ratio;
		}
		
		pSmall = new ViewerWindowSketch(theParent, round(smallWindowWidth), round(smallWindowHeight));
		PApplet.runSketch(new String[] { "--window-color=#000000", ViewerWindowSketch.class.getCanonicalName() }, pSmall);
		
		// register with small window in case user closes it
		pSmall.registerMethod("dispose", this);
		
		if (frameRate > 0) {
			desiredMillisBetweenUpdates = round(1000f / frameRate);
		}
		lastUpdateMillis = theParent.millis();
	}
	
	// post – after draw() has exited (not safe to draw)
	public void post() {
		// limit update frequency to desired frame rate
		int millisSinceLastUpdate = theParent.millis() - lastUpdateMillis;
		if (millisSinceLastUpdate >= desiredMillisBetweenUpdates) {
			pSmall.update(theParent.getGraphics());
			lastUpdateMillis = theParent.millis();
		}
	}
	
	/*
	 * small window is shutting down, we need to
	 * manually stop the main sketch now too
	 */
	public void dispose() {
		theParent.noLoop(); // prevents 3D thread errors during shutdown
		theParent.exit();
	}
	
	/**
	 * return the version of the Library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}
}
