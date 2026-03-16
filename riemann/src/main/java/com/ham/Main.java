package com.ham;

// https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/package-summary.html
// https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/Image.html#drawLine(double,double,double,double)
import codedraw.CodeDraw;
import codedraw.EventScanner;
import codedraw.KeyPressEvent;
import codedraw.MouseClickEvent;
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
        ApproxSelector apprx = new ApproxSelector(600, 25, 30, 100, 30, cd);
        
        cd.setTitle("Riemann Sum Visualizer");

        InputField nInput = new InputField(95, 160, 100, 25, cd);
        InputField aInput = new InputField(95, 190, 100, 25, cd);
        InputField bInput = new InputField(95, 220, 100, 25, cd);
        InputField yInput = new InputField(95, 250, 150, 25, cd);
        
        RuntimeState state = new RuntimeState(0, 0, 0, "", "mid", nInput, aInput, bInput, yInput);

        UI.setA(state.getA()+"");
        UI.setB(state.getB()+"");
        UI.setN(state.getN()+"");
        UI.setExpression(state.getExpression());
        UI.setApproxType(state.getApproxType());

        while (!cd.isClosed())
        {
            while (scanner.hasEventNow()) 
            {
                if (scanner.hasMouseClickEvent())
                {
                    // https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/EventScanner.html#hasMouseDownEvent()
                    MouseClickEvent mouse = scanner.nextMouseClickEvent();

                    double mouseX = mouse.getX();
                    double mouseY = mouse.getY();

                    String newApproxType = apprx.handleClick(mouseX, mouseY);
                    if (newApproxType != null)
                    {
                        state.setApproxType(newApproxType);
                        UI.setApproxType(newApproxType);
                    }
                    
                    //commits changes when user clicks off an input field
                    InputField currentActive = state.getActiveInput();
                    if (currentActive != null && !currentActive.contains(mouseX, mouseY))
                    {
                        state.commitActiveInput(UI);
                    }
                    
                    if (nInput.contains(mouseX, mouseY))
                    {
                        state.setInputsActive(true, false, false, false);
                        state.setActiveInput(nInput);
                        state.setCaretActive(true);
                    }
                    else if (aInput.contains(mouseX, mouseY))
                    {
                        state.setInputsActive(false, true, false, false);
                        state.setActiveInput(aInput);
                        state.setCaretActive(true);
                    }
                    else if (bInput.contains(mouseX, mouseY))
                    {
                        state.setInputsActive(false, false, true, false);
                        state.setActiveInput(bInput);
                        state.setCaretActive(true);
                    }
                    else if (yInput.contains(mouseX, mouseY))
                    {
                        state.setInputsActive(false, false, false, true);
                        state.setActiveInput(yInput);
                        state.setCaretActive(true);
                    }
                    else
                    {
                        state.setActiveInput(null);
                        state.setCaretActive(false);
                        state.setInputsActive(false, false, false, false);
                    }
                }
                if (scanner.hasKeyPressEvent())
                {
                    // https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/KeyPressEvent.html
                    KeyPressEvent keyEvent = scanner.nextKeyPressEvent();
                    if (state.getActiveInput() != null)
                    {
                        boolean submitted = state.getActiveInput().handleKeyInput(keyEvent);

                        if (submitted)
                        {
                            state.commitActiveInput(UI);
                        }
                    }
                }
                else 
                {
                    scanner.nextEvent();
                }
            }

            drawGrid(cd);
            drawAll(cd, state, UI, apprx);

            if (state.getCaretActive() && state.getActiveInput() != null)
            {
                InputField activeInput = state.getActiveInput();
                activeInput.drawText(activeInput.getValue()+"|");
            }

            cd.show();
        }
    }

    public static void drawAll(CodeDraw cd, RuntimeState state, UIHandler UI, ApproxSelector apprx)
    {
        UI.setApproxType(state.getApproxType());

        //doesnt draw function if expression is empty
        if (state.getExpression() == null || state.getExpression().trim().isEmpty())
        {
            drawInputFields(state);
            UI.setSum(null);
            UI.drawEquation();
            apprx.draw();
            return;
        }
        if (state.getN() > 0)
        {
            drawRS(cd, state);
            UI.setSum(state.getSum() + "");  //update sum in UI
        }
        drawFunction(state.getFunction(), cd);
        drawInputFields(state);
        UI.drawEquation();
        apprx.draw();
    }

    public static void drawInputFields(RuntimeState state)
    {
        InputField nInput = state.getNInput();
        InputField aInput = state.getAInput();
        InputField bInput = state.getBInput();
        InputField yInput = state.getYInput();

        nInput.drawBox();
        aInput.drawBox();
        bInput.drawBox();
        yInput.drawBox();
        nInput.drawText(nInput.getValue());
        aInput.drawText(aInput.getValue());
        bInput.drawText(bInput.getValue());
        yInput.drawText(yInput.getValue());
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
        cd.drawText(X_OFFSET + WIDTH/2 - 20, Y_OFFSET + 10, "x");
        cd.drawText(X_OFFSET + 10, Y_OFFSET - HEIGHT/2 + 10, "y");
    }

    public static void drawFunction(Function f, CodeDraw cd)
    {
        double[] prevPoint = new double[2];
        double[] curPoint = new double[2];
        boolean validPrev = false;
        cd.setColor(Palette.GREEN);
        for (int i = -WIDTH/2; i < WIDTH/2; i++)
        {
            double x = i / SCALE;  //convert screen coordinate to math coordinate
            double y = f.getY(x);
            
            //skip invalid values
            if (Double.isNaN(y) || Double.isInfinite(y)) 
            {
                validPrev = false;
                continue;
            }
            
            prevPoint = curPoint;
            curPoint = new double[2];
            curPoint[0] = i + X_OFFSET;
            curPoint[1] = -(y * SCALE) + Y_OFFSET;
            
            //draws line only if theres valid previous point
            if (validPrev)
            {
                cd.drawLine(prevPoint[0], prevPoint[1], curPoint[0], curPoint[1]);
            }
            validPrev = true;
                 
            cd.drawPoint(i + X_OFFSET, -(y * SCALE) + Y_OFFSET);  //convert back to screen coordinates
        }
    }

    public static void drawRS(CodeDraw cd, RuntimeState state)
    {
        double a = state.getA();
        double b = state.getB();
        int n = state.getN();
        Function f = state.getFunction();
        String approxType = state.getApproxType();

        if (n <= 0)
        {
            state.setSum(0.0);
            return;
        }

        double sum = 0;
        double dx = (b-a)/n;

        if (approxType.equals("right"))
        {
            for (int i = 1; i <= n; i++)
            {
                double x = a + i * dx;
                double y = f.getY(x);

                if (Double.isNaN(y) || Double.isInfinite(y))
                {
                    continue;
                }

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

                sum += y * dx;
            }
        }
        else if (approxType.equals("left"))
        {
            for (int i = 0; i < n; i++)
            {
                double x = a + i * dx;
                double y = f.getY(x);

                if (Double.isNaN(y) || Double.isInfinite(y))
                {
                    continue;
                }

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

                sum += y * dx;
            }
        }
        else if (approxType.equals("mid"))
        {
            for (int i = 0; i < n; i++)
            {
                double x = a + (i + 0.5) * dx;
                double y = f.getY(x);

                if (Double.isNaN(y) || Double.isInfinite(y))
                {
                    continue;
                }

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

                sum += y * dx;
            }
        }

        state.setSum(sum);
    }


}
