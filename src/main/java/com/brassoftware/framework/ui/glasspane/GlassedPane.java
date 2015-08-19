package com.brassoftware.framework.ui.glasspane;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Component that supports two layers: the basic, normal content, plus a glass
 * pane that covers it.
 * <br>
 * Content is added on the normal layer, using operations such as
 * <pre>
 * glasspane.getContentPane().setLayout(new BorderLayout());
 * glasspane.getContentPane().add(new JTable());
 * </pre>
 *
 * To hide the normal content pane and visualize the glass pane -which should
 * block also any interaction with the content pane-, it is enough to do
 * <pre>
 * glassPane.getGlassPane().setVisible(true);
 * </pre>
 *
 * By default, the content pane is a {@link JPanel}.
 * <br>
 * Also by default, the glass pane is a {@link GlassPane}. However, any other
 * component can be supplied by overriding the system property
 * "net.coderazzi.glasspane.glassclass"
 * ({@link GlassedPane#GLASS_CLASS_PROPERTY})
 * <br>
 * For example, to use by default the {@link LabelGlassPane}, it is possible to
 * write:
 * <pre>
 * System.setProperty(GlassedPane.GLASS_CLASS_PROPERTY,
 *                    LabelGlassPane.class.getName());
 * </pre>
 *
 * @author coderazzi (lu@coderazzi.net)
 * @version 1.0
 */
public class GlassedPane extends JComponent {

    private static final long serialVersionUID = -7261168172730829946L;

    /**
     * These property can be defined as a system property to specify the class
     * used as default glass pane. By default, it corresponds to
     * {@link GlassPane}
     */
    public static final String GLASS_CLASS_PROPERTY
            = "com.brassoftware.framework.ui.glasspane.glassclass";

    private Container contentPane;

    private Component glassPane;

    /**
     * Returns the content pane. It is, by default, a {@link JPanel}
     *
     * @return
     */
    public Container getContentPane() {
        if (contentPane == null) {
            setContentPane(new JPanel());
        }
        return contentPane;
    }

    /**
     * <p>
     * Returns the glass pane.</p> The default glass pane can be redefined by
     * overriding the method {@link GlassedPane#createDefaultGlassPane()}, or by
     * simply defining as system property the property
     * {@link GlassedPane#GLASS_CLASS_PROPERTY}.
     *
     * @return
     */
    public Component getGlassPane() {
        if (glassPane == null) {
            setGlassPane(createDefaultGlassPane());
        }
        return glassPane;
    }

    /**
     * Defines the component to be used as glass pane
     *
     * @param component
     */
    public void setGlassPane(Component component) {
        boolean visible = (glassPane == null) ? false : glassPane.isVisible();
        glassPane = set(component, glassPane, 0);
        glassPane.setVisible(visible);
        invalidate();
    }

    /**
     * Defines the component to be used as content pane
     *
     * @param component
     */
    public void setContentPane(Container component) {
        contentPane = (Container) set(component, contentPane, -1);
        invalidate();
    }

    /**
     * Defines the default glass pane, with by default is a {@link GlassPane}
     * instance, unless the system properties include a valid definition for the
     * property {@link GlassedPane#GLASS_CLASS_PROPERTY}.
     *
     * @return
     */
    protected Component createDefaultGlassPane() {
        String def = System.getProperty(GLASS_CLASS_PROPERTY);
        if (def != null) {
            try {
                return (Component) Class.forName(def).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException nex) {
            }
        }
        return new GlassPane();
    }

    private Component set(Component component, Component old, int index) {
        if (old != null) {
            remove(old);
        }
        if (component != null) {
            add(component, index);
            propagateSize(component, getWidth(), getHeight());
        }
        return component;
    }

    /**
     * Overrides the parent definition to d
     *
     * @return isable optimized drawing when the glass panel is visible
     */
    @Override
    public boolean isOptimizedDrawingEnabled() {
        return glassPane == null;
    }

    /**
     * Overrides the parent definition to make the content and glass panes
     * occupy the whole component area.
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        propagateSize(contentPane, width, height);
        propagateSize(glassPane, width, height);
    }

    private void propagateSize(Component target, int width, int height) {
        if (target != null) {
            Insets insets = getInsets();
            width -= insets.left + insets.right;
            height -= insets.top + insets.bottom;
            target.setBounds(insets.left, insets.top, width, height);
        }
    }

    /**
     * Overrides the default implementation, to return the preferred size of the
     * content panel.
     *
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return addInsets((contentPane == null)
                ? new Dimension(0, 0) : getContentPane().getPreferredSize());
    }

    /**
     * Overrides the default implementation, to return the minimum size of the
     * content panel.
     *
     * @return
     */
    @Override
    public Dimension getMinimumSize() {
        return addInsets((contentPane == null)
                ? new Dimension(0, 0) : getContentPane().getMinimumSize());
    }

    private Dimension addInsets(Dimension s) {
        Insets insets = getInsets();
        return new Dimension(s.width + insets.left + insets.right,
                s.height + insets.top + insets.bottom);
    }

}
