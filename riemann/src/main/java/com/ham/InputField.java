package com.ham;
import java.awt.Font;

import codedraw.CodeDraw;
import codedraw.TextFormat;
import codedraw.Palette;
import codedraw.Key;
import codedraw.KeyPressEvent;

public class InputField extends RectRegion
{
    private StringBuilder value;
    private boolean isActive;
    private TextFormat tf;

    public InputField(double x, double y, int w, int h, CodeDraw cd)
    {
        super(x, y, w, h, cd);

        value = new StringBuilder();
        isActive = false;
        this.tf = cd.getTextFormat();
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }
    public boolean getIsActive()
    {
        return isActive;
    }

    public void setValue(String str)
    {
        value.setLength(0);
        value.append(str);
    }
    public String getValue()
    {
        return value.toString();
    }

    public void drawText(String display)
    {
        // https://krassnig.github.io/CodeDrawJavaDoc/v5.0.x/codedraw/TextFormat.html
        cd.setColor(Palette.DARK_BLUE);
        // https://docs.oracle.com/en/java/javase/22/docs/api/java.desktop/java/awt/Font.html#DIALOG
        tf.setFontName(Font.MONOSPACED);
        tf.setBold(true);
        tf.setFontSize(20);
        cd.drawText(coord[0]+2, coord[1]+2, display);
    }

    public boolean handleKeyInput(KeyPressEvent keyEvent)
    {
        if (!isActive)
        {
            return false;
        }

        char firstChar = keyEvent.getKey().toString().charAt(0);
        Key key = keyEvent.getKey();
        // Warning: horrendous conditional chain ahead... :/
        
        //shift press: (, ), +, *, ^
        if (keyEvent.getChar() == '+')
        {
            value.append('+');
        }
        else if (keyEvent.getChar() == '(')
        {
            value.append('(');
        }
        else if (keyEvent.getChar() == ')')
        {
            value.append(')');
        }
        else if (keyEvent.getChar() == '^')
        {
            value.append('^');
        }
        else if (keyEvent.getChar() == '*')
        {
            value.append('*');
        }

        //single press letters (x, sin, cos, tan, sqrt, cbrt, ...)
        else if (Character.isLetter(keyEvent.getChar()))
        {
            value.append(Character.toLowerCase(keyEvent.getChar()));
        }

        //single press: -, /
        else if (key == Key.MINUS)
        {
            value.append('-');
        }
        else if (key == Key.SLASH)
        {
            value.append('/');
        }
        else if (key == Key.PERIOD)
        {
            value.append('.');
        }

        // numbers are N_
        else if (firstChar == 'N')
        {
            value.append(key.toString().charAt(1));
        }

        //space
        else if (key == Key.SPACE)
        {
            value.append(' ');
        }
        //delete
        else if (key == Key.BACK_SPACE)
        {
            if (value.length() > 0)
            {
                value.deleteCharAt(value.length() - 1);
            }
        }
        //enter
        else if (key == Key.ENTER)
        {
            isActive = false;
            return true;
        }

        return false;
    }
}