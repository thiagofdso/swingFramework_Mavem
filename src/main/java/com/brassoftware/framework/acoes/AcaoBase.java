package com.brassoftware.framework.acoes;

import com.brassoftware.framework.ui.task.TaskDialogs;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * Componente representa uma ação, normalmente vinculada a intervenção do
 * usuário nos componentes de interface gráfica, para solicitar uma operação ao
 * sistema.
 *
 * <p>
 * A ação é representada por <code>AcaoBase</code>, um tipo de componente
 * complementar ao <code>MVC</code> (<strong>M</strong>odel
 * <strong>V</strong>iew <strong>C</strong>ontroller), um modelo arquitetural
 * utilizado para organizar os componentes do sistema.
 * </p>
 *
 * <p>
 * Utiliza o design pattern <code>Template Method</code> para definir um
 * estrutura/template com código complementar (e opcional) a ação:
 * </p>
 *
 * <ul>
 * <li><code>preAction()</code>: Acionando antes da execução de
 * <code>action()</code>.</li>
 * <li><code>posAction()</code>: Acionando após a execução (com sucesso) de
 * <code>action()</code>.</li>
 * <li><code>actionFailure()</code>: Acionando caso a execução de
 * <code>action()</code> falhe.</li>
 * </ul>
 *
 * @version 1.0.0
 *
 * @author Karlay
 */
public abstract class AcaoBase {

    private long inicio;

    /**
     * Método principal, define o processamento da <code>AbstractAction</code>.
     */
    protected abstract void action();

    /**
     * Método acionado <strong>antes</strong> de <code>action()</code>.<br>
     * Parte de um dos passos do Padrão <strong>Tamplate</strong>, e é um
     * <strong>Gancho</strong> para ser usado quando for conveniente.
     * <p>
     * Caso uma exceção (<code>RuntimeException</code>) seja lançada, a execução
     * de toda a <code>AbstractAction</code> é interrompida.
     * </p>
     */
    protected void preAction() {
        inicio = System.nanoTime();
    }

    /**
     * Método executado <strong>após</strong> a conclusão de
     * <code>action()</code>.<br>
     * Parte de um dos passos do Padrão <strong>Tamplate</strong>, e é um
     * <strong>Gancho</strong> para ser usado quando for conveniente.
     * <p>
     * Caso uma exceção (<code>RuntimeException</code>) seja lançada, a execução
     * de toda a <code>AbstractAction</code> é interrompida.
     * </p>
     */
    protected void posAction() {
        long tempo = System.nanoTime() - inicio;
        System.err.println("Tempo de resposta da ação: " + TimeUnit.MILLISECONDS.convert(tempo, TimeUnit.NANOSECONDS) + " Milisegundos");
    }

    /**
     * Método é acionado quando alguma falha ocorre durante a execução de
     * <code>action</code>, <code>preAction</code> ou
     * <code>posAction</code>.<br>
     * Parte de um dos passos do Padrão <strong>Tamplate</strong>, e é um
     * <strong>Gancho</strong> para ser usado quando for conveniente.
     * <p>
     * Caso uma exceção (<code>RuntimeException</code>) seja lançada, a execução
     * de toda a <code>AbstractAction</code> é interrompida.
     * </p>
     * @param ex
     */
    protected void actionFailure(Exception ex) {
    }

    /**
     * Método responsável por organizar e executar a cadeia de métodos de
     * <code>AbstractAction</code>.<br>
     * Esse é conhecido como método template no padrão de projeto
     * <strong>Template</strong>
     *
     * @throws RuntimeException caso algum erro ocorra.
     */
    public final void actionPerformed() {
                try {
                    preAction();
                    action();
                    posAction();
                } catch (Exception ex) {
                    TaskDialogs.showException(ex);
                    actionFailure(ex);
                    throw new RuntimeException(ex);
                }
                
            }
    public void setAction(JButton button,String hotkey){
        Action action=  new AbstractAction(button.getText()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    preAction();
                    action();
                    posAction();
                } catch (Exception ex) {
                    TaskDialogs.showException(ex);
                    actionFailure(ex);
                    throw new RuntimeException(ex);
                }

            }
        };
        if(hotkey!=null){
            action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(hotkey));
        }
        button.getActionMap().put(button.getText(), action);
        button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
        (KeyStroke) action.getValue(Action.ACCELERATOR_KEY), button.getText());
    }
    

}
