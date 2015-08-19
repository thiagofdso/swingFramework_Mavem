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
public class NotasDocument extends PlainDocument {

    private final int parteInteira;
    private final int parteDecimal;
    private final String caracteresValidos;

    public NotasDocument(int parteInteira, int parteDecimal) {
        super();
        this.parteInteira = parteInteira;
        this.parteDecimal = parteDecimal;
        this.caracteresValidos = "0123456789,";
    }

    public NotasDocument(int parteInteira, int parteDecimal, String caracteresValidos) {
        super();
        this.parteInteira = parteInteira;
        this.parteDecimal = parteDecimal;
        this.caracteresValidos = caracteresValidos;
    }

    public NotasDocument() {
        super();
        this.parteInteira = 2;
        this.parteDecimal = 2;
        this.caracteresValidos = "0123456789,";
    }

    /**
     *
     * @param offs quantidade de caraquiteres
     * @param str caractere digitado
     * @param a
     * @throws BadLocationException
     */
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

        if (str == null) {
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!caracteresValidos.contains(String.valueOf(str.charAt(i)))) {
                return;
            }
        }

        // soma o ultimo tamanho da string com a nova digitada
        int totalCaracteres = getLength() + str.length();

        if (getLength() > (parteInteira + parteDecimal)) {
            return;
        }

        boolean ponto = getText(0, getLength()).contains(",");

        if (ponto) {
            if (str.contains(",")) {
                return;
            }
            int pontoIndex = getText(0, getLength()).indexOf(",");
            String decimal = getText(pontoIndex + 1, getLength() - (pontoIndex + 1));
            if (decimal.length() >= parteDecimal) {
                return;
            }
            super.insertString(offs, str, a);
            return;
        }

        /**
         * Teste se o total de numeros inteiros atingiu o seu limite
         */
        if (totalCaracteres >= parteInteira) {
            if (str.contains(",")) {
                super.insertString(offs, str, a);
                return;
            }
            if (totalCaracteres > parteInteira) {
                String newStr = ",";
                super.insertString(offs, newStr.concat(str), a);
                return;
            }
            super.insertString(offs, str, a);
            return;
        }

        super.insertString(offs, str, a);
    }

}
