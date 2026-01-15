package org.hlanz.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "personajes")
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "arma_principal", length = 100)
    private String armaPrincipal;

    @Column(name = "nivel_poder")
    private Double nivelPoder;

    @Column(name = "fecha_aparicion")
    private LocalDate fechaAparicion;

    @ManyToOne
    @JoinColumn(name = "raza_id")
    private Raza raza;

    // Constructor vacío (requerido por JPA)
    public Personaje() {
    }

    // Constructor con parámetros
    public Personaje(String nombre, Integer edad, String armaPrincipal, Double nivelPoder, LocalDate fechaAparicion,    Raza raza) {
        this.nombre = nombre;
        this.edad = edad;
        this.armaPrincipal = armaPrincipal;
        this.nivelPoder = nivelPoder;
        this.fechaAparicion = fechaAparicion;
        this.raza = raza;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getArmaPrincipal() {
        return armaPrincipal;
    }

    public void setArmaPrincipal(String armaPrincipal) {
        this.armaPrincipal = armaPrincipal;
    }

    public Double getNivelPoder() {
        return nivelPoder;
    }

    public void setNivelPoder(Double nivelPoder) {
        this.nivelPoder = nivelPoder;
    }

    public LocalDate getFechaAparicion() {
        return fechaAparicion;
    }

    public void setFechaAparicion(LocalDate fechaAparicion) {
        this.fechaAparicion = fechaAparicion;
    }

    public Raza getRaza() {
        return raza;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", armaPrincipal='" + armaPrincipal + '\'' +
                ", nivelPoder=" + nivelPoder +
                ", fechaAparicion=" + fechaAparicion +
                ", raza=" + (raza != null ? raza.getNombre() : "null") +
                '}';
    }
}