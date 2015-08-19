package com.brassoftware.framework.dao;

import com.brassoftware.framework.modelos.EntidadeBase;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Componente <code>DAO</code> (design pattern: <code>Data Access Object</code>)
 * base da aplicação.
 *
 * <p>
 * Define as operações básicas de cadastro para cada tipo de entidade.
 * (<strong>C</strong>reate / <strong>R</strong>ead / <strong>U</strong>pdate /
 * <strong>D</strong>elete)
 * </p>
 *
 * <p>
 * Dessa forma os componentes <code>DAO</code> das entidades, a priori, não
 * devem se preocupar com essas funcionalidades.</p>
 *
 * <p>
 * Efetivamente, na <code>JPA</code>, todas as operações de persistência são
 * realizadas pelo componente <code>EntityManager</code>, por isso dependemos
 * desse componente.
 * </p>
 *
 * @version 1.0.0
 *
 * @author karlay
 *
 * @param <T> determina o tipo da entidade, deve herdar
 * <code>AbstractEntity</code>.
 * @param <PK> determina o tipo da chave primária (Primary Key) da entidade,
 * deve herdar <code>Number</code>.
 */
public abstract class BaseDAO<T extends EntidadeBase, PK extends Object> {

    /**
     * "Tipo" da entidade gerenciada pelo <code>DAO</code>.
     */
    private final Class<T> entityClass;


    /**
     * Referência do <i>contexto de persistência</i>.
     */
    private final EntityManager em;

    /**
     * A dependência para <code>EntityManager</code> deve ser resolvida durante
     * a construção do <code>AbstractDAO</code>.
     *
     * @param em referência para o <code>EntityManager</code>.
     */
    @SuppressWarnings("unchecked")
    public BaseDAO(EntityManager em) {
        this.em = em;
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    /**
     * Faz o <code>insert</code> caso a entidade ainda não tenha sido persistida (<code> id == null
     * </code>), ou <code>update</code> caso contrário.
     *
     * @param e indica a entidade que deverá ser persistida.
     * @return a referência atual da entidade.
     */
    public T save(T e) {
        if (e.getId() != null && em.find(entityClass, e.getId()) != null) {
            return em.merge(e);
        } else {
            em.persist(e);
            return e;
        }
    }

    /**
     * Busca o registro por <code>id</code> (primary key).
     *
     * @param id filtro da consulta.
     * @return a entidade encontrada de acordo com o <code>id</code>.
     * @throws RuntimeException caso o não exista registro para esse id.
     */
    public T findById(PK id) {
        return em.find(entityClass, id);
    }

    /**
     *
     * @param entidade
     * @return
     */
    public T refresh(T entidade) {

        try {
            if (!em.contains(entidade)) {
                entidade = em.merge(entidade);
            }
            em.refresh(entidade);
        } catch (Exception e) {

        }

        return entidade;
    }

    /**
     * @return uma coleção (<code>List</code>) com todos os registro da entidade
     * armazenados no banco de dados. Caso não existam registros, retorna uma
     * coleção vazia.
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        String str = "SELECT o FROM " + entityClass.getName() + " o";
        Query query = em.createQuery(str);
        return (List<T>) query.getResultList();
    }

    /**
     * @return uma coleção (<code>List</code>) com todos os registro da entidade
     * armazenados no banco de dados. Caso não existam registros, retorna uma
     * coleção vazia.
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllRefresh() {
        String str = "SELECT o FROM " + entityClass.getName() + " o";
        Query query = em.createQuery(str);
        List<T> lista = query.getResultList();
        for (T elemento : lista) {
            em.refresh(elemento);
        }
        return lista;
    }

    /**
     * @param consulta
     * @return uma coleção (<code>List</code>) com todos os registro da entidade
     * armazenados no banco de dados. Caso não existam registros, retorna uma
     * coleção vazia.
     */
    @SuppressWarnings("unchecked")
    public List<T> getQuery(String consulta) {
        Query query;
        try {
            query = em.createQuery(consulta);
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
        return query.getResultList();
    }

    /**
     * Faz o <code>delete</code> da entidade no banco de dados.
     *
     * @param e
     */
    public void remove(T e) {
        if (!em.contains(e)) {
            e = em.merge(e);
        }
        em.remove(e);
    }

    /**
     * @return referência do componente <code>EntityManager</code>.
     */
    public EntityManager getPersistenceContext() {
        return this.em;
    }

}
