package com.brassoftware.framework.eventos;

/**
 * Define um componente básico como estrutura de evento.
 *
 * <p>
 * Um evento é um objeto que está relacionado a alguma ação no sistema, que
 * normalmente ocasiona em um reação na interface gráfica.
 * </p>
 *
 * <p>
 * O <code>EventoBase</code>, quando gerado deve ser encaminhado a algum
 * <code>EventoBaseOuvinte</code>. Ambos os componentes implementam o design
 * pattern <strong>Observer</strong>.
 * </p>
 *
 * <p>
 * <code>EventoBase</code> atua como <i>observado</i>.</p>
 * 
 * @version 1.0.0
 *
 * @author Karlay
 *
 * @param <T> Um objeto relacionado a geração do evento, geralmente usado para
 * ser modificado ou usado de alguma forma.
 *
 * @see Controle
 */
public abstract class EventoBase<T> {

    /* Objeto que será utilizado quando ocorrer um evento no sistema,
     * esse objeto é opicinal. */
    private final T alvo;

    public EventoBase(T alvo) {
        this.alvo = alvo;
    }

    /**
     * Método que rotorna o objeto que gerador do evento.
     * @return Objeto gerador do eveno.
     */
    public T getAlvo() {
        return alvo;
    }

}
