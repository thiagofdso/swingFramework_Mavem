package com.brassoftware.framework.validadores;


import java.util.List;

/**
 * Define um contrato para validar o estado (dados) de um objeto(Bean), que
 * pode ser uma entidade persistente.
 *
 * @version 1.0.0
 * @author Karlay
 * 
 * @param <T> Indica o <code>Objeto</code> a ser validado.
 */
public interface Validador<T> {

    /**
     * Método que aplica o mecanismo de validação a objeto(Bean).
     *
     * @param object Objeto(Bean) a ser validado.
     * @return String <i>vazia</i>, caso não exista problemas de validação. Ou
     * retorna uma string com as mensagens de validação.
     */
    public String validar(T object);
    
    public boolean isValidar(T object);    
    
}
