# Large Sketch Viewer
Displays a small preview for sketches that are too large to view on your current screen.

I created this library because I was developing a sketch targeted at an HD television screen with resolution of 1920x1080. I wanted to be able to run, test and modify my sketch when I only had access to my laptop which has a resolution of 1440x900. This sketch allows you to create a pixel perfect sketch at your final target resolution 1920x1080 while working on a smaller screen. IE when you are ready to run your sketch on your final screen, the only thing you will need to do is delete the line that initializes this library.

This library serves a different purpose than Daniel Shiffman's [Most Pixels Ever](https://github.com/shiffman/Most-Pixels-Ever-Processing) library. Most Pixels Ever is used to span and keep synchronized multiple Processing sketches across large and/or multiple physical screens. Large Sketch Viewer does not do this, it is only useful for developing single large screen Sketches from a smaller screen computer.   

## References
 * <https://github.com/processing/processing-library-template>
 * <https://github.com/processing/processing/wiki/Library-Basics>