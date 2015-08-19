/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.utilidades;

import java.math.BigInteger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Casa
 */
public class BigIntegerDocument extends FixedLengthDocument {

    public BigIntegerDocument(int maxLength) {
        super(maxLength);
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) {
            return;
        }
        try {
            new BigInteger(str);
        } catch (Exception e) {
            return;
        }
        super.insertString(offs, str, a);
    }
    
}
