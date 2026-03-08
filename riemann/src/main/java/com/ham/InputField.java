package com.ham;
import codedraw.CodeDraw;
import codedraw.TextFormat;
import codedraw.Palette;

public class InputField
{
    private double[] coord;
    private int width;
    private int height;
    private String value;
    private boolean isActive;
    private CodeDraw cd;

    public InputField(int x, int y, int w, int h, CodeDraw cd)
    {
        coord = new double[]{x, y};
        width = w;
        height = h;
        value = null;
        isActive = false;
        this.cd = cd;
        drawBox();
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }
    public boolean getIsActive()
    {
        return isActive;
    }

    public double[] getCoord()
    {
        return coord;
    }

    //on enter call
    public void setValue(String str)
    {
        value = str;
    }
    public String getValue()
    {
        return value;
    }


    void drawBox()
    {
        cd.setColor(Palette.LIGHT_GRAY);
        cd.fillRectangle(coord[0], coord[1], width, height);
    }

    public boolean contains(double mouseX, double mouseY)
    {
        return (mouseX >= coord[0] && mouseX <= coord[0]+width) && (mouseY >= coord[1] && mouseY <= coord[1]+height);
    }
}