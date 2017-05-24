package largesketchviewer;

import static javax.swing.SwingUtilities.invokeLater;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class ViewerWindowSketch {
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
	
	private JFrame frame;
	private ImagePanel imagePanel;
	
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
		
		invokeLater(new Runnable() {
			@Override
			public void run() {
				frame = new JFrame(PREVIEW_WINDOW_TITLE);
				frame.setLayout(new BorderLayout()); // default
				frame.setPreferredSize(new Dimension(w, h));
				frame.setResizable(true);
				Container content = frame.getContentPane();
				imagePanel = new ImagePanel();
				content.add(imagePanel, BorderLayout.CENTER);
				
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	public void update(BufferedImage img) {
		invokeLater(new Runnable() {
			@Override
			public void run() {
				imagePanel.setImage(img);
				imagePanel.repaint();
			}
		});
	}
	
	//	public void setup() {
	//		surface.setTitle(PREVIEW_WINDOW_TITLE);
	//		
	//		/*
	//		 * NOTE: we load our own fonts because sometimes
	//		 * loading two instances of PApplet causes Processing to not find
	//		 * the default fonts
	//		 */
	//		openSansSemiBold16 = loadFont("OpenSans-Semibold-16.vlw");
	//		
	//		img = createImage(imageWidth - (MARGIN * 2), imageHeight - (MARGIN * 2), RGB);
	//		noLoop();
	//		
	//		background(255);
	//		
	//		// Draw axis labels, original pixel dimensions of sketch
	//		// for reference
	//		fill(0);
	//		stroke(0);
	//		strokeWeight(1);
	//		textFont(openSansSemiBold16);
	//		
	//		// realized framerate
	//		textAlign(LEFT, TOP);
	//		text("fps: " + theParent.frameRate, MARGIN, TEXT_OFFSET);
	//		
	//		String widthText = theParent.width + "px";
	//		String heightText = theParent.height + "px";
	//		if (rotate90deg) {
	//			widthText = theParent.height + "px";
	//			heightText = theParent.width + "px";
	//		}
	//		
	//		textAlign(CENTER, BOTTOM);
	//		text(widthText, width / 2f, height - TEXT_OFFSET);
	//		float lineY = height - TEXT_OFFSET - (textSize / 2f);
	//		line(MARGIN, lineY, width / 2f - TEXT_BUFFER, lineY);
	//		line(width - MARGIN, lineY, width / 2f + TEXT_BUFFER, lineY);
	//		line(MARGIN, lineY - END_SIZE, MARGIN, lineY + END_SIZE);
	//		line(width - MARGIN, lineY - END_SIZE, width - MARGIN, lineY + END_SIZE);
	//		
	//		pushMatrix();
	//		textAlign(CENTER, CENTER);
	//		translate(TEXT_OFFSET + textSize / 2f, height / 2f);
	//		rotate(-HALF_PI);
	//		text(heightText, 0, 0);
	//		popMatrix();
	//		float lineX = TEXT_OFFSET + textSize / 2f;
	//		line(lineX, MARGIN, lineX, height / 2f - TEXT_BUFFER);
	//		line(lineX, height - MARGIN, lineX, height / 2f + TEXT_BUFFER);
	//		line(lineX - END_SIZE, MARGIN, lineX + END_SIZE, MARGIN);
	//		line(lineX - END_SIZE, height - MARGIN, lineX + END_SIZE, height - MARGIN);
	//	}
	//	
	//	public void draw() {
	//		if (flipLeftToRight) {
	//			scale(-1, 1);
	//			translate(-width, 0);
	//		}
	//		translate(MARGIN, MARGIN);
	//		if (rotate90deg) {
	//			translate(img.height, 0);
	//			rotate(HALF_PI); // 90-deg clockwise
	//		}
	//		image(img, 0, 0);
	//	}
	//	
	//	public void update(PGraphics g) {
	//		if (g != null && img != null) {
	//			img.copy(g, 0, 0, g.width, g.height, 0, 0, img.width, img.height);
	//			redraw();
	//		}
	//	}
	
	// https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
	class ImagePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private BufferedImage image;
		
		public void setImage(BufferedImage image) {
			this.image = image;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (image != null) {
				Graphics2D g2 = (Graphics2D) g; //https://stackoverflow.com/questions/179415/java2d-is-it-always-safe-to-cast-graphics-into-graphics2d
				Rectangle bounds = g.getClipBounds();
				
				// https://stackoverflow.com/questions/23457754/how-to-flip-bufferedimage-in-java
				if (flipLeftToRight) {
					AffineTransform at = new AffineTransform();
					at.concatenate(AffineTransform.getScaleInstance(-1, 1));
					at.concatenate(AffineTransform.getTranslateInstance(-(int) bounds.getWidth(), 0));
					g2.transform(at);
				}
				if (rotate90deg) {
					AffineTransform at = AffineTransform.getRotateInstance(PApplet.HALF_PI, bounds.getWidth() / 2.0, bounds.getHeight() / 2.0);
					g2.transform(at);
				}
				//			translate(img.height, 0);
				//			rotate(HALF_PI); // 90-deg clockwise
				//		}
				
				if (bounds != null) {
					g2.drawImage(image, 0, 0, (int) bounds.getWidth(), (int) bounds.getHeight(), this);
				} else {
					g2.drawImage(image, 0, 0, this);
				}
			}
		}
	}
	
	public void exit() {
		invokeLater(new Runnable() {
			@Override
			public void run() {
				if (frame != null) {
					frame.dispose();
				}
			}
		});
	}
}
