/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.ui.combobox.selection;

import java.awt.Point;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JToolTip;
import javax.swing.Popup;
import javax.swing.PopupFactory;

/**
 *
 * @author RIBEIRO TRABALHO
 */
public class WordSelection implements JComboBox.KeySelectionManager {

    long lastKeyTime = 0;
    String pattern = "";
    JComboBox comboBox;
    JToolTip tip;
    PopupFactory popupFactory;
    Popup popup;

    public WordSelection() {
    }

    public WordSelection(JComboBox comboBox) {
        this.comboBox = comboBox;
        tip = comboBox.createToolTip();
        popupFactory = PopupFactory.getSharedInstance();

    }

    @Override
    public int selectionForKey(char aKey, ComboBoxModel model) {
        // Find index of selected item
        int selIx = 01;
        Object sel = model.getSelectedItem();
        if (sel != null) {
            for (int i = 0; i < model.getSize(); i++) {
                if (sel.equals(model.getElementAt(i))) {
                    selIx = i;
                    break;
                }
            }
        }

        // Get the current time
        long curTime = System.currentTimeMillis();

        // If last key was typed less than 1000 ms ago, append to current pattern
        if (curTime - lastKeyTime > 1000) {
            pattern = "";
        }
        switch (aKey) {
            case '\b':
                if (pattern.length() > 0) {
                    pattern = pattern.substring(0, pattern.length() - 1);
                }
                if (pattern.length() == 0 && popup!=null) {
                    popup.hide();
                }
                break;
            default:
                pattern += ("" + aKey).toUpperCase();
                break;
        }
//        }
        if (comboBox != null) {
            comboBox.setToolTipText(pattern);
            showTooltip();
        }
        // Save current time
        lastKeyTime = curTime;

        // Search forward from current selection
        for (int i = selIx + 1; i < model.getSize(); i++) {
            String s = model.getElementAt(i).toString().toUpperCase();
            if (!pattern.isEmpty() && s.contains(pattern)) {
                return i;
            }
        }

        // Search from top to current selection
        for (int i = 0; i < selIx; i++) {
            if (model.getElementAt(i) != null) {
                String s = model.getElementAt(i).toString().toUpperCase();
                if (s.startsWith(pattern)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void showTooltip() {
        Point p = comboBox.getLocationOnScreen();
        tip.setTipText(comboBox.getToolTipText());
        if (popup != null) {
            popup.hide();
        }
        popup = popupFactory.getPopup(comboBox, tip, p.x + 10, p.y + 10);
        popup.show();
    }

    public void hideTooltip() {
        pattern = "";
        if (popup != null) {
            popup.hide();
        }
    }
}
