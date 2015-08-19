/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brassoftware.framework.utilidades;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Casa
 */
public class PadraoDocument extends FixedLengthDocument {

    private final String caracteresValidos;

    public PadraoDocument() {
        super(8);
        this.caracteresValidos = "0123456789.-";
    }

    public PadraoDocument(int maxLength) {
        super(maxLength);
        this.caracteresValidos = "0123456789.-";
    }

    public PadraoDocument(int maxLength, String caracteresValidos) {
        super(maxLength);
        this.caracteresValidos = caracteresValidos;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        for (int i = 0; i < str.length(); i++) {
            if (!caracteresValidos.contains(String.valueOf(str.charAt(i)))) {
                return;
            }
        }
        super.insertString(offs, str, a); //To change body of generated methods, choose Tools | Templates.
    }

}
