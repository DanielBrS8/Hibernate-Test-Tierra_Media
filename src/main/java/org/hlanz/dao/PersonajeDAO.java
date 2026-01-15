package org.hlanz.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hlanz.Utils.HibernateUtil;
import org.hlanz.model.Personaje;

import java.util.List;

public class PersonajeDAO {

    // CREATE - Guardar personaje
    public void guardar(Personaje personaje) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(personaje);
            transaction.commit();
            System.out.println("Personaje guardado exitosamente");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // READ - Obtener personaje por ID
    public Personaje obtenerPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Personaje.class, id);
        }
    }

    // READ - Obtener todos los personajes
    public List<Personaje> obtenerTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Personaje", Personaje.class).list();
        }
    }

    // UPDATE - Actualizar personaje
    public void actualizar(Personaje personaje) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(personaje);
            transaction.commit();
            System.out.println("Personaje actualizado exitosamente");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // DELETE - Eliminar personaje
    public void eliminar(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Personaje personaje = session.get(Personaje.class, id);
            if (personaje != null) {
                session.delete(personaje);
                System.out.println("Personaje eliminado exitosamente");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Buscar personajes por raza
    public List<Personaje> buscarPorRaza(Long razaId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Personaje WHERE raza.id = :razaId", Personaje.class)
                    .setParameter("razaId", razaId)
                    .list();
        }
    }

    // Buscar personajes por rango de nivel de poder
    public List<Personaje> buscarPorRangoNivelPoder(Double min, Double max) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Personaje WHERE nivelPoder BETWEEN :min AND :max",
                            Personaje.class)
                    .setParameter("min", min)
                    .setParameter("max", max)
                    .list();
        }
    }

    // Buscar personajes por arma
    public List<Personaje> buscarPorArma(String arma) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Personaje WHERE armaPrincipal = :arma", Personaje.class)
                    .setParameter("arma", arma)
                    .list();
        }
    }

    // Obtener los N personajes más poderosos
    public List<Personaje> obtenerMasPoderosos(int limite) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Personaje ORDER BY nivelPoder DESC",
                            Personaje.class)
                    .setMaxResults(limite)
                    .list();
        }
    }

    //PARTE ADICIONAL

    // Buscar personajes con más de X años de edad
    public List<Personaje> buscarPersonajesAntiguos(int años) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Personaje WHERE edad > :años ORDER BY edad DESC",
                            Personaje.class)
                    .setParameter("años", años)
                    .list();
        }
    }
}