package com.ham;

// https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/package-summary.html
// https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/Image.html#drawLine(double,double,double,double)
import codedraw.CodeDraw;
import codedraw.EventScanner;
import codedraw.MouseClickEvent;
import codedraw.MouseMoveEvent;
import codedraw.Palette;
// https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/Palette.html

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
        EventScanner scanner = cd.getEventScanner();
        UIHandler UI = new UIHandler(cd);

        cd.setTitle("Riemann Sum Visualizer");

        String expression = "sin(x)";//"sin(20x)*sin(x)";
        Function f = new Function(expression);
        double a;
        double b;
        int n;

        cd.show();

        InputField nInput = new InputField(95, 160, 100, 25, cd);
        InputField aInput = new InputField(95, 190, 100, 25, cd);
        InputField bInput = new InputField(95, 220, 100, 25, cd);
        InputField yInput = new InputField(95, 250, 150, 25, cd);

        boolean caretActive = false;
        InputField activeInput = null;

        while (!cd.isClosed())
        {
            while (scanner.hasEventNow()) 
            {
                if (scanner.hasMouseClickEvent())
                {
                    System.out.println("mouse clicked");
                    
                    // https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/EventScanner.html#hasMouseDownEvent()
                    MouseClickEvent mouse = scanner.nextMouseClickEvent();

                    double mouseX = mouse.getX();
                    double mouseY = mouse.getY();
                    
                    double caretX;
                    double caretY;
                    if (nInput.contains(mouseX, mouseY))
                    {
                        caretActive = true;
                        nInput.setActive(true);
                        caretX = nInput.getCoord()[0];
                        caretY = nInput.getCoord()[1];

                        aInput.setActive(false);
                        bInput.setActive(false);
                        yInput.setActive(false);
                    }
                    else if (aInput.contains(mouseX, mouseY))
                    {
                        caretActive = true;
                        aInput.setActive(true);
                        caretX = aInput.getCoord()[0];
                        caretY = aInput.getCoord()[1];

                        nInput.setActive(false);
                        bInput.setActive(false);
                        yInput.setActive(false);
                    }
                    else if (bInput.contains(mouseX, mouseY))
                    {
                        caretActive = true;
                        bInput.setActive(true);
                        caretX = bInput.getCoord()[0];
                        caretY = bInput.getCoord()[1];

                        aInput.setActive(false);
                        nInput.setActive(false);
                        yInput.setActive(false);
                    }
                    else if (yInput.contains(mouseX, mouseY))
                    {
                        caretActive = true;
                        yInput.setActive(true);
                        caretX = yInput.getCoord()[0];
                        caretY = yInput.getCoord()[1];
                        
                        aInput.setActive(false);
                        nInput.setActive(false);
                        bInput.setActive(false);
                        
                    }
                    else
                    {
                        caretActive = false;
                        aInput.setActive(false);
                        nInput.setActive(false);
                        bInput.setActive(false);
                        yInput.setActive(false);
                    }
                }
                else 
                {
                    scanner.nextEvent();
                }

                //KEY INPUTS-- NOT DONE PART 
                //if any one button is selected, listen for keyboard input
                //pass in input string 
                if (caretActive) //will need this: public boolean hasKeyDownEvent()
                {
                    String inputStr = "";
                    //cd.fillRectangle(n, a, b, n);
                    // https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/Key.html
                    // Key key = scanner.hasKeyDownEvent().getKey();
                    // inputStr += key;
                }
            }

            drawGrid(cd);
            //drawAll(CodeDraw cd, String expression, double a, double b, int n, Function f, UIHandler UI, String approxType)
            drawAll(cd, expression, 0, 10, 100, f, UI, "left");
            
            nInput.drawBox();
            aInput.drawBox();
            bInput.drawBox();
            yInput.drawBox();
            
            cd.show();
        }
    }

    public static void drawAll(CodeDraw cd, String expression, double a, double b, int n, Function f, UIHandler UI, String approxType)
    {
        UI.drawEquation(a + "", b + "", n + "", expression);
        drawRS(approxType, a, b, n, f, cd);
        drawFunction(f, cd);
    }

    public static void drawGrid(CodeDraw cd)
    {
        //reset canvas
        cd.clear();

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

        //xy axis
        cd.setColor(Palette.BLACK);
        cd.drawLine(-WIDTH/2 + X_OFFSET, 0 + Y_OFFSET, WIDTH/2 + X_OFFSET, 0 + Y_OFFSET);
        cd.drawLine(0 + X_OFFSET, -HEIGHT/2 + Y_OFFSET, 0 + X_OFFSET, HEIGHT/2 + Y_OFFSET);
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
    //drawRectangle(double x, double y, double width, double height)
    public static void drawRS(String approxType, double a, double b, int n, Function f, CodeDraw cd)
    {
        double dx = (b-a)/n;
        if (approxType.equals("right"))
        {
            for (int i = 1; i <= n; i++)
            {
                double x = a + i * dx;
                double y = f.getY(x);

                // Convert to screen coordinates
                double xScreen = x * SCALE + X_OFFSET;  // left edge
                double heightScreen = Math.abs(y * SCALE);  // absolute height
                double yScreen;  // top position
                if (y >= 0) {
                    yScreen = -y * SCALE + Y_OFFSET;  // top of rect is at y, draws down to x-axis
                } else {
                    yScreen = Y_OFFSET;               // top of rect is x-axis, draws down to y
                }
                cd.setColor(Palette.AQUA);
                cd.fillRectangle(xScreen - (dx * SCALE), yScreen, dx * SCALE, heightScreen);
                cd.setColor(Palette.GREEN);
                cd.drawRectangle(xScreen - (dx * SCALE), yScreen, dx * SCALE, heightScreen);
            }
        }
        else if (approxType.equals("left"))
        {
            for (int i = 0; i < n; i++)
            {
                double x = a + i * dx;
                double y = f.getY(x);

                double xScreen = x * SCALE + X_OFFSET;  
                double heightScreen = Math.abs(y * SCALE); 
                double yScreen;
                if (y >= 0) {
                    yScreen = -y * SCALE + Y_OFFSET;  // top of rect is at y, draws down to x-axis
                } else {
                    yScreen = Y_OFFSET;               // top of rect is x-axis, draws down to y
                }
                
                cd.setColor(Palette.AQUA);
                cd.fillRectangle(xScreen, yScreen, dx * SCALE, heightScreen);
                cd.setColor(Palette.GREEN);
                cd.drawRectangle(xScreen, yScreen, dx * SCALE, heightScreen);
            }
        }
        else if (approxType.equals("mid"))
        {
            for (int i = 0; i < n; i++)
            {
                double x = a + (i + 0.5) * dx;
                double y = f.getY(x);

                double xScreen = x * SCALE + X_OFFSET;  
                double heightScreen = Math.abs(y * SCALE); 
                double yScreen;
                if (y >= 0) {
                    yScreen = -y * SCALE + Y_OFFSET;  // top of rect is at y, draws down to x-axis
                } else {
                    yScreen = Y_OFFSET;               // top of rect is x-axis, draws down to y
                }
                
                cd.setColor(Palette.AQUA);
                cd.fillRectangle(xScreen - (dx * SCALE/2), yScreen, dx * SCALE, heightScreen);
                cd.setColor(Palette.GREEN);
                cd.drawRectangle(xScreen - (dx * SCALE/2), yScreen, dx * SCALE, heightScreen);
            }
        }
    }

}
