/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author flora
 */
@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void crearEditorial(MultipartFile archivo, String nombre) throws Excepciones {

        validarEditorialNombre(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);

        Foto foto = fotoServicio.guardar(archivo);
        editorial.setFoto(foto);

        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void modificarEditorial(MultipartFile archivo, String id, String nombre) throws Excepciones {

        validarEditorialNombre(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);

            String idFoto = null;
            if (editorial.getFoto() != null) {
                idFoto = editorial.getFoto().getId();
            }

            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            editorial.setFoto(foto);

            editorialRepositorio.save(editorial);
        } else {
            throw new Excepciones("No se encontró el nombre del Editorial");
        }
    }
    
    @Transactional
    public void modificarEditorialId(String id, String nombrenuevo) throws Excepciones {
        validarEditorialId(id);
        validarEditorialNombre(nombrenuevo);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombrenuevo);
            editorialRepositorio.save(editorial);
        } else {
            throw new Excepciones("No se encontró el id del Editorial");
        }
    }

    private void validarEditorialNombre(String nombre) throws Excepciones {

        if (nombre == null || nombre.isEmpty()) {
            throw new Excepciones("El nombre del editorial no puede ser nulo");
        }
    }

    private void validarEditorialId(String id) throws Excepciones {

        if (id == null || id.isEmpty()) {
            throw new Excepciones("El nombre del editorial no puede ser nulo");
        }
    }

    @Transactional
    private void darDeBajaEditorial(String id) throws Excepciones {

        validarEditorialId(id);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.setAlta(false);

            editorialRepositorio.save(editorial);
        } else {
            throw new Excepciones("No se encontró el id del Editorial");
        }
    }

    public Editorial buscarEditorialId(String id) throws Excepciones {
        validarEditorialId(id);
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        Editorial editorial= respuesta.get();
        if (respuesta.isPresent()) {
            return editorial;
        } else {
            throw new Excepciones("No se encontró el id del Editorial");
        }
    }

    private void buscarEditorialnombre(String nombre) throws Excepciones {

        validarEditorialNombre(nombre);
        List<Editorial> respuesta = editorialRepositorio.listarNombresEditorial(nombre);
        if (respuesta!= null) {
            for (Editorial a : respuesta) {
                System.out.println("Nombre: " + a.getNombre());
            }
        } else {
            throw new Excepciones("No se encontró el nombre de la Editorial");
        }
    }
}
