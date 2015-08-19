/**
 * Author: Luis M Pena ( lu@coderazzi.net ) License: MIT License
 *
 * Copyright (c) 2007 Luis M. Pena - lu@coderazzi.net
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.brassoftware.framework.ui.filters.gui;

import java.text.Format;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.swing.table.TableModel;

import com.brassoftware.framework.ui.filters.IFilter;

/**
 * Public interface of the editors associated to each table's column.
 */
public interface IFilterEditor {

    /**
     * Returns the model position associated to this editor.
     * @return 
     */
    int getModelIndex();

    /**
     * Returns the class associated to the editor on the model.
     * @return 
     */
    Class<?> getModelClass();

    /**
     * Returns the {@link IFilter} associated to the editor's content<br>
     * The returned instance can then be used to enable or disable the filter
     * and its GUI component.
     * @return 
     */
    IFilter getFilter();

    /**
     * Resets the filter, which implies set its content to empty and reset its
     * history choices.
     */
    void resetFilter();

    /**
     * Sets the content, adapted to the editors' type.
     * @param content
     */
    void setContent(Object content);

    /**
     * Returns the current editor's content.
     * @return 
     */
    Object getContent();

    /**
     * Using autoChoices, the choices displayed on the popup menu are
     * automatically extracted from the associated {@link TableModel}.<br>
     * For editors associated to boolean or short enumerations, if
     * AutoCompletion is not set, setting the AutoChoices automatically changes
     * the editable flag to true, unless AutoChoices has the DISABLED value
     * @param mode
     */
    void setAutoChoices(AutoChoices mode);

    /**
     * Returns the autoChoices mode.
     * @return 
     */
    AutoChoices getAutoChoices();

    /**
     * Sets the available choices, shown on the popup menu.
     * @param choices
     */
    void setCustomChoices(Set<CustomChoice> choices);

    /**
     * Returns the current choices.
     * @return 
     */
    Set<CustomChoice> getCustomChoices();

    /**
     * Enables or disables the user's interaction; if disabled, the control is
     * disabled but the associated filter remains in place.
     * @param enable
     */
    void setUserInteractionEnabled(boolean enable);

    /**
     * Returns the user interaction mode.
     * @return 
     */
    boolean isUserInteractionEnabled();

    /**
     * Defines the editor, if text based -i.e., without associated {@link
     * ChoiceRenderer}, as editable: this flag means that the user can enter any
     * text, not being limited to the existing choices
     * @param enable
     */
    void setEditable(boolean enable);

    /**
     * Returns the editable flag.
     * @return 
     */
    boolean isEditable();

    /**
     * Sets the ignore case flag.
     * @param set
     */
    void setIgnoreCase(boolean set);

    /**
     * Returns the ignore case flag.
     * @return 
     */
    boolean isIgnoreCase();

    /**
     * Sets the {@link Format} required by the editor to handle the user's input
     * when the associated class is not a String<br>
     * It is initially retrieved from the {@link IParserModel}.
     * @param format
     */
    void setFormat(Format format);

    /**
     * Returns the associated {@link Format}.
     * @return 
     */
    Format getFormat();

    /**
     * Sets the {@link Comparator} required to compare (and sort) instances of
     * the associated class in the table model.<br>
     * This operation sets also this operator as the choices comparator (see
     * {@link #setChoicesComparator(Comparator)})
     *
     * @param comparator cannot be null
     */
    void setComparator(Comparator comparator);

    /**
     * Returns the associated {@link Comparator}, which is never null.
     * @return 
     */
    Comparator getComparator();

    /**
     * Sets the {@link Comparator} used to sort out the choices. By default.
     * this is the same operator associated to the editor. Note that editors
     * associated to enumeration types are sorted by default alphabetically.<br>
     *
     * @param comparator can be set to null to use alphabetic sorting
     * @see IFilterEditor#setComparator(Comparator)
     */
    void setChoicesComparator(Comparator comparator);

    /**
     * Returns the associated {@link Comparator} choices comparator.
     * @return 
     */
    Comparator getChoicesComparator();

    /**
     * Sets the auto completion flag.
     * @param enable
     */
    void setAutoCompletion(boolean enable);

    /**
     * Returns the auto completion flag.
     * @return 
     */
    boolean isAutoCompletion();

    /**
     * Sets the instant filtering flag.
     * @param enable
     */
    void setInstantFiltering(boolean enable);

    /**
     * Returns the instant filtering flag.
     * @return 
     */
    boolean isInstantFiltering();

    /**
     * Limits the history size.<br>
     * This limit is only used when the popup contains also choices. Otherwise,
     * the maximum history size is to the maximum number of visible rows<br>
     * The max history cannot be greater than the max visible rows
     * @param size
     */
    void setMaxHistory(int size);

    /**
     * Returns the maximum history size, as defined by the user.<br>
     * This is not the real maximum history size, as it depends on the max
     * number of visible rows and whether the popup contains only history or
     * also choices
     * @return 
     */
    int getMaxHistory();

    /**
     * Sets the history contents.
     *
     * @param history
     * @since 4.3.1.0
     */
    void setHistory(List<Object> history);

    /**
     * Returns the current history contents
     *
     * @return 
     * @since 4.3.1.0
     */
    List<Object> getHistory();

    /**
     * Sets the {@link ChoiceRenderer} for the choices / history.
     *
     * <p>
     * It also affects to how the content is rendered<br>
     * If not null, the content cannot be text-edited anymore</p>
     *
     * @param renderer
     */
    void setRenderer(ChoiceRenderer renderer);

    /**
     * Returns the associated {@link ChoiceRenderer}.
     * @return 
     */
    ChoiceRenderer getRenderer();

    /**
     * Returns the current editor's look.
     * @return 
     */
    Look getLook();

}
