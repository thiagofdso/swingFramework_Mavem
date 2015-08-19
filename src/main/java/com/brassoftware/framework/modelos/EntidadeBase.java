package com.brassoftware.framework.modelos;

import java.io.Serializable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Estipula um contrato base para as entidades persistentes da aplicação.
 * 
 * <p>Esse contrato é utilizado pelo componente base de persistência: <code>BaseDAO</code>.</p>
 * 
 *  1.0.0
 * 
 * @author karlay
 */
public interface EntidadeBase extends Serializable {

    /**
     * @return A referência para a chave primária (Primary Key) de cada objeto
     * persistido. Caso o objeto ainda não tenha sido persistido, deve retornar
     * <code>null</code>.
     */
    public Object getId();

    @PrePersist
    public void onCreate();

    @PreUpdate
    public void onUpdate();
}
