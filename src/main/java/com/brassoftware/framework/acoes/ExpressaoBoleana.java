package com.brassoftware.framework.acoes;

/**
 * Interface que define uma operação condicional (<code>boolean</code>), que
 * será usada em conjunto com uma <code>AcaoCondicional</code> para determinar
 * se uma ação será ou não executada, geralmente será utilizado um
 * <code>Validador</code> para determinar essa condição.
 *
 * @version 1.0.0
 *
 * @author Karlay
 */
public interface ExpressaoBoleana {

    /**
     * @return o resultado de uma condição <code>boolean</code> qualquer.
     */
    public boolean condicional();
    
}
