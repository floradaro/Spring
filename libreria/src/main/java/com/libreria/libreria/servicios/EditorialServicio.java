/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.repositorios.EditorialRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author flora
 */
@Service
public class EditorialServicio {
    
     @Autowired
    private EditorialRepositorio editorialRepositorio;
     
     public void crearEditorial(String nombre, boolean alta) throws Excepciones {

        validarEditorialNombre(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);

        editorialRepositorio.save(editorial);
    }

    public void modificarEditorial(String id, String nombre) throws Excepciones {

        validarEditorialNombre(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);
        }else{
            throw new Excepciones("No se encontró el nombre del Editorial");
        }
    }

    private void validarEditorialNombre(String nombre) throws Excepciones {

        if (nombre == null || nombre.isEmpty()) {
            throw new Excepciones("El nombre del editorial no puede ser nulo");
        }
    }
    
      private void validarEditorialId(String id) throws Excepciones {

        if (id== null || id.isEmpty()) {
            throw new Excepciones("El nombre del editorial no puede ser nulo");
        }
    }
    
    private void darDeBajaEditorial (String id) throws Excepciones{
        
        validarEditorialId(id);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.setAlta(false);

            editorialRepositorio.save(editorial);
        }else{
            throw new Excepciones("No se encontró el id del Editorial");
        }
    }
    
    private void buscarEditorialId (String id) throws Excepciones{
      
        validarEditorialId(id);
       Optional<Editorial> respuesta = editorialRepositorio.findById(id);
       if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.getNombre();

        }else{
            throw new Excepciones("No se encontró el id del Editorial");
        }
    }
    
    private void buscarEditorialnombre (String nombre) throws Excepciones{
      
        validarEditorialId(nombre);
       Optional<Editorial> respuesta = editorialRepositorio.findById(nombre);
       if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.getNombre();

        }else{
            throw new Excepciones("No se encontró el nombre del Editorial");
        }
    }
}
