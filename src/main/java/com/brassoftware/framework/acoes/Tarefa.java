package com.brassoftware.framework.acoes;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingWorker;

/**
 *
 * @author Karlay
 */
public class Tarefa extends SwingWorker<Void, Integer>{

    private final AcaoBase acao;   
    private long inicio;/* Opicionais */
    
    public Tarefa(AcaoBase acao) {
        this.acao = acao;
    }
    
    @Override
    protected Void doInBackground() throws Exception {
        inicio = System.nanoTime();/* Opicional */
        acao.actionPerformed();
        return null;
    }

    @Override
    protected void process(List<Integer> chunks) {
        System.err.println(chunks);
    }
    
    @Override
    protected void done() {
        long tempo = System.nanoTime() - inicio; /* Opicional */
        System.err.println("Tempo de resposta da tarefa: " + TimeUnit.MILLISECONDS.convert(tempo, TimeUnit.NANOSECONDS) + " Milisegundos"); /* Opicional */
    }
    
}
