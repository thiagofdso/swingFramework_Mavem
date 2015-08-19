/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.ui;

/**
 *
 * @author RIBEIRO TRABALHO
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BackgroundDesktopPanel extends JDesktopPane {

    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    private final int initialWidth;
    private final int initialHeight;
    class ResizerListener implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            resize();
        }

        @Override
        public void componentHidden(ComponentEvent e) {
        }

        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
        }
    }
    
    public BackgroundDesktopPanel(URL imagem) {
        initialHeight = 690;
        initialWidth = 911;

        try {
            scaledImage= ImageIO.read(imagem);
            originalImage = ImageIO.read(imagem);
        AffineTransform at = new AffineTransform();
        at.scale(getWidth(), getHeight());

        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        originalImage = scaleOp.filter(originalImage, null);
        } catch (Exception e) {
        }

        addComponentListener(new ResizerListener());
    }

    public void resize() {
        double widthScaleFactor = getWidth() / (double) originalImage.getWidth();
        double heightScaleFactor = getHeight() / (double) originalImage.getHeight();
        double scaleFactor = (widthScaleFactor > heightScaleFactor) ? heightScaleFactor : widthScaleFactor;

        AffineTransform at = new AffineTransform();
        at.scale(scaleFactor, scaleFactor);

        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        scaledImage = scaleOp.filter(originalImage, null);

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        g.drawImage(scaledImage,          //imagem
                this.getWidth()/2-scaledImage.getWidth()/4-scaledImage.getWidth()/16,    //x
                this.getHeight()/2-scaledImage.getHeight()/4,  //y
                initialWidth/2,                         //width
                initialHeight/2,                        //heigth
                null);
    }

}
