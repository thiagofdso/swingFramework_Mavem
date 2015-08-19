/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.utilidades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

/**
 *
 * @author Casa
 */
public final class TextureFactory {

    private static final Color DEFAULT_COLOR = new Color(100, 100, 100, 100);

    private TextureFactory() { /* Singleton */ }

    public static TexturePaint createCheckerTexture(int cs, Color color) {
        int size = cs * cs;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setPaint(color);
        g2.fillRect(0, 0, size, size);
        for (int i = 0; i * cs < size; i++) {
            for (int j = 0; j * cs < size; j++) {
                if ((i + j) % 2 == 0) {
                    g2.fillRect(i * cs, j * cs, cs, cs);
                }
            }
        }
        g2.dispose();
        return new TexturePaint(img, new Rectangle(0, 0, size, size));
    }

    public static TexturePaint createCheckerTexture(int cs) {
        return createCheckerTexture(cs, DEFAULT_COLOR);
    }
}
