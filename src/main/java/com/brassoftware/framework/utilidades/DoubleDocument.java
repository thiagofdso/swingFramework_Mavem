/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.utilidades;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Helper class. Only allows an Double value in the document.
 *
 * @author <a href="mailto:vhardy@apache.org">Vincent Hardy</a>
 * @version $Id: DoubleDocument.java 504084 2007-02-06 11:24:46Z dvholten $
 */
public class DoubleDocument extends PlainDocument {

    /**
     * Strip all non digit characters. The first character must be '-' or '+'.
     * Only one '.' is allowed.
     *
     * @throws javax.swing.text.BadLocationException
     */
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

        if (str == null) {
            return;
        }

        // Obter valor atual
        String curVal = getText(0, getLength());
        boolean hasDot = curVal.indexOf(',') != -1;

        // Faixa de caracteres não dígitos
        char[] buffer = str.toCharArray();
        char[] digit = new char[buffer.length];
        int j = 0;

        if (offs == 0 && buffer.length > 0 && buffer[0] == '-') {
            digit[j++] = buffer[0];
        }

        for (int i = 0; i < buffer.length; i++) {
            if (Character.isDigit(buffer[i])) {
                digit[j++] = buffer[i];
            }
            if (!hasDot && buffer[i] == ',') {
                digit[j++] = ',';
                hasDot = true;
            }
        }

        // Agora, testar esse novo valor está dentro do alcance 
        String added = new String(digit, 0, j);
        try {
            StringBuilder val = new StringBuilder(curVal);
            val.insert(offs, added);
            String valStr = val.toString();
            if (valStr.equals(",")) {
                super.insertString(offs, added, a);
            } else {
                Double.valueOf(valStr);
                super.insertString(offs, added, a);
            }
        } catch (NumberFormatException e) {
            // Ignore insertion, as it results in an out of range value
        }
    }

    public void setValue(double d) {
        try {
            remove(0, getLength());
            insertString(0, String.valueOf(d), null);
        } catch (BadLocationException e) {
            // Will not happen because we are sure
            // we use the proper range
        }
    }

    public double getValue() {
        try {
            String t = getText(0, getLength());
            if (t != null && t.length() > 0) {
                return Double.parseDouble(t);
            } else {
                return 0;
            }
        } catch (BadLocationException e) {
            // Will not happen because we are sure
            // we use the proper range
            throw new Error(e.getMessage());
        }
    }
}
