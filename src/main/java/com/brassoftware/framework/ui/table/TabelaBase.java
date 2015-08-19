/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.ui.table;

import com.brassoftware.framework.ui.table.modelo.ModeloBase;
import com.brassoftware.framework.modelos.EntidadeBase;
import com.brassoftware.framework.ui.filters.gui.TableFilterHeader;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.sort.TableSortController;

/**
 *
 * @author Karlay
 * @param <Entidade>
 * @param <Modelo>
 */
public abstract class TabelaBase<Entidade extends EntidadeBase, Modelo extends ModeloBase> extends JXTable {

    private TableFilterHeader tableFilterHeader;
    private boolean filterHeaderEnable;
    private final Modelo modelo;
    private TableSortController<TableModel> sort;

    public TabelaBase(Modelo modelo) {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.modelo = modelo;
        setModel(this.modelo);
        sort = (TableSortController<TableModel>) getRowSorter();
    }

    public void setFilterHeaderEnable(boolean filterHeaderEnable) {
        if (filterHeaderEnable && !this.filterHeaderEnable && tableFilterHeader == null) {
            tableFilterHeader = new TableFilterHeader(this);
        }
        if (tableFilterHeader != null) {
            tableFilterHeader.setVisible(filterHeaderEnable);
            tableFilterHeader.setEnabled(filterHeaderEnable);
        }
        this.filterHeaderEnable = filterHeaderEnable;
    }

    public TableFilterHeader getTableFilterHeader() {
        return tableFilterHeader;
    }

    public boolean isFilterHeaderEnable() {
        return filterHeaderEnable;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component componente = super.prepareRenderer(renderer, row, column);
        if (componente instanceof JLabel) {
            JLabel label = (JLabel) componente;
            label.setToolTipText(label.getText());
        }
        return componente;
    }

    public Entidade getEntidadeSelecionada() {
        int i = getSelectedRow();
        if (i < 0) {
            return null;
        }
        return (Entidade) modelo.getEntityAt(sort.convertRowIndexToModel(i));
    }
    public Entidade getEntidadeAt(int index){
        if(index<modelo.getListaOriginal().size()&&index>-1)
        return (Entidade) modelo.getEntityAt(index);
        else return null;
    }
    
    public int getIndexOf(Entidade entidade){
        return modelo.getIndexOf(entidade);
    }

    public List<Entidade> getEntidadesSelecionadas() {
        int[] rowsIndex = this.getSelectedRows();
        if (rowsIndex.length > 0) {
            List<Entidade> lista = new ArrayList<>();
            for (int i = 0; i < rowsIndex.length; i++) {
                int index = sort.convertRowIndexToModel(rowsIndex[i]);
                lista.add((Entidade) modelo.getEntityAt(index));
            }
            return lista;
        }
        return null;
    }

    /**
     * Função que retorna uma cópia da lista da tebela
     *
     * @return Cópia da lista da tabela
     */
    public List<Entidade> getCopiaLista() {
        return modelo.getLista();
    }

    public void recarrega(List<Entidade> lista) {
        modelo.recarregar(lista);
    }

    public static RowFilter<TableModel, Integer> makeRowFilter(final List<String> tokens, final Integer... cols) {
        return new RowFilter<TableModel, Integer>() {
            @Override
            public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> en) {
                for (String tok : tokens) {
                    for (int i = 0; i < cols.length; ++i) {
                        String val = en.getStringValue(cols[i]).toLowerCase();
                        if (val.contains(tok)) {
                            break;
                        } else if (i == cols.length - 1) {
                            return false;
                        }
                    }
                }
                return true;
            }
        };
    }

}
