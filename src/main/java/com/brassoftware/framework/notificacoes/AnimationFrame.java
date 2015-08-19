/**
 * <b>AnimationFrame.java</b>: fornece uma enumeração para o processo de
 * animação. Essa enumeração é usada para definir as ações referentes ao
 * processo de animação de uma notificação.
 */
package com.brassoftware.framework.notificacoes;

/**
 * Oferece uma enumeração para o processo de animação. Essa enumeração consiste
 * basicamente em três etapas, definidas posteriormente a documentação.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.3
 * @since 1.0
 */
public enum AnimationFrame {

    /**
     * Esta é a primeira fase da animação. A janela está sendo animado,
     * movendo-se a sua posição padrão.
     */
    ONSHOW,
    /**
     * Esta é a segunda etapa da animação. Não há nenhuma animação depois de
     * tudo, uma vez que a janela está na sua posição predefinida.
     */
    ONDISPLAY,
    /**
     * Esta é a última fase da animação. A janela agora está sendo animada,
     * movendo-se para fora da tela.
     */
    ONCLOSE;
    
}
