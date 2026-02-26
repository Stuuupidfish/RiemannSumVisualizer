package com.ham;
import codedraw.CodeDraw;
import codedraw.TextFormat;

public class UIHandler {
    private CodeDraw cd;
    private TextFormat tf;

    public UIHandler (CodeDraw cd)
    {
        this.cd = cd;
        this.tf = cd.getTextFormat();
    }

    public void drawEquation(String a, String b, String n, String expression)
    {
        /*  
         b
         Σ  f(x)Δx
         a
        */
        tf.setFontSize(100);
        cd.drawText(60, 50, "Σ");

        tf.setFontSize(20);
        if (b != null && b.length() > 0)
        {
            cd.drawText(90, 35, b);
        }
        else
        {
            cd.drawText(90, 35, "b");
        }
        
        if (a != null && a.length() > 0)
        {
            cd.drawText(90, 135, a);
        }
        else
        {
            cd.drawText(90, 135, "a");
        }
        tf.setFontSize(32);

        if (expression != null && expression.length() > 0)
        {
            cd.drawText(135, 80, "(" + expression + ") ");
        }
        else
        {
            cd.drawText(135, 80, "f(x) ");
        }

        if (b != null && b.length() > 0 && a != null && a.length() > 0 && n != null && n.length() > 0)
        {
            if (expression != null && expression.length() > 0)
            {
                cd.drawText(135+20+20*expression.length(), 80, "((" + b + "-" + a + ")/" + n + ")");
            }
            else
            {
                cd.drawText(165, 80, "Δx");
            }
        }
        else
        {
            cd.drawText(165, 80, "Δx");
        }

        tf.setFontSize(20);
        cd.drawText(60, 170, "n = ");
        
        cd.drawText(60, 200, "a = ");

        cd.drawText(60, 230, "b = ");

        cd.drawText(42, 260, "f(x) = ");
    }

}
