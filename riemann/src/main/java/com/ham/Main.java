package com.ham;
// https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/package-summary.html
// https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/Image.html#drawLine(double,double,double,double)
import codedraw.CodeDraw;
import codedraw.Palette;
// run on POWERSHELL:
// (cd to riemann)
// mvn clean compile exec:java "-Dexec.mainClass=com.ham.Main"
public class Main {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int X_OFFSET = 800/2;
    private static final int Y_OFFSET = 600/2;
    private static final double SCALE = 25.0;  // pixels per unit
    public static void main (String args[])
    {  

        CodeDraw cd = new CodeDraw(WIDTH, HEIGHT);
        Function f = new Function("-2x+3");
        
        drawGrid(X_OFFSET, Y_OFFSET, cd);
        drawFunction(f, X_OFFSET, Y_OFFSET, cd);
        
        cd.show();
    }

    public static void drawGrid(int xOff, int yOff, CodeDraw cd)
    {
        //reset canvas
        cd.clear();

        //xy axis
        cd.setColor(Palette.BLACK);
        cd.drawLine(-WIDTH/2 + X_OFFSET, 0 + Y_OFFSET, WIDTH/2 + X_OFFSET, 0 + Y_OFFSET);
        cd.drawLine(0 + X_OFFSET, -HEIGHT/2 + Y_OFFSET, 0 + X_OFFSET, HEIGHT/2 + Y_OFFSET);

        //grid lines
        cd.setColor(Palette.LIGHT_GRAY);
        for (int i = -WIDTH/2; i < WIDTH/2; i += SCALE)
        {
            if (i != 0) {  //skip y-axis
                cd.drawLine(i + xOff, -HEIGHT/2 + yOff, i + xOff, HEIGHT/2 + yOff);
            }
        }
        for (int i = -HEIGHT/2; i < HEIGHT/2; i += SCALE)
        {
            if (i != 0) {  //skip x-axis
                cd.drawLine(-WIDTH/2 + xOff, i + yOff, WIDTH/2 + xOff, i + yOff);
            }
        }
    }

    public static void drawFunction(Function f, int xOff, int yOff, CodeDraw cd)
    {
        cd.setColor(Palette.GREEN);
        for (int i = -WIDTH/2; i < WIDTH/2; i++)
        {
            double x = i / SCALE;  //convert screen coordinate to math coordinate
            double y = f.getY(x);
            cd.drawPoint(i + xOff, -(y * SCALE) + yOff);  //convert back to screen coordinates
        }
    }

    public static void drawRS(String approxType, double a, double b, int n, Function f, int xOff, int yOff, CodeDraw cd)
    {

    }

}
