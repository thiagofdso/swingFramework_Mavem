/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.ui;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.plaf.LayerUI;

/**
 *
 * @author karlay
 */
public class CarregandoUI extends LayerUI<JComponent>{
    private static final long serialVersionUID = 4854275430063729166L;
    private boolean rodando = false;
    private String mensagem;
    
    @Override
    public void paint(Graphics g, JComponent c) {
        // Paint the view.
        super.paint(g, c);
        
        if (!rodando) {
            c.setCursor(null);
            return;
        }
        c.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        
        int w = c.getWidth();
        int h = c.getHeight();        

        Graphics2D g2 = (Graphics2D) g.create();

        // Gray it out.
        Composite urComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .2f));
        g2.fillRect(0, 0, w, h);
        g2.setComposite(urComposite);
 
        FontMetrics fontMetrics = g2.getFontMetrics(c.getFont());
        int fh = fontMetrics.getHeight();
        int fw = fontMetrics.stringWidth(mensagem);
        g2.setPaint(Color.white);
        g2.fillRect((w/2) - (fw/2) -5, (h/2) - (fh/2), fw + 10, fh + 6);
        g2.setPaint(Color.DARK_GRAY);
        g2.drawString(mensagem, (w/2) - (fw/2), (h/2) + (fh/2));

        g2.dispose();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        ((JLayer) c).setLayerEventMask(
                AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK
                | AWTEvent.MOUSE_WHEEL_EVENT_MASK | AWTEvent.KEY_EVENT_MASK
                | AWTEvent.FOCUS_EVENT_MASK | AWTEvent.COMPONENT_EVENT_MASK);    
    }

    @Override
    public void uninstallUI(JComponent c) {
        ((JLayer) c).setLayerEventMask(0);
        super.uninstallUI(c);
    }  
    
    @Override
    public void eventDispatched(AWTEvent e, JLayer<? extends JComponent> l) {
        if (rodando && e instanceof InputEvent) {
            ((InputEvent) e).consume();
        }
    }    
    public void iniciar(String mensagem, boolean valor) {
        this.mensagem = mensagem;
        rodando = valor;
    }
}
