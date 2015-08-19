package com.brassoftware.framework.eventos;

/**
 * Contrato para componentes com a capacidade de definir tratamento adequado
 * para um evento.
 *
 * <p>
 * Em conjunto com <code>Controle</code> e <code>EventoBase</code>, esse
 * componente é parte do trecho que implementa o design pattern
 * <strong>Observer</strong>.
 * </p>
 *
 * <p>
 * <code>EventoBaseOuvinte</code> atua como <i>observador</i>.</p>
 * 
 * @version 1.0.0
 *
 * @author Karlay
 *
 * @param <T> Tipo do Evento que será tratado, esse componente carrega
 * também um <code>objeto</code> <strong>(opcional)</strong> para ser usado
 * pelo seus ouvintes.
 * 
 * @see EventoBase
 */
public interface EventoBaseOuvinte<T extends EventoBase<?>> {
    
    /**
     * Quem implementa esse método será notificado quando ocorrer um evento.
     * @param evento Um tipo concreto de <code>EventoBase</code>.
     */
    public void notificandoEvento(T evento);
    
}
