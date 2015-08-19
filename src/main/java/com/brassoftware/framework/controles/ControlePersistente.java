package com.brassoftware.framework.controles;

import com.brassoftware.framework.utilidades.FabricaEntidades;
import javax.persistence.EntityManager;

/**
 * Define um <code>Controle</code> com funcionalidades para persistência.
 *
 * <p>
 * Responsável por carregar, disponibilizar e encerrar o <i>contexto de
 * persistência</i> (<code>EntityManager</code>), para as operações de
 * persistência definidas no <code>Controle</code>.
 * </p>
 *
 * @author Karlay
 */
public abstract class ControlePersistente extends Controle {

    private EntityManager contextoPersistencia;
    
    // Variável responsável de informar se o Controle é ou não
    // dono do contexto de persistência.
    private boolean donaContextoPersistencia = false;

    /**
     * Contrutor padão sem argumentos.
     */
    public ControlePersistente() {
    }

    /**
     * Contrutor responsável por passar para a sua super classe o pai desse
     * <strong>Controle</strong>.
     *
     * @param pai <strong>Controle</strong> que será o pai.
     */
    public ControlePersistente(Controle pai) {
        super(pai);
    }
    
    /**
     * Método final que devolve a instância da própia classe para ser usada no
     * construtor para não haver vazamento no mesmo.
     *
     * @return Uma intância do controle persistente a ser criado.
     */
    protected final ControlePersistente getInstancePersistente() {
        return this;
    }
    
    /**
     * Carrega um novo <i>contexto de persistência</i>
     * (<code>EntityManager</code>).
     */
    protected void carregaContextoPersistencia() {
        carregaContextoPersistencia(null);
    }

    /**
     * Carrega o <i>contexto de persistência</i> para uso na Controle.
     *
     * <p>
     * Utiliza o contexto argumento, ou cria um novo senão for informado. Dessa
     * forma é possível criar ou compartilhar o mesmo <code>EntityManager</code>
     * em mais de uma <strong>Controle</strong>. Caso um novo
     * <code>EntityManager</code> seja obtido, o <code>Controle</code> é
     * considerado "dono" do <i>contexto de persistência</i>.
     * </p>
     *
     * @param contextoPersistencia
     */
    protected void carregaContextoPersistencia(EntityManager contextoPersistencia) {
        if (contextoPersistencia == null) {
            this.contextoPersistencia = FabricaEntidades.createEntityManager();
            this.donaContextoPersistencia = true;
        } else {
            this.contextoPersistencia = contextoPersistencia;
            this.donaContextoPersistencia = false;
        }

    }

    /**
     * @return referência para o <i>contexto de persistência</i>
     * (<code>EntityManager</code>).
     */
    public EntityManager getContextoPersistencia() {
        if (contextoPersistencia == null || !contextoPersistencia.isOpen()) {
            carregaContextoPersistencia();
        }
        return contextoPersistencia;
    }

    /**
     * Libera o <code>EntityManager</code>, caso o <strong>Controle</strong>
     * seja a "dono" do <i>contexto de persistência</i>.
     */
    @Override
    protected void cleanUp() {
        /* Testa se o controle é dono do contexto de persistência e
         * se ele está aberto. */
        if (donaContextoPersistencia && contextoPersistencia != null && contextoPersistencia.isOpen()) {
            // Fechando o contexto de persistência para liberar recusro.
            contextoPersistencia.close();
        }
        super.cleanUp();
    }

}
