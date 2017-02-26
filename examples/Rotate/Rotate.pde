/* Rotate
 * Rotate the preview window. This is useful if you will be rotating your final screen. IE you will be hanging a TV sideways
 * or placing your projector on its side.
 */

// *** (Step 1) *** Import library line
import largesketchviewer.*;

void setup() {
  size(1920, 1080);

  // *** (Step 2) *** Create the small preview (initialize the library)
  // second argument true, states that we want to rotate our preview window
  LargeSketchViewer.smallPreview(this, true);
}

void draw() {
  background(80);
  textAlign(CENTER, CENTER);
  textSize(22);
  int size = 200;

  fill(255);
  ellipse(150, 150, size, size);
  fill(0);
  text("150,150", 150, 150);

  fill(255);
  ellipse(150, 930, size, size);
  fill(0);
  text("150,930", 150, 930);

  fill(255);
  ellipse(1770, 150, size, size);
  fill(0);
  text("1770,150", 1770, 150);

  fill(255);
  ellipse(1770, 930, size, size);
  fill(0);
  text("1770,930", 1770, 930);
}