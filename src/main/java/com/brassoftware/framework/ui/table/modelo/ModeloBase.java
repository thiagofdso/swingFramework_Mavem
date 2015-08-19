package com.brassoftware.framework.ui.table.modelo;

import com.brassoftware.framework.modelos.EntidadeBase;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Karlay
 * @param <T>
 */
public abstract class ModeloBase<T extends EntidadeBase> extends AbstractTableModel {

    protected List<T> lista = new ArrayList<>();

    @Override
    public int getRowCount() {
        return lista == null? 0 : lista.size();
    }

    public List<T> getListaOriginal() {
        return lista;
    }

    public void recarregar(List<T> lista) {
        this.lista = lista;
        /**
         * O <code>SwingUtilities.invokeLater()</code> garante que esse evento
         * será executado dentro de uma fila de espera do
         * <strong>Thread</strong> da inteface gráfica, isso garante que esse
         * evento seja executado dentro de uma
         * <strong>Thread-Safe</strong> ou seja processamento seguro.
         */
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                fireTableDataChanged();
            }
        });
    }

    /**
     * Função que devolve uma cópia da lista
     *
     * @return Cópia da lista
     */
    public List<T> getLista() {
        return lista == null ? new ArrayList<T>() : new ArrayList<>(lista);
    }

    public int getIndexOf(T entidade) {
        return lista == null? -1 : lista.indexOf(entidade);
    }

    public T getEntityAt(int index) {
        if (lista != null &&!lista.isEmpty()) {
            return lista.get(index);
        } else {
            return null;
        }
    }

    public void clear() {
        lista.clear();
    }

    public void add(List<T> lista) {
        final int first = this.lista.size();
        final int last = first + lista.size() - 1;
        this.lista.addAll(lista);
        /**
         * O <code>SwingUtilities.invokeLater()</code> garante que esse evento
         * será executado dentro de uma fila de espera do
         * <strong>Thread</strong> da inteface gráfica, isso garante que esse
         * evento seja executado dentro de uma
         * <strong>Thread-Safe</strong> ou seja processamento seguro.
         */
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                fireTableRowsInserted(first, last);
            }
        });
    }

    public void add(T newCidades) {
        final int index = lista.size();
        lista.add(newCidades);
        /**
         * O <code>SwingUtilities.invokeLater()</code> garante que esse evento
         * será executado dentro de uma fila de espera do
         * <strong>Thread</strong> da inteface gráfica, isso garante que esse
         * evento seja executado dentro de uma
         * <strong>Thread-Safe</strong> ou seja processamento seguro.
         */
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                fireTableRowsInserted(index, index);
            }
        });
    }

}
