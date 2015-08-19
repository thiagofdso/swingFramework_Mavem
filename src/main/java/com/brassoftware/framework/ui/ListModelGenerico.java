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

/**
 *
 * @author Casa
 * @param <T>
 */
public class ListModelGenerico<T extends EntidadeBase> extends AbstractListModel<T> {

    private List<T> lista = new ArrayList<>();
    //private T entidade;

    public ListModelGenerico() {
    }

    public List<T> getCopiaLista() {
        return new ArrayList<>(lista);
    }

    public ListModelGenerico(List<T> lista) {
        this.lista = lista;
    }

    public void addComponent(T entidade) {
        lista.add(entidade);
        this.fireContentsChanged(entidade, 0, lista.size() - 1);
    }

    public void removeComponent(T entidade) {
        lista.remove(entidade);
        this.fireContentsChanged(entidade, 0, lista.size() - 1);
    }

    @Override
    public int getSize() {
        return lista.size();
    }

    @Override
    public T getElementAt(int index) {
        return lista.get(index);
    }

}
