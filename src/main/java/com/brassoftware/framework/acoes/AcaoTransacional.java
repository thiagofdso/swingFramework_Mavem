package com.brassoftware.framework.acoes;

import com.brassoftware.framework.controles.ControlePersistente;
import com.brassoftware.framework.ui.task.TaskDialogs;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 * Classe decoradora de uma ação. Adiciona o controle transacional a uma ação.
 *
 * <p>
 * <code>AcaoTransacional</code> possui uma referência para outra
 * <code>AcaoBase</code>, quem efetivamente implementa a ação. Realiza o
 * controle da transação utilizando a API <code>JPA</code>.
 * </p>
 *
 * <p>
 * O escopo transacional está disponível somente para o método
 * <code>action()</code>. As operações <code>preAction</code>,
 * <code>posAction</code> e <code>actionFailure</code> são delegadas para a ação
 * contida.
 * </p>
 *
 * <p>
 * Para implementar esse componente utilizamos o padrão de projeto
 * <strong>Decorator</strong> e <strong>Builder</strong>.</p>
 *
 * @version 1.0.0
 *
 * @author Karlay
 */
public class AcaoTransacional extends AcaoBase {

    /**
     * Referência para a ação que deverá executar em escopo transacional.
     */
    private AcaoBase acao;

    /**
     * Referência para o controlador que gerencia o contexto de persistência.
     */
    private ControlePersistente controlePersistente;

    public AcaoTransacional() {
    }

    /**
     * @return Constrói e retorna uma instância de <code>AcaoTransacional</code>
     * sem ação e controle definido usando o padão <strong>Builder</strong>.
     */
    public static AcaoTransacional criar() {
        return new AcaoTransacional();
    }

    /**
     * Adiciona uma ação ao <code>AcaoTransacional</code>.
     *
     * @param acao ação que efetivamente executa as operações
     * <code>AcaoTransacional</code>.
     * @return <code>AcaoTransacional</code> com uma ação definida.
     */
    public AcaoTransacional addAcao(AcaoBase acao) {
        this.acao = acao;
        return this;
    }

    /**
     * Passa a referência para o <code>ControlePersistente</code> <i>dono</i>
     * do contexto de persistência.
     * <p>
     * O acesso ao <code>EntityManager</code> ocorre via
     * <code>ControlePersistente</code>.</p>
     *
     * @param controlePersistente referência para o controle presistente.
     * @return <code>AcaoTransacional</code> com
     * <code>ControlePersistente</code> vinculado.
     */
    public AcaoTransacional addControlePersistente(ControlePersistente controlePersistente) {
        this.controlePersistente = controlePersistente;
        return this;
    }

    /**
     * Executa o método <code>action</code> da ação encapsulada com escopo
     * transacional.
     *
     * @throws IllegalArgumentException caso não tenha ação,
     * <code>ControlePersistente</code> e/ou <code>EntityManager</code>
     * vinculado.
     */
    @Override
    protected void action() {
        if (acao == null) {
            throw new IllegalArgumentException("Indique a Ação que deve ser executada, utilize o método addAcao.");
        }
        if (controlePersistente == null) {
            throw new IllegalArgumentException("Informe o dono do contexto de persistencia, utilize o método addControlePersistente.");
        }
        EntityManager em = controlePersistente.getContextoPersistencia();
        try {
            em.getTransaction().begin();
            acao.action();
            em.getTransaction().commit();
        } catch (Exception e) {
             if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            actionFailure(e);
            TaskDialogs.showException(e);
            throw new RuntimeException(e);
        }

    }

    /**
     * Delega a execução para o método <code>actionFailure</code> para a ação
     * encapsulada.
     * @param ex
     */
    @Override
    protected void actionFailure(Exception ex) {
        if (acao != null) {
            acao.actionFailure(ex);
        }
    }

    /**
     * Delega a execução para o método <code>posAction</code> para a ação
     * encapsulada.
     */
    @Override
    protected void posAction() {
        if (acao != null) {
            acao.posAction();
        }
    }

    /**
     * Delega a execução para o método <code>preAction</code> para a ação
     * encapsulada.
     */
    @Override
    protected void preAction() {
        if (acao != null) {
            acao.preAction();
        }
    }

}
