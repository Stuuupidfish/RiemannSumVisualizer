package com.ham;
import java.awt.Font;
import codedraw.CodeDraw;
import codedraw.TextFormat;
import codedraw.Palette;

public class UIHandler {
    private CodeDraw cd;
    private TextFormat tf;
    private String a;
    private String b;
    private String n;
    private String expression;

    public UIHandler(CodeDraw cd)
    {
        this.cd = cd;
        this.tf = cd.getTextFormat();
        a = null;
        b = null;
        n = null;
        expression = null;
    }
    public UIHandler(CodeDraw cd, String a, String b, String n, String expression)
    {
        this.cd = cd;
        this.tf = cd.getTextFormat();
        this.a = a;
        this.b = b;
        this.n = n;
        this.expression = expression;
    }

    public void setA(String a)
    {
        this.a = a;
    }
    public void setB(String b)
    {
        this.b = b;
    }
    public void setN(String n)
    {
        this.n = n;
    }
    public void setExpression(String expression)
    {
        this.expression = expression;
    }

    public void drawEquation()
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
