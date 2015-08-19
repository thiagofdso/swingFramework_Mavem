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

public class BackgroundPanel extends JPanel {

    private BufferedImage originalImage;
    private BufferedImage scaledImage;
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
    
    public BackgroundPanel(URL imagem) {
        try {
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

//    try {
//        scaledImage = Thumbnails.of(originalImage)
//                .size(this.getSize().width, this.getSize().height)
//                .asBufferedImage();
//    } catch (IOException ex) {
//        Logger.getLogger(BackgroundDesktopPanel.class.getName()).log(Level.SEVERE, null, ex);
//    }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(scaledImage, 0, 0, this.getWidth(), this.getHeight(), null);
    }

}
