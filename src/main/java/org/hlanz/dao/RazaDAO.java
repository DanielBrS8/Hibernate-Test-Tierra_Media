package org.hlanz.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hlanz.Utils.HibernateUtil;
import org.hlanz.model.Raza;

import java.util.List;

public class RazaDAO {

    // CREATE - Guardar raza
    public void guardar(Raza raza) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(raza);
            transaction.commit();
            System.out.println("Raza guardada exitosamente");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // READ - Obtener raza por ID
    public Raza obtenerPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Raza.class, id);
        }
    }

    // READ - Obtener todas las razas
    public List<Raza> obtenerTodas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Raza", Raza.class).list();
        }
    }

    // UPDATE - Actualizar raza
    public void actualizar(Raza raza) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(raza);
            transaction.commit();
            System.out.println("Raza actualizada exitosamente");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // DELETE - Eliminar raza
    public void eliminar(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Raza raza = session.get(Raza.class, id);
            if (raza != null) {
                session.delete(raza);
                System.out.println("Raza eliminada exitosamente");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Buscar razas por reino
    public List<Raza> buscarPorReino(String reino) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Raza WHERE reinoOrigen = :reino", Raza.class)
                    .setParameter("reino", reino)
                    .list();
        }
    }

    // Obtener raza con todos sus personajes (carga eager)
    public Raza obtenerConPersonajes(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT r FROM Raza r LEFT JOIN FETCH r.personajes WHERE r.id = :id",
                            Raza.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    // MÉTODOS ADICIONALES

    // Obtener estadísticas: cuenta cuántos personajes hay de cada raza
    public List<Object[]> obtenerEstadisticasPorRaza() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT r.nombre, COUNT(p.id) " +
                                    "FROM Raza r LEFT JOIN r.personajes p " +
                                    "GROUP BY r.id, r.nombre " +
                                    "ORDER BY COUNT(p.id) DESC",
                            Object[].class)
                    .list();
        }
    }

    // Obtener la raza con más personajes
    public Raza obtenerRazaMasNumerosa() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Paso 1: Obtener el ID de la raza con más personajes
            Long razaId = session.createQuery(
                            "SELECT r.id FROM Raza r LEFT JOIN r.personajes p " +
                                    "GROUP BY r.id " +
                                    "ORDER BY COUNT(p.id) DESC",
                            Long.class)
                    .setMaxResults(1)
                    .uniqueResult();

            // Paso 2: Obtener la raza completa con sus personajes
            if (razaId != null) {
                return session.createQuery(
                                "SELECT r FROM Raza r LEFT JOIN FETCH r.personajes WHERE r.id = :id",
                                Raza.class)
                        .setParameter("id", razaId)
                        .uniqueResult();
            }
            return null;
        }
    }
}