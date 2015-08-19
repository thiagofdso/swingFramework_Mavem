/**
 * <b>WindowType.java</b>: fornece uma enumeração para o tipo de janela na tela.
 * Essa enumeração é usada para definir o tipo de objeto janela é atribuído
 * atualmente à notificação.
 */
package com.brassoftware.framework.notificacoes;

/**
 * Oferece uma enumeração para o tipo de janela na tela. Essa enumeração
 * consiste basicamente em dois estados, definidos mais tarde sobre a
 * documentação.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.3
 * @since 1.1
 */
public enum WindowType {

    /**
     * É atribuido a janela um objeto <code>javax.swing.JFrame</code>.
     */
    JFRAME,
    /**
     * É atribuido a janela um objeto <code>javax.swing.JWindow</code>.
     */
    JWINDOW;
    
}
