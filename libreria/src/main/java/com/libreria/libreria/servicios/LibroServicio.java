/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.repositorios.AutorRepositorio;
import com.libreria.libreria.repositorios.EditorialRepositorio;
import com.libreria.libreria.repositorios.LibroRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author flora
 */
@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    public void crearLibro(String titulo, Integer anio, Integer ejemplares, boolean alta, String idAutor, String idEditorial) throws Excepciones {

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        validarLibro(titulo, anio, ejemplares);

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setAlta(true);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    public void modificarLibro(String idLibro, String idAutor, String idEditorial, String titulo, Integer anio, Integer ejemplares) throws Excepciones {

        validarLibro(titulo, anio, ejemplares);

        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            if (libro.getAutor().getId().equals(idAutor)) {
                if (libro.getEditorial().getId().equals(idEditorial)) {
                    libro.setTitulo(titulo);
                    libro.setAnio(anio);
                    libro.setEjemplares(ejemplares);
                    libroRepositorio.save(libro);

                } else {
                    throw new Excepciones("No tiene permisos suficientes para modificar (Editorial)");
                }
            } else {
                throw new Excepciones("No tiene permisos suficientes(Autor)");
            }
        } else {
            throw new Excepciones("No se encontró el titulo del Libro");
        }

    }

    private void validarLibro(String titulo, Integer anio, Integer ejemplares) throws Excepciones {

        if (titulo == null || titulo.isEmpty()) {
            throw new Excepciones("El titulo del libro no puede ser nulo");
        }

        if (anio == null) {
            throw new Excepciones("Se debe especificar el año de Alta");
        }
        if (ejemplares == null) {
            throw new Excepciones("Debe indicar el número de ejemplares");
        }
    }

    private void validarLibroId(String id) throws Excepciones {

        if (id == null || id.isEmpty()) {
            throw new Excepciones("El titulo del libro no puede ser nulo");
        }
    }

    private void darDeBajaLibro(String idLibro, String idAutor, String idEditorial) throws Excepciones {

        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            if (libro.getAutor().getId().equals(idAutor)) {
                 if (libro.getEditorial().getId().equals(idEditorial)) {
                libro.setAlta(false);
                libroRepositorio.save(libro);
            } else {
                    throw new Excepciones("No tiene permisos suficientes para modificar (Editorial)");
                }
            } else {
                throw new Excepciones("No tiene permisos suficientes(Autor)");
            }
        } else {
            throw new Excepciones("No se encontró el titulo del Libro");
        }

    }

    private void buscarLibroId(String id) throws Excepciones {

        validarLibroId(id);
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            libro.getTitulo();

        } else {
            throw new Excepciones("No se encontró el id del Libro");
        }
    }

    private void buscarLibrotitulo(String titulo) throws Excepciones {

        validarLibroId(titulo);
        Optional<Libro> respuesta = libroRepositorio.findById(titulo);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            libro.getTitulo();

        } else {
            throw new Excepciones("No se encontró el titulo del Libro");
        }
    }

}
