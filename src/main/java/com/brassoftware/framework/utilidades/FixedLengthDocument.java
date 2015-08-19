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
 *
 * @author Casa
 */
public class FixedLengthDocument extends PlainDocument {

    private int maxLength;

    public FixedLengthDocument(int maxLength) {
        super();
        if (maxLength <= 0) {
            throw new IllegalArgumentException("Você deve especificar um tamanho máximo!");
        }
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null || getLength() == maxLength) {
            return;
        }
        int totalLen = getLength() + str.length();
        if (totalLen <= maxLength) {
            super.insertString(offs, str, a);
            return;
        }
        String newStr = str.substring(0,(maxLength - getLength()));
        super.insertString(offs, newStr, a);
    }

}
