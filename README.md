# Large Sketch Viewer
Displays a small preview for sketches that are too large to view on your current screen.

I created this library because I was developing a sketch targeted at an HD television screen with a resolution of 1920x1080. I wanted to be able to run, test and modify my sketch when I only had access to my laptop which has a resolution of 1440x900. This sketch allows you to create a pixel perfect sketch at your final target resolution 1920x1080 while working on a smaller screen. This library does not change your Sketch size or mess with the width and height variables. So, when you are ready to run your Sketch on your final screen the only thing you need to do is delete the line that initializes this library.

## Related Projects
This library serves a different purpose than Daniel Shiffman's [Most Pixels Ever](https://github.com/shiffman/Most-Pixels-Ever-Processing) library. Most Pixels Ever is used to span and keep synchronized multiple Processing sketches across large and/or multiple physical screens. Large Sketch Viewer does not do this; this library is only useful for developing for single large screen that is larger than the screen on your development computer.   

## TODO
 * Step by step instructions on Readme page
 * manually set viewer window resolution
 * Performance?

## References
 * <https://github.com/processing/processing-library-template>
 * <https://github.com/processing/processing/wiki/Library-Basics>