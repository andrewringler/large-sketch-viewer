package largesketchviewer;

import static java.lang.Math.round;

import processing.core.PApplet;

/**
 * @example Empty
 */
public class LargeSketchViewer {
	private static final int DEFAULT_FRAME_RATE = 60;
	
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
		return new LargeSketchViewer(theParent, false, frameRate);
	}
	
	/**
	 * Create a new small screen version of your sketch. Call one time
	 * in the setup() method of your sketch to initialize this library.
	 * 
	 * @example Empty
	 * @param theParent
	 *            - your sketch, usually pass `this`
	 * @param rotate90deg
	 *            - true: rotate the preview window 90°clockwise, this is useful if you will be rotating your final physical screen
	 */
	public static LargeSketchViewer smallPreview(PApplet theParent, boolean rotate90deg) {
		return new LargeSketchViewer(theParent, rotate90deg, DEFAULT_FRAME_RATE);
	}
	
	/**
	 * Create a new small screen version of your sketch. Call one time
	 * in the setup() method of your sketch to initialize this library.
	 * 
	 * @example Empty
	 * @param theParent
	 *            - your sketch, usually pass `this`
	 * @param rotate90deg
	 *            - true: rotate the preview window 90°clockwise, this is useful if you will be rotating your final physical screen
	 * @param frameRate
	 *            - target frameRate of your preview window, typically this will be lower than your sketch frame
	 *            rate for performance reasons
	 */
	public static LargeSketchViewer smallPreview(PApplet theParent, boolean rotate90deg, float frameRate) {
		return new LargeSketchViewer(theParent, rotate90deg, frameRate);
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
		return new LargeSketchViewer(theParent, false, DEFAULT_FRAME_RATE);
	}
	
	private LargeSketchViewer(PApplet theParent, boolean rotate90deg, float frameRate) {
		this.theParent = theParent;
		// register post with main sketch to capture screen
		theParent.registerMethod("post", this);
		
		float parentWidth = theParent.width;
		float parentHeight = theParent.height;
		if (rotate90deg) {
			// swap width and height
			parentWidth = theParent.height;
			parentHeight = theParent.width;
		}
		
		int buffer = 100;
		float smallWindowWidth = parentWidth;
		float smallWindowHeight = parentHeight;
		float ratio = parentWidth / parentHeight;
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
		
		int imageWidth = round(smallWindowWidth);
		int imageHeight = round(smallWindowHeight);
		if (rotate90deg) {
			/*
			 * if we are rotating still keep the image width and height
			 * the same orientation as the original sketch
			 * IE, swap them back
			 */
			imageWidth = round(smallWindowHeight);
			imageHeight = round(smallWindowWidth);
		}
		
		pSmall = new ViewerWindowSketch(theParent, round(smallWindowWidth), round(smallWindowHeight), imageWidth, imageHeight, rotate90deg);
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
