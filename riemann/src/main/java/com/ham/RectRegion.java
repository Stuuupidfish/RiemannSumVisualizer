package com.ham;

import codedraw.CodeDraw;
import codedraw.Palette;

public class RectRegion {
    protected double[] coord;
    protected double width;
    protected double height;
    protected CodeDraw cd;

    public RectRegion(double x, double y, double width, double height, CodeDraw cd)
    {
        this.coord = new double[]{x, y};
        this.width = width;
        this.height = height;
        this.cd = cd;
        drawBox();
    }

    public boolean contains(double mouseX, double mouseY)
    {
        return (mouseX >= coord[0] && mouseX <= coord[0]+width) && (mouseY >= coord[1] && mouseY <= coord[1]+height);
    }

    public void drawBox()
    {
        cd.setColor(Palette.LIGHT_GRAY);
        cd.fillRectangle(coord[0], coord[1], width, height);
    }

    public double[] getCoord()
    {
        return coord;
    }
}
