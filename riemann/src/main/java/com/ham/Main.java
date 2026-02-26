package com.ham;
// https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/package-summary.html
// https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/Image.html#drawLine(double,double,double,double)
import codedraw.CodeDraw;
import codedraw.Palette;
// run on POWERSHELL:
// (cd to riemann)
// mvn compile exec:java "-Dexec.mainClass=com.ham.Main"
public class Main {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int X_OFFSET = 800/2;
    private static final int Y_OFFSET = 600/2;
    private static double SCALE = 25.0;  // pixels per unit
    public static void main (String args[])
    {  
        CodeDraw cd = new CodeDraw(WIDTH, HEIGHT);
        UIHandler UI = new UIHandler(cd);
        cd.setTitle("Riemann Sum Visualizer");
        String expression = "3x*cos(3x)";
        Function f = new Function(expression);
        
        drawGrid(cd);
        drawFunction(f, cd);
        UI.drawEquation("0", "10", "5", expression);
        //drawRS("right", 0, 8, 8, f, cd);
        
        cd.show();
    }

    public static void drawGrid(CodeDraw cd)
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
                cd.drawLine(i + X_OFFSET, -HEIGHT/2 + Y_OFFSET, i + X_OFFSET, HEIGHT/2 + Y_OFFSET);
            }
        }
        for (int i = -HEIGHT/2; i < HEIGHT/2; i += SCALE)
        {
            if (i != 0) {  //skip x-axis
                cd.drawLine(-WIDTH/2 + X_OFFSET, i + Y_OFFSET, WIDTH/2 + X_OFFSET, i + Y_OFFSET);
            }
        }
    }

    public static void drawFunction(Function f, CodeDraw cd)
    {
        double[] prevPoint = new double[2];
        double[] curPoint = new double[2];
        cd.setColor(Palette.GREEN);
        for (int i = -WIDTH/2; i < WIDTH/2; i++)
        {
            double x = i / SCALE;  //convert screen coordinate to math coordinate
            double y = f.getY(x);
            
            prevPoint = curPoint;
            curPoint = new double[2];
            curPoint[0] = i + X_OFFSET;
            curPoint[1] = -(y * SCALE) + Y_OFFSET;
            
            // public void drawLine(double startX, double startY, double endX, double endY)
            if (i != -WIDTH/2)
            {
                cd.drawLine(prevPoint[0], prevPoint[1], curPoint[0], curPoint[1]);
            }
                 
            cd.drawPoint(i + X_OFFSET, -(y * SCALE) + Y_OFFSET);  //convert back to screen coordinates
        }
    }

    //fillRectangle(double x, double y, double width, double height)
    public static void drawRS(String approxType, double a, double b, int n, Function f, CodeDraw cd)
    {
        if (approxType.equals("right"))
        {
            for (int i = 0; i < n; i++)
            {
                //cd.fillRectangle(1,1 ,1 ,1 );
            }
        }
        else if (approxType.equals("left"))
        {
            for (int i = 0; i < n; i++)
            {

            }
        }
        else if (approxType.equals("mid"))
        {
            for (int i = 0; i < n; i++)
            {

            }
        }
    }

}
