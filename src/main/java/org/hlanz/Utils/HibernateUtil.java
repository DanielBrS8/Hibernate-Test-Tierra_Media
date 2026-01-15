package org.hlanz.Utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    /*
    La SessionFactory es una fábrica de hilos seguros (thread-safe)
    y un objeto "pesado" que se crea una sola vez durante el inicio de la aplicación.

    Función: Lee el archivo de configuración (hibernate.cfg.xml)
    y mapea las clases Java con las tablas de la base de datos.

    Se crea una sola vez por aplicación; es la "fábrica" de conexiones.
     */
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static SessionFactory buildSessionFactory() {
        try {
            // Crea SessionFactory desde hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Error al crear SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}