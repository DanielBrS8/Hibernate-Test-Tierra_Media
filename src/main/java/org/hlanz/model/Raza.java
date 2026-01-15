package org.hlanz.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "razas")
public class Raza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "habilidad_especial", length = 150)
    private String habilidadEspecial;

    @Column(name = "reino_origen", length = 100)
    private String reinoOrigen;

    @Column(name = "esperanza_vida")
    private Integer esperanzaVida;

    @OneToMany(mappedBy = "raza", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Personaje> personajes = new ArrayList<>();

    // Constructor vacío (requerido por JPA)
    public Raza() {
    }

    // Constructor con parámetros
    public Raza(String nombre, String habilidadEspecial, String reinoOrigen, Integer esperanzaVida) {
        this.nombre = nombre;
        this.habilidadEspecial = habilidadEspecial;
        this.reinoOrigen = reinoOrigen;
        this.esperanzaVida = esperanzaVida;
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

    public String getHabilidadEspecial() {
        return habilidadEspecial;
    }

    public void setHabilidadEspecial(String habilidadEspecial) {
        this.habilidadEspecial = habilidadEspecial;
    }

    public String getReinoOrigen() {
        return reinoOrigen;
    }

    public void setReinoOrigen(String reinoOrigen) {
        this.reinoOrigen = reinoOrigen;
    }

    public Integer getEsperanzaVida() {
        return esperanzaVida;
    }

    public void setEsperanzaVida    (Integer esperanzaVida) {
        this.esperanzaVida = esperanzaVida;
    }

    public List<org.hlanz.model.Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<org.hlanz.model.Personaje> personajes) {
        this.personajes = personajes;
    }

    @Override
    public String toString() {
        return "Raza{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", habilidadEspecial='" + habilidadEspecial + '\'' +
                ", reinoOrigen='" + reinoOrigen + '\'' +
                ", esperanzaVida=" + esperanzaVida +
                ", cantidadPersonajes=" + personajes.size() +
                '}';
    }
}