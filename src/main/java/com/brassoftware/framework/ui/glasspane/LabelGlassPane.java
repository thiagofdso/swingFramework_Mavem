package com.brassoftware.framework.ui.glasspane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Specific glass pane that extends the basic {@link GlassPane} implementation,
 * placing a {@link JLabel} centered on the pane.
 * <br>
 * It is possible to define the text and icon in the message, and the default
 * icon can be provided by default with the system property
 * "net.coderazzi.glasspane.labelicon"
 * {@link LabelGlassPane#GLASS_MESSAGE_ICON_PROPERTY}
 *
 * @author coderazzi (lu@coderazzi.net)
 * @version 1.0
 */
public class LabelGlassPane extends GlassPane {

    private static final long serialVersionUID = -7809840802179546704L;

    /**
     * These property can be defined as a system property to specify the icon
     * used by default in the {@link JLabel}. By default, ni icon is used
     */
    public static final String GLASS_MESSAGE_ICON_PROPERTY
            = "com.brassoftware.framework.ui.glasspane.labelicon";

    private JLabel label;

    public LabelGlassPane() {
        JLabel newLabel = new JLabel();
        String icon = System.getProperty(GLASS_MESSAGE_ICON_PROPERTY);
        if (icon != null) {
            Image image = new GlassedPaneBeanInfo().loadImage(icon);
            if (image != null) {
                newLabel.setIcon(new ImageIcon(image));
            }
        }
        newLabel.setOpaque(true);
        setLabel(newLabel);
        setOpaqueLabelBackground(getColor());
    }

    /**
     * If the user updates the label's text, icon, position, etc, this method
     * should be called to recenter the label in the pane
     */
    public void recenter() {
        Dimension size = getSize();
        Dimension psize = label.getPreferredSize();
        label.setBounds((size.width - psize.width) / 2,
                (size.height - psize.height) / 2,
                psize.width,
                psize.height);
    }

    /**
     * Returns the label in the pane
     *
     * @return
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Sets the label to display in the pane, which is automatically centered.
     *
     * @param label
     */
    public final void setLabel(JLabel label) {
        if (this.label != null) {
            remove(label);
        }
        this.label = label;
        add(label);
        recenter();
    }

    /**
     * Overrides the {@link GlassPane#setColor(Color)} implementation, to setup
     * the color (without transparency) as the label background.
     */
    @Override
    public void setColor(Color c) {
        if (label != null) {
            setOpaqueLabelBackground(c);
        }
        super.setColor(c);
    }

    private void setOpaqueLabelBackground(Color bg) {
        label.setBackground(new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), 0));
    }

    /**
     * Overrides the basic implementation, to automatically center the label in
     * the pane.
     */
    @Override
    public void setBounds(int x,
            int y,
            int width,
            int height) {
        super.setBounds(x, y, width, height);
        if (label != null) {
            recenter();
        }
    }

}
