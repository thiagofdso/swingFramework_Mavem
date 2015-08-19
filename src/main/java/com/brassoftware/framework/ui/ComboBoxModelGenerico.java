/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.ui;

import com.brassoftware.framework.modelos.EntidadeBase;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Karlay
 * @param <T>
 */
public class ComboBoxModelGenerico<T extends EntidadeBase> extends AbstractListModel<T> implements ComboBoxModel<T> {

    private final List<T> lista;
    private T entidade;

    public ComboBoxModelGenerico() {
        this.lista = new ArrayList<>();
    }

    public ComboBoxModelGenerico(T entidade) {
        this.lista = new ArrayList<>();
        this.lista.add(entidade);
    }

    public ComboBoxModelGenerico(List<T> lista) {
        this.lista = lista;
    }

    public List<T> getLista() {
        return lista;
    }

    /**
     * Retorna a quantidade de itens da lista
     *
     * @return Inteiro com o total de itens
     */
    @Override
    public int getSize() {
        return lista.size();
    }

    /**
     * Recebe um inteiro como parametro que será usado como índice para retornar
     * um elemento da lista
     *
     * @param index Índece de um elemento da lista
     * @return Um Cidade da lista
     */
    @Override
    public T getElementAt(int index) {
        return lista.get(index);
    }

    /**
     * Este método é usado pelo próprio componente JComboBox para setar
     * (selecionado) o objeto que está selecionado.
     *
     * @param anItem Objeto a ser seleciona no JComboBox
     */
    @Override
    public void setSelectedItem(Object anItem) {
        entidade = (T) anItem;
    }

    /**
     * Este método é usado pelo próprio componente JComboBox para retornar o
     * objeto que está selecionado.
     *
     * @return O objeto selecionado no JComboBox
     */
    @Override
    public Object getSelectedItem() {
        return entidade;
    }

}
