/**
 * <b>Notification.java</b>: Fornece os recursos de notificação para qualquer
 * objeto <code>javax.swing.JFrame</code> ou <code>javax.swing.JWindow</code>.
 */
package com.brassoftware.framework.notificacoes;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JWindow;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;

/**
 * Fornece os recursos de notificação para qualquer objeto
 * <code>javax.swing.JFrame</code> ou <code>javax.swing.JWindow</code>. A fim de
 * alcançar um efeito melhor, é altamente recomendável remover a borda da janela
 * fornecida pelo <code>javax.swing.JFrame</code>. Você não precisa se preocupar
 * com isso quando se utiliza <code>javax.swing.JWindow</code>, uma vez que ela
 * não tem bordas. Verifique o seguinte exemplo:
 * @code
 * JWindow window = new JWindow();
 * Notification note = new Notification(window, WindowPosition.BOTTOMRIGHT, 25, 25, 1000);
 * NotificationQueue queue = new NotificationQueue();
 * queue.add(note);
 * @endcode
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.3
 * @since 1.3
 */
public class Notification implements TimingTarget {

    private WindowPosition windowPosition;
    private WindowType windowType;
    private int thisHeight;
    private int thisWidth;
    // window object
    private JFrame windowJFrame;
    private JWindow windowJWindow;
    // coordinates
    private double borderX, borderY;
    private double boundX, boundY;
    private double positionX, positionY;
    // animation control
    private int duration;
    private AnimationFrame animationFrame;
    // animators, each one representing one state
    // on AnimationFrame class
    private Animator animatorHandlerOnShow;
    private Animator animatorHandlerOnDisplay;
    private Animator animatorHandlerOnClose;
    // time in milliseconds to animate windows
    // on show and close events
    private int timeToAnimate = 500;

    /**
     * Constructor method for a basic <code>javax.swing.JFrame</code> object. It
     * basically builds the notification model according to the provided
     * parameters. Just keep in mind the provided
     * <code>javax.swing.JFrame</code> is <b>disposed</b> after the notification
     * being displayed. Check the following example:
     * @code
     * JFrame window = new JFrame();
     * Notification note = new Notification(window, WindowPosition.BOTTOMRIGHT, 25, 25, 1000);
     * NotificationQueue queue = new NotificationQueue();
     * queue.add(note);
     * @endcode
     * @param window The window to act as a notification. Please, remove the borders
     * in order to achieve a greater effect.
     * @param windowPosition The window position on screen. You may choose one amongst
     * four states, each one representing the screen corners.
     * @param borderX The distance in pixels the window must keep from the X axis border. If
     * the notification is right-aligned, this border will be from the right side, and so forth.
     * Usually 50 pixels or less is an acceptable value for this parameter.
     * @param borderY The distance in pixels the window must keep from the Y axis border. If
     * the notification is aligned from the top, this border will be from the top itself, and so forth.
     * Usually 50 pixels or less is an acceptable value for this parameter.
     * @param duration The notification display duration in milliseconds. So if you want 2 seconds, you need
     * to multiply it by 1000; 2 seconds times 1000 = 2000 milliseconds.
     */
    public Notification(JFrame window, WindowPosition windowPosition, int borderX, int borderY, int duration) {

        // JFrame object
        this.windowType = WindowType.JFRAME;
        this.windowJWindow = null;

        // setting some attributes
        this.windowPosition = windowPosition;
        this.windowJFrame = window;
        this.borderX = borderX;
        this.borderY = borderY;

        // window attributes
        this.thisHeight = windowJFrame.getHeight();
        this.thisWidth = windowJFrame.getWidth();

        // set the animation duration
        this.duration = duration;

        {
            // retrieve the screen resolution and set some attributes
            Rectangle rect = getScreenResolution();
            this.boundX = rect.getWidth();
            this.boundY = rect.getHeight();
        }

        // second calculation: based on the window position and the provided
        // values, calculate positions on screen and the global payload for
        // that specific region
        switch (this.windowPosition) {
            case BOTTOMRIGHT:
                this.positionX = this.boundX - (this.thisWidth + this.borderX);
                this.positionY = this.boundY - (this.thisHeight + this.borderY);
                break;
            case BOTTOMLEFT:
                this.positionX = this.borderX;
                this.positionY = this.boundY - (this.thisHeight + this.borderY);
                break;
            case TOPRIGHT:
                this.positionX = this.boundX - (this.thisWidth + this.borderX);
                this.positionY = this.borderY;
                break;
            case TOPLEFT:
                this.positionX = this.borderX;
                this.positionY = this.borderY;
                break;
        }
    }

    /**
     * Constructor method for a basic <code>javax.swing.JWindow</code> object.
     * It basically builds the notification model according to the provided
     * parameters. Just keep in mind the provided
     * <code>javax.swing.JWindow</code> is <b>disposed</b> after the
     * notification being displayed. Check the following example:
     * @code
     * JWindow window = new JWindow();
     * Notification note = new Notification(window, WindowPosition.BOTTOMRIGHT, 25, 25, 1000);
     * NotificationQueue queue = new NotificationQueue();
     * queue.add(note);
     * @endcode
     * @param window The window to act as a notification. Note that a <code>javax.swing.JWindow</code>
     * object has no borders by default, so there is no need of removing them.
     * @param windowPosition The window position on screen. You may choose one amongst
     * four states, each one representing the screen corners.
     * @param borderX The distance in pixels the window must keep from the X axis border. If
     * the notification is right-aligned, this border will be from the right side, and so forth.
     * Usually 50 pixels or less is an acceptable value for this parameter.
     * @param borderY The distance in pixels the window must keep from the Y axis border. If
     * the notification is aligned from the top, this border will be from the top itself, and so forth.
     * Usually 50 pixels or less is an acceptable value for this parameter.
     * @param duration The notification display duration in milliseconds. So if you want 2 seconds, you need
     * to multiply it by 1000; 2 seconds times 1000 = 2000 milliseconds.
     */
    public Notification(JWindow window, WindowPosition windowPosition, int borderX, int borderY, int duration) {

        // JFrame object
        this.windowType = WindowType.JWINDOW;
        this.windowJFrame = null;

        // setting some attributes
        this.windowPosition = windowPosition;
        this.windowJWindow = window;
        this.borderX = borderX;
        this.borderY = borderY;

        // window attributes
        this.thisHeight = windowJWindow.getHeight();
        this.thisWidth = windowJWindow.getWidth();

        // set the animation duration
        this.duration = duration;

        {
            // retrieve the screen resolution and set some attributes
            Rectangle rect = getScreenResolution();
            this.boundX = rect.getWidth();
            this.boundY = rect.getHeight();
        }

        // second calculation: based on the window position and the provided
        // values, calculate positions on screen and the global payload for
        // that specific region
        switch (this.windowPosition) {
            case BOTTOMRIGHT:
                this.positionX = this.boundX - (this.thisWidth + this.borderX);
                this.positionY = this.boundY - (this.thisHeight + this.borderY);
                break;
            case BOTTOMLEFT:
                this.positionX = this.borderX;
                this.positionY = this.boundY - (this.thisHeight + this.borderY);
                break;
            case TOPRIGHT:
                this.positionX = this.boundX - (this.thisWidth + this.borderX);
                this.positionY = this.borderY;
                break;
            case TOPLEFT:
                this.positionX = this.borderX;
                this.positionY = this.borderY;
                break;
        }
    }

    /**
     * Calculates the screen size.
     *
     * @return A <code>java.awt.Rectangle</code> with the exact size of the
     * screen.
     */
    private Rectangle getScreenResolution() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return environment.getMaximumWindowBounds();
    }

    /**
     * Calculates the current window position based on the Y axis and the
     * fraction of elapsed time.
     *
     * @param x Fraction of elapsed time. This value is on a continuum interval,
     * 0 <= x <= 1.
     * @return An <code>int</code> value representing the current Y value
     * according to the elapsed time.
     */
    private int calculateCurrentPositionOnY(float x) {

        int result = 0;

        // checks if the animation is the beginning
        if (animationFrame == AnimationFrame.ONSHOW) {

            // calculates the position, using the following math function
            switch (windowPosition) {
                case BOTTOMRIGHT:
                case BOTTOMLEFT:
                    result = (int) (positionY + ((boundY - positionY) * (1 - x)));
                    break;
                case TOPRIGHT:
                case TOPLEFT:
                    result = (int) (positionY - ((thisHeight + borderY) * (1 - x)));
                    break;
            }

        } else {

            // animation is now closing
            if (animationFrame == AnimationFrame.ONCLOSE) {

                // calculates the position, now using the inverse math function
                switch (windowPosition) {
                    case BOTTOMRIGHT:
                    case BOTTOMLEFT:
                        result = (int) (positionY + ((boundY - positionY) * (x)));
                        break;
                    case TOPRIGHT:
                    case TOPLEFT:
                        result = (int) (positionY - ((thisHeight + borderY) * (x)));
                        break;
                }

            } else {

                // seems animation is now on display, then just return the very
                // same position
                result = (int) positionY;
            }

        }

        return result;
    }

    /**
     * Implements the <code>timingEvent</code> method from
     * <code>org.jdesktop.animation.timing.TimingTarget</code>. Please don't
     * call this function directly.
     *
     * @param f The continnum interval referring to the animation.
     */
    @Override
    public void timingEvent(float f) {

        // animate the window based on the Y axis
        setCurrentWindowBounds((int) positionX, calculateCurrentPositionOnY(f), thisWidth, thisHeight);

    }

    /**
     * Implements the <code>begin</code> method from
     * <code>org.jdesktop.animation.timing.TimingTarget</code>. This method is
     * called before animation begins. Please don't call this function directly.
     */
    @Override
    public void begin() {
        // empty body
    }

    /**
     * Implements the <code>end</code> method from
     * <code>org.jdesktop.animation.timing.TimingTarget</code>. This method is
     * called after the animation finishes. Please don't call this function
     * directly.
     */
    @Override
    public void end() {

        // checks if animation just finished the presenting state
        if (animationFrame == AnimationFrame.ONSHOW) {

            // create a new animation handler
            animatorHandlerOnDisplay = new Animator(duration, 1, Animator.RepeatBehavior.LOOP, this);

            // sets the current animation state
            animationFrame = AnimationFrame.ONDISPLAY;

            // run it
            animatorHandlerOnDisplay.start();
        } else {

            // now checking if animation just finished displaying
            if (animationFrame == AnimationFrame.ONDISPLAY) {

                // create a new animation handler
                animatorHandlerOnClose = new Animator(timeToAnimate, 1, Animator.RepeatBehavior.LOOP, this);

                // sets the current animation state
                animationFrame = AnimationFrame.ONCLOSE;

                // run it
                animatorHandlerOnClose.start();
            } else {

                // animation is done, so hide and dispose window
                setCurrentWindowVisible(false);
                disposeCurrentWindow();

            }
        }

    }

    /**
     * Implements the <code>repeat</code> method from
     * <code>org.jdesktop.animation.timing.TimingTarget</code>. This function is
     * called on every animation repetition. Please don't call this function
     * directly.
     */
    @Override
    public void repeat() {
        // empty body
    }

    /**
     * Performs the animation itself based on the parameters provided in the
     * constructor method. Keep in mind this method is synchronized. Check the
     * following example:
     * @code
     * JWindow window = new JWindow();
     * Notification note = new Notification(window, WindowPosition.BOTTOMRIGHT, 25, 25, 1000);
     * note.animate();
     * @endcode
     * Wherever possible, please use the new notification queue manager
     * <b>net.sf.jcarrierpigeon.NotificationQueue</b>.
     */
    public synchronized void animate() {

        // set the animation state
        animationFrame = AnimationFrame.ONSHOW;

        // define some window properties
        setCurrentWindowAlwaysOnTop(true);
        setCurrentWindowVisible(true);

        // defines the animator handler from Timing Framework
        // first animator handler
        animatorHandlerOnShow = new Animator(timeToAnimate, 1, Animator.RepeatBehavior.LOOP, this);

        // start animation
        animatorHandlerOnShow.start();
    }

    /**
     * Checks if the notification process is still running. It basically calls
     * the <code>isRunning</code> method from
     * <code>org.jdesktop.animation.timing.Animator</code>.
     *
     * @return <code>true</code> if the notification is still running, or
     * <code>false</code> otherwise.
     */
    public boolean isRunning() {
        return (animatorHandlerOnShow.isRunning())
                || (animatorHandlerOnDisplay.isRunning())
                || (animatorHandlerOnClose).isRunning();
    }

    /**
     * Sets the bounds of the current window. It's basically a call to the inner
     * window <code>setBounds</code> method.
     *
     * @param x Coordinate X
     * @param y Coordinate Y
     * @param width Width
     * @param height Height
     */
    private void setCurrentWindowBounds(int x, int y, int width, int height) {
        switch (windowType) {
            case JFRAME:
                windowJFrame.setBounds(x, y, width, height);
                break;
            case JWINDOW:
                windowJWindow.setBounds(x, y, width, height);
                break;
        }
    }

    /**
     * Sets the visibility of the current window. It's basically a call to the
     * inner window <code>setVisible</code> method.
     *
     * @param value <code>true</code> if window should be visible, or
     * <code>false</code> otherwise.
     */
    private void setCurrentWindowVisible(boolean value) {
        switch (windowType) {
            case JFRAME:
                windowJFrame.setVisible(value);
                break;
            case JWINDOW:
                windowJWindow.setVisible(value);
                break;
        }
    }

    /**
     * Sets the window parameter of being on top of other windows. It's
     * basically a call to the inner window <code>setAlwaysOnTop</code> method.
     *
     * @param value <code>true</code> if window should be on top of other
     * windows, or <code>false</code> otherwise.
     */
    private void setCurrentWindowAlwaysOnTop(boolean value) {
        switch (windowType) {
            case JFRAME:
                windowJFrame.setAlwaysOnTop(value);
                break;
            case JWINDOW:
                windowJWindow.setAlwaysOnTop(value);
                break;
        }
    }

    /**
     * Dispose the current window. It's basically a call to the inner window
     * <code>dispose</code> method.
     */
    private void disposeCurrentWindow() {
        switch (windowType) {
            case JFRAME:
                windowJFrame.dispose();
                break;
            case JWINDOW:
                windowJWindow.dispose();
                break;
        }
    }

    /**
     * Sets the animation speed. This method was rewritten and the name was
     * replaced by a more meaningful one. Check the following example:
     * @code
     * JWindow window = new JWindow();
     * Notification note = new Notification(window, WindowPosition.BOTTOMRIGHT, 25, 25, 1000);
     * note.setAnimationSpeed(500);
     * NotificationQueue queue = new NotificationQueue();
     * queue.add(note);
     * @endcode
     * @param milliseconds The notification effects duration in milliseconds.
     * Usually 500 milliseconds or less is an acceptable value for this parameter.
     */
    public void setAnimationSpeed(int milliseconds) {
        this.timeToAnimate = milliseconds;
    }
}
