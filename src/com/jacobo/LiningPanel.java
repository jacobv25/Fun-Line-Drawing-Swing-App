package com.jacobo;

import javax.swing.*;
import java.awt.*;

public class LiningPanel extends JPanel{

    protected int width;                // width of viewing area in pixels
    protected int height;               // height of viewing area in pixels
    protected int thickness_multiplier = 100; //At the moment 100 works okay.
    protected double thickness = 0;      // variable that will be used in sin() to create thickness pulse
    protected Dimension size;           // size of viewing area
    protected Image image;              // off-screen image
    protected Graphics2D offScreen;     // off-screen graphics
    public static int iterations = 0;   // index to keep track of number of intervals (lines)
    public static double lines = 100;   //number of lines in each "quarter". More lines = more detail


    public LiningPanel(int aWidth, int aHeight)
    {
        // Set the width and height and size
        width = aWidth;
        height = aHeight;
        setSize(width, height);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Get the size of the viewing area
        size = this.getSize();

        // Create the off-screen image buffer if it is the first time
        if (image == null) {
            System.out.println("create image");
            image = createImage(size.width, size.height);
            offScreen = (Graphics2D) image.getGraphics();
        }

        int w = getWidth();
        int h = getHeight();

        iterations++;
        thickness++;

        int w2 = (int)((iterations /lines)*w);
        int h2 = (int)((iterations /lines)*h);

        drawLine( 0, h2, w2, h,LineDrawingDriver.isValentinesDay,LineDrawingDriver.isPulsing);
        drawLine( w, h2, w2,0,LineDrawingDriver.isValentinesDay,LineDrawingDriver.isPulsing);

        int w3 = (int) ((1.0 - (iterations /lines)) * w);
        int h3 = (int) ((1.0 - (iterations /lines)) * h);

        drawLine( w, h2, w3, h,LineDrawingDriver.isValentinesDay,LineDrawingDriver.isPulsing);
        drawLine( 0, h3, w2, 0,LineDrawingDriver.isValentinesDay,LineDrawingDriver.isPulsing);

        g.drawImage(image, 0, 0, this);
    }

    /*****************************************************
     DESCRIPTION: Draws a line to the offscreen buffer.
     PARAMETERS:
     x1 - starting x
     y1 - starting y
     x2 - ending x
     y2 - ending y
     isValentinesDay - colors are random or pink/red
     isPulsing - lines "pulse" or not
     ******************************************************/
    private void drawLine(int x1, int y1, int x2, int y2, boolean isValentinesDay, boolean isPulsing)
    {
        if(isPulsing)
        {
            //Set the thickness using sin wave
            double d = Math.sin(thickness);
            int i = (int)(d * thickness_multiplier);
            i = Math.abs(i);
            BasicStroke thick = new BasicStroke(i);
            offScreen.setStroke(thick);
        }
        if(isValentinesDay)
        {
            offScreen.setColor(getPinksAndReds());
        }
        else
        {
            offScreen.setColor(getRandomColor());
        }
//        System.out.println("drawing line");
        offScreen.drawLine(x1, y1, x2, y2);
    }

    /******************************************************
     Returns a new Color object initialized with random values
     ******************************************************/
    private Color getRandomColor()
    {
        int min = 0;
        int max = 255;

        int r = (int)(Math.random() * (max - min + 1) + min);
        int b = (int)(Math.random() * (max - min + 1) + min);
        int g = (int)(Math.random() * (max - min + 1) + min);

        return new Color(r, b, g );
    }
    /******************************************************
     Returns a new Pink/Red Color
     ******************************************************/
    private Color getPinksAndReds()
    {
        int i;

        int min = 1;
        int max = 5;

        i = (int)(Math.random() * (max - min + 1) + min);

        switch(i)
        {
            case 1:
                return new Color(255,0,0);
            //break;
            case 2:
                return new Color(255,59,59);
            //break;
            case 3:
                return new Color(255,93,93);
            //break;
            case 4:
                return new Color(255,147,147);
            //break;
            case 5:
                return new Color(255,175,175);
            //break;
        }
        return Color.BLACK; //YOU SHOULD NOT BE SEEING BLACK!
    }

}
