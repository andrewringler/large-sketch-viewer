package largesketchviewer;

import static java.lang.Math.round;

import java.awt.image.BufferedImage;

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
		return new LargeSketchViewer(theParent, false, frameRate, false);
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
		return new LargeSketchViewer(theParent, rotate90deg, DEFAULT_FRAME_RATE, false);
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
		return new LargeSketchViewer(theParent, rotate90deg, frameRate, false);
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
	 * @param flipLeftToRight
	 *            - flip image left to right, IE mirror, useful if you are rear-projection with your final piece
	 */
	public static LargeSketchViewer smallPreview(PApplet theParent, boolean rotate90deg, float frameRate, boolean flipLeftToRight) {
		return new LargeSketchViewer(theParent, rotate90deg, frameRate, flipLeftToRight);
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
		return new LargeSketchViewer(theParent, false, DEFAULT_FRAME_RATE, false);
	}
	
	private LargeSketchViewer(PApplet theParent, boolean rotate90deg, float frameRate, boolean flipLeftToRight) {
		this.theParent = theParent;
		
		if (theParent.args != null && theParent.args.length != 0 && theParent.args[0].equals("nopreview")) {
			// no preview, we are done
			return;
		}
		
		// register post with main sketch to capture screen
		theParent.registerMethod("post", this);
		
		// register dispose with main sketch so we know when it is closing
		theParent.registerMethod("dispose", this);
		
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
		if (smallWindowWidth > (theParent.displayWidth - buffer)) {
			// sketch is too wide, shrink it horizontally
			smallWindowWidth = theParent.displayWidth - buffer;
			smallWindowHeight = smallWindowWidth / ratio;
		}
		if (smallWindowHeight > (theParent.displayHeight - buffer)) {
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
		
		pSmall = new ViewerWindowSketch(theParent, round(smallWindowWidth), round(smallWindowHeight), imageWidth, imageHeight, rotate90deg, flipLeftToRight);
		
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
			pSmall.update((BufferedImage) theParent.copy().getNative());
			lastUpdateMillis = theParent.millis();
		}
	}
	
	/*
	 * Main sketch is shutting down, we need to
	 * manually stop small sketch now too
	 */
	public void dispose() {
		try {
			pSmall.exit();
		} catch (Exception e) {
			// nothing to do here, just
			// prevent Processing console from showing errors
		}
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
