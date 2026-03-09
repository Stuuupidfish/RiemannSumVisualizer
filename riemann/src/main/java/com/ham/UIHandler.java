package com.ham;
import java.awt.Font;
import codedraw.CodeDraw;
import codedraw.TextFormat;
import codedraw.Palette;

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
        tf.setFontName(Font.MONOSPACED);
        tf.setBold(true);
        /*  
         b
         Σ  f(x)Δx
         a
        */
        cd.setColor(Palette.GREEN);
        tf.setFontSize(100);
        cd.drawText(60, 40, "Σ");

        tf.setFontSize(20);
        if (b != null && b.length() > 0)
        {
            cd.drawText(70, 25, b);
        }
        else
        {
            cd.drawText(70, 25, "b");
        }
        
        if (a != null && a.length() > 0)
        {
            cd.drawText(70, 125, a);
        }
        else
        {
            cd.drawText(70, 125, "a");
        }
        tf.setFontSize(32);

        String str = "";
        if (expression != null && expression.length() > 0)
        {
            str = "(" + expression + ")";
        }
        else
        {
            str = "f(x) ";
        }

        if (b != null && b.length() > 0 && a != null && a.length() > 0 && n != null && n.length() > 0)
        {
            if (expression != null && expression.length() > 0)
            {
                str += "((" + b + "-" + a + ")/" + n + ")";
            }
            else
            {
                str += "Δx";
            }
        }
        else
        {
            str += "Δx";
        }
        cd.drawText(135, 70, str);

        tf.setFontSize(20);
        cd.drawText(40, 160, "n = ");
        
        cd.drawText(40, 190, "a = ");

        cd.drawText(40, 220, "b = ");

        cd.drawText(12, 250, "f(x) = ");
    }

}
