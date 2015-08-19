package com.brassoftware.framework.ui.glasspane;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Associated GlassedPane bean
 * <p>
 * The provided GlassedPane component's icon forms part of the WYSIWYG Sapphire
 * set of free icons, available at
 * <a href='http://dryicons.com/free-icons/icons-list/wysiwyg-sapphire/'>dryicons</a>
 * </p>
 *
 * @author coderazzi (lu@coderazzi.net)
 */
public class GlassedPaneBeanInfo extends SimpleBeanInfo {

    private Image image;

    @Override
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor beanDescriptor = new BeanDescriptor(GlassedPane.class, null);
        beanDescriptor.setValue("containerDelegate", "getContentPane");
        return beanDescriptor;
    }

    @Override
    public Image getIcon(int iconKind) {
        if (image == null) {
            image = loadImage("/com/brassoftware/framework/ui/glasspane/glasspane.png");
        }
        return image;
    }

}
