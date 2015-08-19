package com.brassoftware.framework.acoes;

import javax.swing.SwingWorker;

/**
 *
 * @author Karlay
 */
public class AcaoTarefa extends AcaoBase {

    /**
     * Referência para a ação que deverá executar em escopo transacional.
     */
    private AcaoBase acao;
    
    private SwingWorker<?, ?> tarefa;
    
    public AcaoTarefa() {
    }

    /**
     * @return Constrói e retorna uma instância de <code>AcaoTransacional</code>
     * sem ação e controle definido.
     */
    public static AcaoTarefa criar() {
        return new AcaoTarefa();
    }    

    /**
     * Adiciona uma ação ao <code>AcaoTransacional</code>.
     *
     * @param acao ação que efetivamente executa as operações
     * <code>AcaoTransacional</code>.
     * @return <code>AcaoTransacional</code> com uma ação definida.
     */
    public AcaoTarefa addAcao(AcaoBase acao) {
        this.acao = acao;
        return this;
    } 
    
    public AcaoTarefa addTarefa(SwingWorker<?, ?> tarefa) {
        this.tarefa = tarefa;
        return this;
    }
    
    @Override
    protected void action() {
        if (tarefa == null) {
            if (acao == null) {
                throw new IllegalArgumentException("Indique a Ação que deve ser executada, utilize o método addAcao.");
            }            
            Tarefa segundoPlano = new Tarefa(acao);
            segundoPlano.execute();            
        } else {
            tarefa.execute();
        }

    }
    
}
