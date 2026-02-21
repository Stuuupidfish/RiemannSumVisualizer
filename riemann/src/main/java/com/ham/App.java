package com.ham;
import codedraw.CodeDraw;
import codedraw.Palette;
/**
 * Hello world!
 *
 */

// run on POWERSHELL:
// mvn clean compile exec:java "-Dexec.mainClass=com.ham.App"
public class App 
{
    public static void main( String[] args )
    {
        // Creates a new CodeDraw window with a size of 400x400 pixel.
        CodeDraw cd = new CodeDraw(400, 400);

        // Sets the drawing color to red.
        cd.setColor(Palette.RED);
        // Draws the outline of a rectangle.
        cd.drawRectangle(100, 100, 200, 100);
        // Draws a filled square.
        cd.fillSquare(180, 150, 80);

        // Changes the color to light blue.
        cd.setColor(Palette.LIGHT_BLUE);
        cd.fillCircle(300, 200, 50);

        // Finally, the method "show" must be called
        // to display the drawn shapes in the CodeDraw window.
        cd.show();
    }
}
