/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.ui.table.renderer;

import com.jidesoft.swing.StyleRange;
import com.jidesoft.swing.StyledLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Suany
 */
public class HighlightCellRenderer extends StyledLabel implements TableCellRenderer {

    private List<String> termos = new ArrayList<>();
    private Color[] cores = new Color[]{Color.CYAN, Color.YELLOW, Color.PINK};
    private final List<Integer> colunas;

    public HighlightCellRenderer(List<Integer> colunas) {
        this.colunas = colunas;
        setBorder(new EmptyBorder(1, 1, 1, 1));
        setOpaque(true);
    }

    public void setTermos(List<String> termos) {
        this.termos = termos;
    }

    public void limparTermos() {
        this.termos.clear();
    }

    public void setCores(Color... cores) {
        this.cores = cores;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String texto = value != null ? value.toString() : "";
        setText(texto);
        clearStyleRanges();
        final Color fg = isSelected ? table.getSelectionForeground() : table.getForeground();
        final Color bg = isSelected ? table.getSelectionBackground() : table.getBackground();
        setForeground(fg);        
        setBackground(bg);
        if (colunas.contains(column)) {
            String textLc = texto.toLowerCase();
            for (int i=0; i < termos.size(); ++i) {
                String termo = termos.get(i);
                int offSet = textLc.indexOf(termo);
                while (offSet != -1) {
                    addStyleRange(new StyleRange(offSet, termo.length(), getFont().getStyle(), isSelected ? bg : getForeground(), isSelected ? fg : cores[i % cores.length], 0));
                    offSet = textLc.indexOf(termo, offSet + 1);
                }
            }
        }
        configurarBorda(table, isSelected, hasFocus, row, column);
        return this;
    }
    
    private void configurarBorda(JTable tbl, boolean sel, boolean temFoco, int linha, int coluna) {
        // copiado a partir DefaultTableCellRenderer
        if (temFoco) {
            Border border = null;
            if (sel) {
                border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");                
//                border = DefaultLookup.getBorder(
//                        this, ui, "Table.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = UIManager.getBorder("Table.focusCellHighlightBorder"); 
//                border = DefaultLookup.getBorder(
//                        this, ui, "Table.focusCellHighlightBorder");
            }
            setBorder(border);
            if (!sel && tbl.isCellEditable(linha, coluna)) {
                Color color;
                color = UIManager.getColor("Table.focusCellForeground");
//                color = DefaultLookup.getColor(
//                        this, ui, "Table.focusCellForeground");
                if (color != null) {
                    super.setForeground(color);
                }
                color = UIManager.getColor("Table.focusCellBackground");
//                color = DefaultLookup.getColor(
//                        this, ui, "Table.focusCellBackground");
                if (color != null) {
                    super.setBackground(color);
                }
            }
        } else {
            setBorder(getNoFocusBorder());
        }
    }

    // copiado a partir DefaultTableCellRenderer
    static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;

    private Border getNoFocusBorder() {
        Border border = UIManager.getBorder("Table.cellNoFocusBorder");
//        Border border = DefaultLookup.getBorder(
//                this, ui, "Table.cellNoFocusBorder");
        if (System.getSecurityManager() != null) {
            if (border != null) {
                return border;
            }
            return SAFE_NO_FOCUS_BORDER;
        } else if (border != null) {
            if (noFocusBorder == null
                    || noFocusBorder == DEFAULT_NO_FOCUS_BORDER) {
                return border;
            }
        }
        return noFocusBorder;
    }

    // Sobrescrever por razões de desempenho
    @Override
    public void invalidate() {
    }

    @Override
    public void validate() {
    }

    @Override
    public void revalidate() {
    }

    @Override
    public void repaint(long tm, int x, int y, int w, int h) {
    }

    @Override
    public void repaint(Rectangle r) {
    }

    @Override
    public void repaint() {
    }

}
