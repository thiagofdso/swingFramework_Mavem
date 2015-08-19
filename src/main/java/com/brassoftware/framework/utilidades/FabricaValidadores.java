package com.brassoftware.framework.utilidades;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;

/**
 * Durante a execução do aplicativo é possível trabalhar com vários validadores,
 * por isso carregamos só uma vez a fábrica de velidades
 * (<code>ValidatorFactory</code>) que tem um custo muito alto de recurso para
 * o sitema e usamos essa fábrica durante toda a execução do aplicativo.
 *
 * <p>
 * Essa classe é responsavel por disponibilizar o(s) componente(s)
 * <code>Validator</code>. Utilizamos o <code>ValidatorFactory</code>
 * fornecedor (fábrica) de validadores.</p>
 *
 * <p>
 * Essa classe usa o padrão de projeto <code>Singleton</code>. </p>
 * 
 * @version 1.0.0
 *
 * @author Karlay
 */
public class FabricaValidadores {
    /**
     * Atributo estático que cria uma fábrica de validadores.
     */
    private static final ValidatorFactory factory;
    
    /**
     * Cria uma única fábrica de componentes <code>Validator</code> para 
     * todo o sitema.
     */
    static {
        /* ApacheValidationProvider.class | HibernateValidator.class */
        factory = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory();//buildDefaultValidatorFactory();
    }
    
    /**
     * Método que devolve um validador.
     * @return Um validador para seru usado.
     */
    public static final Validator getValidador() {
        return factory.getValidator();
    }
}
