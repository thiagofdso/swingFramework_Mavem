package com.brassoftware.framework.utilidades;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

/**
 *
 * @author Karlay
 */
public class EscreveSplash {
    // Objeto instanciado pela JVM para obter a imagem para ser usada
    // como Splash Screen
    private static SplashScreen mySplash;
    // contexto gráfico para ser sobreposta na imagem Splash Screen
    private static Graphics2D splashGraphics;
    // Fonte a ser usada para o texto
    private static Font font;  
    
    /**
     * Prepara as variáveis globais para as outras funções do Splash
     */
    public static void splashInit() {
        // O objeto splash screen é criado pela JVM, e ele será
        // exibido como uma imagem de Splash
        mySplash = SplashScreen.getSplashScreen();

        // Se existem problemas para exibir o splash screen
        // A chamada para getSplashScreen será retornado nulo
        if (mySplash != null) {
            // Criar o ambiente gráfico para desenhar informações de status
            splashGraphics = mySplash.createGraphics();
            font = new Font("Arial", Font.PLAIN, 14);
            splashGraphics.setFont(font);

            // Inicializa as informações de status
            splashText("Iniciando o Aplicativo.");
        }
    }
    
    /**
     * Exibi o texto na área de status do Splash. Nota: nenhuma validação vai se
     * encaixar. Display text in status area of Splash. Note: no validation it
     * will fit.
     *
     * @param texto - Texto a ser exibido
     */
    public static void splashText(String texto) {
        // Importante verificar aqui, se há realmente um Splash sendo exibido
        if (mySplash != null && mySplash.isVisible()) {
            // Apaga o último texto exibido
            splashGraphics.setComposite(AlphaComposite.Clear);
            splashGraphics.fillRect(225, 530, 250, 40);
            splashGraphics.setPaintMode();
            // Desenha o texto
            splashGraphics.setPaint(Color.WHITE);
            splashGraphics.drawString(texto, 225, 540);
            // Força a exibição do texto
            mySplash.update();
        }
    }    
}
