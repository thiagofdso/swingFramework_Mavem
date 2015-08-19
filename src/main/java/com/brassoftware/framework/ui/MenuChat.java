/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Casa
 */
public class MenuChat extends JMenu implements ActionListener {

    public MenuChat(List<String> atendentes) {
        super("Chat");
        setIcon(new ImageIcon(getClass().getResource("/com/brassoftware/framework/imagens/icone 16.png")));
        removeAll();
        if (!atendentes.isEmpty()) {
            for (String atendente : atendentes) {
                JMenuItem item = new JMenuItem(atendente);
                item.addActionListener(getInstance());
                add(item);
            }
        }
    }

    protected final MenuChat getInstance() {
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JOptionPane.showMessageDialog(null, ae.getSource());
    }

}
