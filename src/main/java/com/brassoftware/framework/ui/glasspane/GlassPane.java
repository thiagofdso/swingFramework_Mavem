package com.brassoftware.framework.ui.glasspane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * Simple glass panel: its merely captures all mouse and key events, displaying
 * the background in a specific non opaque color.
 * <br>
 * There are two ways to redefine the default appearance of this glass pane (not
 * including subclassing mechanisms):<ul>
 * <li>Change the default background, by defining the system property
 * "net.coderazzi.glasspane.glasscolor"
 * ({@link GlassPane#GLASS_COLOR_PROPERTY})</li>
 * <li>Change the transparency level, by defining the system property
 * "net.coderazzi.glasspane.glasstransparency"
 * ({@link GlassPane#GLASS_TRANSPARENCY_PROPERTY})</li>
 * </ul>
 *
 * @author coderazzi (lu@coderazzi.net)
 * @version 1.0
 */
public class GlassPane extends JPanel implements MouseListener, KeyListener {

    private static final long serialVersionUID = 5631249361142773510L;

    /**
     * These property can be defined as a system property to specify the color
     * used by default. By default, it corresponds to
     * {@link GlassPane#DEFAULT_GLASS_COLOR}
     */
    public static final String GLASS_COLOR_PROPERTY
            = "com.brassoftware.framework.ui.glasspane.glasscolor";

    /**
     * These property can be defined as a system property to specify the
     * transparency level used by default (integer from 0 to 255). By default,
     * it corresponds to {@link GlassPane#DEFAULT_TRANSPARENCY}
     */
    public static final String GLASS_TRANSPARENCY_PROPERTY
            = "com.brassoftware.framework.ui.glasspane.glasstransparency";

    /**
     * Default glass color (#c0c0c0)
     */
    public static final Color DEFAULT_GLASS_COLOR = new Color(192, 192, 192);

    /**
     * Default transparency level (142)
     */
    public static final int DEFAULT_TRANSPARENCY = 142;

    private Color color;

    public GlassPane() {
        super(null);
        setOpaque(false);
        addMouseListener(getInstance());
        addKeyListener(getInstance());
    }
    
    protected final GlassPane getInstance() {
        return this;
    }

    /**
     * Sets the color associated to this pane
     *
     * @param c
     */
    public void setColor(Color c) {
        color = c;
    }

    /**
     * Returns the color associated to this pane
     *
     * @return
     */
    public Color getColor() {
        if (color == null) {
            color = getDefaultColor();
        }
        return color;
    }

    /**
     * This method can be overridden to define a different default color.
     * <br>
     * By default, it is created out of the properties:      {@link GlassPane#GLASS_COLOR_PROPERTY} & 
     * {@link GlassPane#GLASS_TRANSPARENCY_PROPERTY}
     *
     * @return
     */
    protected Color getDefaultColor() {
        Color base = Color.getColor(GLASS_COLOR_PROPERTY, DEFAULT_GLASS_COLOR);
        int transparency;
        try {
            transparency = Integer.decode(
                    System.getProperty(GLASS_TRANSPARENCY_PROPERTY));
        } catch (Exception ex) {
            transparency = DEFAULT_TRANSPARENCY;
        }
        return new Color(base.getRed(), base.getGreen(), base.getBlue(),
                transparency);
    }

    /**
     * Overrides the default implementation to visualize the defined color
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(getColor());
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Captures this event, doing nothing with it
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Captures this event, doing nothing with it
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Captures this event, doing nothing with it
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Captures this event, doing nothing with it
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Captures this event, doing nothing with it
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Captures this event, doing nothing with it
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Captures this event, doing nothing with it
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Captures this event, doing nothing with it
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
