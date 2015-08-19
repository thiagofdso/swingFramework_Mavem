package com.brassoftware.framework.utilidades;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Durante a execução do projeto é possível trabalhar com várias sessões com o
 * banco de dados. Na JPA a sessão com o banco é representa pelo componente
 * EntityManager.
 *
 * <p>
 * Essa classe é responsavel por disponibilizar o(s) componente(s)
 * <code>EntityManager</code>. Utilizamos o <code>EntityManagerFactory</code>
 * fornecedor (fábrica) de sessões com o banco de dados.</p>
 *
 * <p>
 * Caso não consiga carregar a fabrica de sessões, problemas com a conexão com
 * banco ou não encontrou configurador, a execução da aplicação é interrompida
 * (lança <code>ExceptionInInitializerError</code>).</p>
 *
 * <p>
 * Essa classe usa o padrão de projeto <code>Singleton</code>. </p>
 *
 * @version 1.0.0
 *
 * @author Karlay
 */
public class FabricaEntidades {

    /* Unidade de persistencia definida no arquivo 
     * persistence.xml. */
    private static final String PERSISTENCE_UNIT_NAME = "frameworkPU";

    private static EntityManagerFactory emf;
    private static Connection connection;

    /**
     * Cria uma única fábrica de componentes <code>EntityManager</code> para
     * todo o sitema.
     */
    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Throwable ex) {
            System.err.println("Não conseguiu carregar a EntityManagerFactory: " + ex.getMessage());
            //throw new ExceptionInInitializerError(ex);
        }
    }

    private static boolean criar() {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            return true;
        } catch (Throwable ex) {
            System.err.println("Não conseguiu carregar a EntityManagerFactory: " + ex.getMessage());
            //throw new ExceptionInInitializerError(ex);                     
        }
        return false;
    }

    /**
     * @return Cria e retorna um componente <code>EntityManager</code>.
     * @throws RuntimeException lança se <code>EntityManagerFactory</code>
     * estiver fechada.
     */
    public static EntityManager createEntityManager() {
        if (emf == null || !emf.isOpen()) {
            System.err.println("EMF nulo...");
            if (conectado()) {
                System.err.println("Tentou criar...");
                criar();
            } else {
                return null;
            }
        }
//        if (!emf.isOpen()) {
//            throw new RuntimeException("EntityManagerFactory está fechada!");
//        }
        
        return emf.createEntityManager();
    }

    /**
     * Fecha o <code>EntityManagerFactory</code>.
     */
    public static void closeEntityManagerFactory() {
        // Verifica se emf foi criada
        if (emf != null) {
            // Verifica se ela por algum motivo foi fechada
            if (emf.isOpen()) {
                emf.close();
                System.err.println("Fechando a fabrica de Entidades");
            }
        }
    }

    public static boolean internet() {
        try {
            // URL do destino escolhido
            URL url = new URL("http://www.yahoo.com");

            // abre a conexão
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();

               // tenta buscar conteúdo da URL
            // se não tiver conexão, essa linha irá falhar
            Object objData = urlConnect.getContent();

        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean conectado() {
        try {
            String driver = emf.getProperties().get("javax.persistence.jdbc.driver").toString();
            String url = emf.getProperties().get("javax.persistence.jdbc.url").toString();
            String user = emf.getProperties().get("javax.persistence.jdbc.user").toString();
            String password = emf.getProperties().get("javax.persistence.jdbc.password").toString();
            if (connection == null || connection.isClosed()) {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            }
            return connection.isValid(500);
        } catch (ClassNotFoundException | SQLException e) {
            return false;
        }
    }
}
