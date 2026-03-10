package com.ham;

import java.awt.Font;

import codedraw.CodeDraw;
import codedraw.Palette;
import codedraw.TextFormat;

public class ApproxSelector {
    private final String[] APPROX_TYPES = {"left", "mid", "right"};
    private final double gap = 5;

    private int selectedIndex;
    private RectRegion leftButton;
    private RectRegion rightButton;
    private CodeDraw cd;
    private TextFormat tf;

    private double centerWidth;
    private double height;
    private double x;
    private double y;
    private double btnWidth;


    // [<]  [  mid  ]  [>]
    public ApproxSelector(double x, double y, double btnWidth, double centerWidth, double height, CodeDraw cd)
    {
        this.cd = cd;
        this.tf = cd.getTextFormat();
        selectedIndex = 1; //defaults to "mid"
        this.centerWidth = centerWidth;
        this.height = height;
        this.x = x;
        this.y = y;
        this.btnWidth = btnWidth;
        leftButton  = new RectRegion(x, y, btnWidth, height, cd);
        rightButton = new RectRegion(x + btnWidth + gap + centerWidth + gap, y, btnWidth, height, cd);
    }

    public String getApproxType()
    {
        return APPROX_TYPES[selectedIndex];
    }
    public RectRegion getLeftButton()
    {
        return leftButton;
    }
    public RectRegion getRightButton()
    {
        return rightButton;
    }

    public String handleClick(double mouseX, double mouseY)
    {
        if (leftButton.contains(mouseX, mouseY))
        {
            selectedIndex = (selectedIndex - 1 + APPROX_TYPES.length) % APPROX_TYPES.length;
        }
        else if (rightButton.contains(mouseX, mouseY))
        {
            selectedIndex = (selectedIndex + 1) % APPROX_TYPES.length;
        }
        else
        {
            return null; //no change
        }
        return APPROX_TYPES[selectedIndex];
    }

    public void draw()
    {
        leftButton.drawBox(); // [<]
        rightButton.drawBox(); // [>]
        
        double centerX = x + btnWidth + gap;
        cd.fillRectangle(centerX, y, centerWidth, height);
        
        cd.setColor(Palette.DARK_BLUE);
        tf.setFontSize(20);
        tf.setFontName(Font.MONOSPACED);
        tf.setBold(true);

        String label = APPROX_TYPES[selectedIndex];
        double approxCharWidth = 12.0; // monospaced text at this font size
        double labelX = centerX + (centerWidth - label.length() * approxCharWidth) / 2.0;
        
        cd.drawText(labelX, y + height/2 - 10, label);
        cd.drawText(leftButton.getCoord()[0] + 10, y + height/2 - 10, "<");
        cd.drawText(rightButton.getCoord()[0] + 10, y + height/2 - 10, ">");
    }
}
