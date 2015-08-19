/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.tarefas;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Suany
 */
public abstract class TarefaBase extends TimerTask {

    private Timer timer;
    private long delay = 5000;
    private long periodo = 5000;

    public TarefaBase() {
        carrega();
    }

    public TarefaBase(long tempo) {
        this.delay = tempo;
        this.periodo = tempo;
        carrega();
    }   

    public TarefaBase(long delay, long periodo) {
        this.delay = delay;
        this.periodo = periodo;
        carrega();
    }    
    
    private void carrega() {
        this.timer = new Timer();
        this.timer.schedule(this, delay, periodo);
    }

    public void finalizarTarefa() {
        this.timer.cancel();
    }
    
    @Override
    public abstract void run();

}
