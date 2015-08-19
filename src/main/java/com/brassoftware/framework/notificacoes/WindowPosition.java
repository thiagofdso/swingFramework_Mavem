/**
 * <b>WindowPosition.java</b>: provides an enumeration for the window position
 * on screen. This enumeration is used to set the parameters referring the
 * window position and to assign the correct mathematical formulas to the
 * animation handlers.
 */
package com.brassoftware.framework.notificacoes;

/**
 * Provides an enumeration for the window position on screen. This enumeration
 * basically consists on four states, defined later on the documentation.
 * Consider \f$ f: \mathbb{R} \rightarrow \mathbb{Z} \f$ as a function to
 * calculate the current position of a window on the screen. Lets take a
 * parameter \f$ x \in \mathbb{R} \f$ that \f$ 0 \leq x \leq 1 \f$, then \f$
 * f(x) \in \mathbb{Z} \f$. The animation handler will return a continuum value
 * which must be converted to a screen position through this function \f$ f \f$.
 * Each screen position has its own assignment for this calculation. To enhance
 * the comprehension, consider these symbols: - \f$ d \f$ refers to the screen
 * dimension. That way, we may also consider the following notation: - \f$ d_h
 * \f$ is the X axis, that is, the width of the screen. - \f$ d_v \f$ is the Y
 * axis, that is, the height of the screen. - \f$ j \f$ refers to the window
 * acting as a notification. We may also consider the following notation: - \f$
 * j_h \f$ is the X axis, that is, the width of the window. - \f$ j_v \f$ is the
 * Y axis, that is, the height of the window. - \f$ b \f$ is the distance
 * between the window bounds to the screen bounds. We consider the following
 * notation: - \f$ b_h \f$ is the X axis, that is, the width of the distance. -
 * \f$ b_v \f$ is the Y axis, that is, the height of the distance. - \f$ p \f$
 * is the coordinate for the window acting as a notification. We consider the
 * following notations: - \f$ p_h \f$ is the X axis, and by that we really mean
 * the X axis. - \f$ p_v \f$ is the Y axis, and by that we really mean the Y
 * axis.
 *
 * It is also important to note that the assignments for \f$ f(x) \f$ also
 * depend on <b>net.sf.jcarrierpigeon.AnimationFrame</b>.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.3
 * @since 1.0
 */
public enum WindowPosition {

    /**
     * The window will be displayed on the top left of the screen. - \f$ p_h =
     * b_h \f$ - \f$ p_v = b_v \f$ - <i>ONSHOW</i>: \f$ f(x) = p_v - ((j_v +
     * b_v) (1 - x)) \f$ - <I>ONDISPLAY</i>: Not applicable. - <i>ONCLOSE</i>:
     * \f$ f(x) = p_v - ((j_v + b_v) x) \f$
     */
    TOPLEFT,
    /**
     * The window will be displayed on the top right of the screen. - \f$ p_h =
     * d_h - (j_h + b_h) \f$ - \f$ p_v = b_v \f$ - <i>ONSHOW</i>: \f$ f(x) = p_v
     * - ((j_v + b_v) (1 - x)) \f$ - <I>ONDISPLAY</i>: Not applicable. -
     * <i>ONCLOSE</i>: \f$ f(x) = p_v - ((j_v + b_v) x) \f$
     */
    TOPRIGHT,
    /**
     * The window will be displayed on the bottom left of the screen. - \f$ p_h
     * = b_h \f$ - \f$ p_v = d_v - (j_v + b_v) \f$ - <i>ONSHOW</i>: \f$ f(x) =
     * p_v + ((d_v - p_v) (1 - x)) \f$ - <I>ONDISPLAY</i>: Not applicable. -
     * <i>ONCLOSE</i>: \f$ f(x) = p_v + ((d_v - p_v) x) \f$
     */
    BOTTOMLEFT,
    /**
     * The window will be displayed on the bottom right of the screen. - \f$ p_h
     * = d_h - (j_h + b_h) \f$ - \f$ p_v = d_v - (j_v + b_v) \f$ -
     * <i>ONSHOW</i>: \f$ f(x) = p_v + ((d_v - p_v) (1 - x)) \f$ -
     * <I>ONDISPLAY</i>: Not applicable. - <i>ONCLOSE</i>: \f$ f(x) = p_v +
     * ((d_v - p_v) x) \f$
     */
    BOTTOMRIGHT
}
