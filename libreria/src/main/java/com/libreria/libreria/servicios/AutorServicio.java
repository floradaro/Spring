/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.repositorios.AutorRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author flora
 */
@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    public void crearAutor(String nombre, boolean alta) throws Excepciones {

        validarAutorNombre(nombre);

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);

        autorRepositorio.save(autor);
    }

    public void modificarAutor(String id, String nombre) throws Excepciones {

        validarAutorNombre(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.setNombre(nombre);

            autorRepositorio.save(autor);
        }else{
            throw new Excepciones("No se encontró el nombre del Autor");
        }
    }

    private void validarAutorNombre(String nombre) throws Excepciones {

        if (nombre == null || nombre.isEmpty()) {
            throw new Excepciones("El nombre del autor no puede ser nulo");
        }
    }
    
      private void validarAutorId(String id) throws Excepciones {

        if (id== null || id.isEmpty()) {
            throw new Excepciones("El nombre del autor no puede ser nulo");
        }
    }
    
    private void darDeBajaAutor (String id) throws Excepciones{
        
        validarAutorId(id);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.setAlta(false);

            autorRepositorio.save(autor);
        }else{
            throw new Excepciones("No se encontró el id del Autor");
        }
    }
    
    private void buscarAutorId (String id) throws Excepciones{
      
        validarAutorId(id);
       Optional<Autor> respuesta = autorRepositorio.findById(id);
       if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.getNombre();

        }else{
            throw new Excepciones("No se encontró el id del Autor");
        }
    }
    
    private void buscarAutornombre (String nombre) throws Excepciones{
      
        validarAutorNombre(nombre);
       Optional<Autor> respuesta = autorRepositorio.findById(nombre);
       if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.getNombre();
        }else{
            throw new Excepciones("No se encontró el nombre del Autor");
        }
    }
}
