package com.brassoftware.framework.controles;

import com.brassoftware.framework.notificacoes.Notification;
import com.brassoftware.framework.notificacoes.NotificationQueue;

/**
 *
 * @author Suany
 */
public abstract class ControleAplicacao extends Controle {
    
    private final NotificationQueue filaNotificacoes = new NotificationQueue();
    
    public ControleAplicacao() {
    }
    
    /**
     * Contrutor responsável por passar para a sua super classe o pai desse
     * <strong>Controle</strong>.
     *
     * @param pai <strong>Controle</strong> que será o pai.
     */
    public ControleAplicacao(Controle pai) {
        super(pai);
    }    

    public void addNotificacaoFila(Notification notificacao) {
        filaNotificacoes.add(notificacao);
    }
    
    @Override
    protected void cleanUp() {
        filaNotificacoes.limparFila();
        super.cleanUp();
    }

}
