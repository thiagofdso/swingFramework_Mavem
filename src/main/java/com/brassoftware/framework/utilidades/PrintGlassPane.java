/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.utilidades;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.TexturePaint;
import javax.swing.JPanel;
import javax.swing.JRootPane;

/**
 *
 * @author Casa
 */
public class PrintGlassPane extends JPanel {

    private static final TexturePaint TEXTURE = TextureFactory.createCheckerTexture(4);

    public PrintGlassPane() {
        super((LayoutManager) null);
    }

    @Override
    public void setVisible(boolean isVisible) {
        boolean oldVisible = isVisible();
        super.setVisible(isVisible);
        JRootPane rootPane = getRootPane();
        if (rootPane != null && isVisible() != oldVisible) {
            rootPane.getLayeredPane().setVisible(!isVisible);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        JRootPane rootPane = getRootPane();
        if (rootPane != null) {
            // http://weblogs.java.net/blog/alexfromsun/archive/2008/01/disabling_swing.html
            // it is important to call print() instead of paint() here
            // because print() doesn't affect the frame's double buffer
            rootPane.getLayeredPane().print(g);
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(TEXTURE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}
