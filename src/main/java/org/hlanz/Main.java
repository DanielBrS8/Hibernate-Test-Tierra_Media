package org.hlanz;

import org.hlanz.Utils.HibernateUtil;
import org.hlanz.dao.PersonajeDAO;

import org.hlanz.dao.RazaDAO;
import org.hlanz.model.Personaje;
import org.hlanz.model.Raza;

import java.time.LocalDate;
import java.util.List;

// ============================================
// PASOS PARA EJECUTAR:
// ============================================
/*
1. Crear base de datos en PostgreSQL:
   CREATE DATABASE personajes_db;

2. Las tablas se crearán automáticamente gracias a hibernate.hbm2ddl.auto=update

3. Estructura del proyecto:
   hibernate-personajes/
   ├── pom.xml
   └── src/
       └── main/
           ├── java/
           │   └── org/
           │       └── hlanz/
           │           ├── Main.java
           │           ├── model/
           │           │   ├── Personaje.java
           │           │   └── Raza.java
           │           ├── dao/
           │           │   ├── PersonajeDAO.java
           │           │   └── RazaDAO.java
           │           └── Utils/
           │               └── HibernateUtil.java
           └── resources/
               └── hibernate.cfg.xml

4. Ejecutar:
   mvn clean install
   mvn exec:java -Dexec.mainClass="org.hlanz.Main"
*/

public class Main {

    public static void main(String[] args) {

        RazaDAO razaDAO = new RazaDAO();
        PersonajeDAO personajeDAO = new PersonajeDAO();

        System.out.println("=== DEMO HIBERNATE - PERSONAJES ===\n");

        // 1. CREATE - Insertar razas
        System.out.println("1. Insertando razas...");
        Raza hobbit = new Raza("Hobbit", "Resistencia y sigilo", "La Comarca", 100);
        Raza elfo = new Raza("Elfo", "Visión aguda e inmortalidad", "Bosque Negro", 10000);
        Raza humano = new Raza("Humano", "Adaptabilidad y determinación", "Gondor", 80);
        Raza enano = new Raza("Enano", "Fuerza y habilidad en minería", "Moria", 250);

        razaDAO.guardar(hobbit);
        razaDAO.guardar(elfo);
        razaDAO.guardar(humano);
        razaDAO.guardar(enano);
        System.out.println();

        // 2. CREATE - Insertar personajes (mínimo 2 por raza)
        System.out.println("2. Insertando personajes...");

        // Hobbits
        Personaje frodo = new Personaje("Frodo Bolsón", 50, "Dardo", 45.0, LocalDate.of(2968, 9, 22), hobbit);
        Personaje sam = new Personaje("Samsagaz Gamyi", 38, "Espada corta", 35.0, LocalDate.of(2980, 4, 6), hobbit);

        // Elfos
        Personaje legolas = new Personaje("Legolas", 2931, "Arco élfico", 88.0, LocalDate.of(87, 1, 1), elfo);
        Personaje arwen = new Personaje("Arwen", 2778, "Espada élfica", 75.0, LocalDate.of(241, 1, 1), elfo);

        // Humanos
        Personaje aragorn = new Personaje("Aragorn", 87, "Andúril", 92.0, LocalDate.of(2931, 3, 1), humano);
        Personaje boromir = new Personaje("Boromir", 41, "Espada y escudo", 78.0, LocalDate.of(2978, 1, 1), humano);

        // Enanos
        Personaje gimli = new Personaje("Gimli", 139, "Hacha de batalla", 82.0, LocalDate.of(2879, 1, 1), enano);
        Personaje thorin = new Personaje("Thorin Escudo de Roble", 195, "Orcrist", 85.0, LocalDate.of(2746, 1, 1), enano);

        personajeDAO.guardar(frodo);
        personajeDAO.guardar(sam);
        personajeDAO.guardar(legolas);
        personajeDAO.guardar(arwen);
        personajeDAO.guardar(aragorn);
        personajeDAO.guardar(boromir);
        personajeDAO.guardar(gimli);
        personajeDAO.guardar(thorin);
        System.out.println();

        // 3. READ - Consulta de todos los personajes
        System.out.println("3. Listando todos los personajes:");
        List<Personaje> todosPersonajes = personajeDAO.obtenerTodos();
        todosPersonajes.forEach(System.out::println);
        System.out.println();

        // 4. Búsqueda de personajes por raza
        System.out.println("4. Buscando personajes de raza Elfo:");
        List<Personaje> elfos = personajeDAO.buscarPorRaza(elfo.getId());
        elfos.forEach(System.out::println);
        System.out.println();

        // 5. Búsqueda de personajes por rango de nivel de poder
        System.out.println("5. Buscando personajes con nivel de poder entre 70 y 90:");
        List<Personaje> personajesRango = personajeDAO.buscarPorRangoNivelPoder(70.0, 90.0);
        personajesRango.forEach(System.out::println);
        System.out.println();

        // 6. UPDATE - Actualización del nivel de poder de un personaje
        System.out.println("6. Actualizando nivel de poder de Frodo...");
        Personaje frodoDB = personajeDAO.obtenerPorId(frodo.getId());
        if (frodoDB != null) {
            frodoDB.setNivelPoder(65.0);
            personajeDAO.actualizar(frodoDB);
            System.out.println("Frodo después de actualizar: " + personajeDAO.obtenerPorId(frodo.getId()));
        }
        System.out.println();

        // 7. DELETE - Eliminación de un personaje
        System.out.println("7. Eliminando personaje Boromir...");
        personajeDAO.eliminar(boromir.getId());
        System.out.println();

        // 8. Listado de personajes de una raza específica
        System.out.println("8. Listando personajes de raza Enano:");
        List<Personaje> enanos = personajeDAO.buscarPorRaza(enano.getId());
        enanos.forEach(System.out::println);
        System.out.println();

        // 9. Mostrar los 5 personajes más poderosos
        System.out.println("9. Top 5 personajes más poderosos:");
        List<Personaje> masPoderosos = personajeDAO.obtenerMasPoderosos(5);
        masPoderosos.forEach(System.out::println);
        System.out.println();

        // BONUS: Búsqueda por arma
        System.out.println("BONUS - Buscando personajes que usan Hacha:");
        List<Personaje> personajesHacha = personajeDAO.buscarPorArma("Hacha de batalla");
        personajesHacha.forEach(System.out::println);
        System.out.println();

        // BONUS: Obtener raza con todos sus personajes
        System.out.println("BONUS - Obteniendo raza Hobbit con todos sus personajes:");
        Raza hobbitCompleto = razaDAO.obtenerConPersonajes(hobbit.getId());
        System.out.println(hobbitCompleto);
        System.out.println("Personajes de esta raza:");
        hobbitCompleto.getPersonajes().forEach(p -> System.out.println("  - " + p.getNombre()));
        System.out.println();

        // 10. CONSULTAS ADICIONALES
        System.out.println("=== CONSULTAS ADICIONALES ===\n");

        // a) Obtener estadísticas por raza
        System.out.println("10a. Estadísticas de personajes por raza:");
        List<Object[]> estadisticas = razaDAO.obtenerEstadisticasPorRaza();
        estadisticas.forEach(stat -> {
            String nombreRaza = (String) stat[0];
            Long cantidad = (Long) stat[1];
            System.out.println("  " + nombreRaza + ": " + cantidad + " personaje(s)");
        });
        System.out.println();

        // b) Obtener raza más numerosa
        System.out.println("10b. Raza con más personajes:");
        Raza razaMasNumerosa = razaDAO.obtenerRazaMasNumerosa();
        if (razaMasNumerosa != null) {
            System.out.println("  " + razaMasNumerosa.getNombre() +
                    " con " + razaMasNumerosa.getPersonajes().size() + " personajes");
        }
        System.out.println();

        // c) Buscar personajes antiguos (más de 100 años)
        System.out.println("10c. Personajes con más de 100 años:");
        List<Personaje> personajesAntiguos = personajeDAO.buscarPersonajesAntiguos(100);
        personajesAntiguos.forEach(p ->
                System.out.println("  " + p.getNombre() + " - " + p.getEdad() + " años")
        );
        System.out.println();

        // Cerrar SessionFactory AL FINAL DE TODO
        HibernateUtil.shutdown();
        System.out.println("=== FIN DEMO ===");
    }
}