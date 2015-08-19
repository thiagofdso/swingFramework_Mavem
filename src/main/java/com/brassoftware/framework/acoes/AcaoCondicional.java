package com.brassoftware.framework.acoes;

/**
 * Uma <code>AcaoCondicional</code> vinculada a uma condição
 * <code>boolean</code>.
 *
 * <p>
 * <code>AcaoCondicional</code> possui uma referência para outra
 * <code>AcaoBase</code>, quem efetivamente implementa a ação. Mas o código
 * dessa ação só será executado quando a condição <code>boolean</code> for
 * satisfatória (<code>true</code>).
 * </p>
 *
 * <p>
 * Para implementar esse componente utilizamos os padrões de projeto
 * <strong>Decorator</strong> e <strong>Builder</strong>.</p>
 *
 * @version 1.0.0
 *
 * @author Karlay
 */
public final class AcaoCondicional extends AcaoBase {

    /**
     * Referência para a ação que deverá executar de acordo com a condição
     * <code>boolean</code>.
     */
    private AcaoBase acao;

    /**
     * Referência para condição <code>boolean</code>.
     */
    private ExpressaoBoleana condicional;

    public AcaoCondicional() {
    }

    /**
     * @return Constrói e retorna uma instância de <code>AcaoCondicional</code>
     * sem ação e condição definida usando o padão <strong>Builder</strong>.
     */
    public static AcaoCondicional criar() {
        return new AcaoCondicional();
    }

    /**
     * Adiciona uma ação a <code>AcaoCondicional</code>.
     *
     * @param acao ação que deve ser processada.
     * @return <code>AcaoCondicional</code> com uma ação definida.
     */
    public AcaoCondicional addAcao(AcaoBase acao) {
        this.acao = acao;
        return this;
    }

    /**
     * Adiciona a condição que determina se a ação deve ou não ser processada.
     *
     * @param condicional condição <code>boolean</code> avaliada por
     * <code>AcaoCondicional</code>.
     * @return <code>AcaoCondicional</code> com a condição <code>boolean</code>
     * definida.
     */
    public AcaoCondicional addCondicional(ExpressaoBoleana condicional) {
        this.condicional = condicional;
        return this;
    }

    /**
     * Avalia a condição <code>boolean</code> para processar ou não a ação.
     *
     * @throws IllegalArgumentException caso não tenha ação e/ou condição
     * <code>boolean</code> vinculada.
     */
    @Override
    protected void action() {
        if (acao == null) {
            throw new IllegalArgumentException("Indique a Ação que deve ser executada, utilize o método addAcao.");
        }
        if (condicional == null) {
            throw new IllegalArgumentException("Indique a expressão condicional da Ação, utilize o método addCondicional.");
        }
        if (condicional.condicional()) {
            acao.actionPerformed();
        }
    }

}
