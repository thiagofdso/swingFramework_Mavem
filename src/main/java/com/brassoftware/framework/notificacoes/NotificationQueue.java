/**
 * <b>NotificationQueue.java</b>: handles the display of notifications. This
 * class was created to act as a notification manager instead of allowing
 * multiple windows at once. Use this class together with the new
 * <b>net.sf.jcarrierpigeon.Notification</b> class.
 */
package com.brassoftware.framework.notificacoes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.Timer;

/**
 * Handles the display of notifications. This class was created to act as a
 * notification manager instead of allowing multiple windows at once. I just
 * realized that too many notifications being displayed at once won't be fully
 * noticed by the user, so a queue system for notifications was a wiser choice.
 * From version 1.3 of JCarrierPigeon, multiple window support is replaced by a
 * notification queue system, where only a single notification is shown per
 * time.
 *
 * Please note that this notification manager handles the calls for methods of
 * the <b>net.sf.jcarrierpigeon.Notification</b> class. After adding a
 * notification to the queue, there is no need of calling any methods of the
 * notification itself. Check the following example:
 * @code
 * JWindow window = new JWindow();
 * Notification note = new Notification(window, WindowPosition.BOTTOMRIGHT, 25, 25, 1000);
 * NotificationQueue queue = new NotificationQueue();
 * queue.add(note);
 * @endcode
 * If there is no notifications in the queue, the notification is displayed
 * right after the call of the #add() method. Otherwise, it will be queued
 * until there is no notifications in display. Please note this is a simple
 * <i>first in first out</i> queue, so no priorities are estabilished when
 * adding notifications.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.3
 * @since 1.3
 */
public class NotificationQueue implements ActionListener {

    // a fila, o tempo e a notificação
    private final Queue<Notification> queue;
    private final Timer timer;
    private Notification current;

    /**
     * Método construtor. Nada de novo aqui, apenas instanciar os atributos
     * locais.
     */
    public NotificationQueue() {
        queue = new LinkedList();
        timer = new Timer(50, this);
        current = null;
    }

    /**
     * Adiciona a notificação para o sistema de fila atual. Se esta é a única
     * notificação na fila, ele provavelmente será mostrado imediatamente. Por
     * favor, verifique o seguinte exemplo:
     * @code
     * JWindow window = new JWindow();
     * Notification note = new Notification(window, WindowPosition.BOTTOMRIGHT, 25, 25, 1000);
     * NotificationQueue queue = new NotificationQueue();
     * queue.add(note);
     * @endcode
     * @param notification The <b>net.sf.jcarrierpigeon.Notification</b>
     * object.
     */
    public synchronized void add(Notification notification) {
        // Verifica se a fila está vazia e não há
        // nenhuma notificação atual
        if (queue.isEmpty() && (current == null)) {

            // mostra notificação
            current = notification;
            current.animate();
        } else {
            // há outras notificações, por isso temos de esperar
            queue.offer(notification);

            // verificar se o temporizador não está em execução
            if (timer.isRunning() == false) {

                // iniciar o temporizador
                timer.start();
            }
        }
    }

    /**
     * Implementa o <code>ActionListener</code> pelo seu tempo. Ele irá acionar
     * notificações e processar a fila.
     *
     * @param e The event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Existe uma notificação atual acontecendo
        if (current != null) {

            // verificar se a fila não está vazio e não há notificação rodando
            if ((!queue.isEmpty()) && (!current.isRunning())) {

                // consultar uma notificação da fila
                current = (Notification) queue.poll();

                // anima a notificação
                current.animate();
            } else {

                // se a fila estiver vazia
                if (queue.isEmpty()) {

                    // parar o temporizador
                    timer.stop();
                }
            }
        }
    }

    public void limparFila() {
        queue.clear();
    }
    
}
