/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.repositorios.AutorRepositorio;
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
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void crearAutor(MultipartFile archivo, String nombre) throws Excepciones {

        validarAutorNombre(nombre);

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);

        Foto foto = fotoServicio.guardar(archivo);
        autor.setFoto(foto);

        autorRepositorio.save(autor);
    }

    @Transactional
    public void modificarAutor(MultipartFile archivo, String id, String nombre) throws Excepciones {

        validarAutorNombre(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.setNombre(nombre);

            String idFoto = null;
            if (autor.getFoto() != null) {
                idFoto = autor.getFoto().getId();
            }
            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            autor.setFoto(foto);

            autorRepositorio.save(autor);
        } else {
            throw new Excepciones("No se encontró el nombre del Autor");
        }
    }

    private void validarAutorNombre(String nombre) throws Excepciones {

        if (nombre == null || nombre.isEmpty()) {
            throw new Excepciones("El nombre del autor no puede ser nulo");
        }
    }

    private void validarAutorId(String id) throws Excepciones {

        if (id == null || id.isEmpty()) {
            throw new Excepciones("El nombre del autor no puede ser nulo");
        }
    }

    @Transactional
    private void darDeBajaAutor(String id) throws Excepciones {

        validarAutorId(id);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.setAlta(false);
            autorRepositorio.save(autor);
        } else {
            throw new Excepciones("No se encontró el id del Autor");
        }
    }

    private void buscarAutorId(String id) throws Excepciones {

        validarAutorId(id);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.getNombre();

        } else {
            throw new Excepciones("No se encontró el id del Autor");
        }
    }

    private void buscarAutornombre(String nombre) throws Excepciones {

        validarAutorNombre(nombre);
        List<Autor> respuesta = autorRepositorio.listarNombresAutores(nombre);
        if (respuesta != null) {
            for (Autor a : respuesta) {
                System.out.println("Nombre: " + a.getNombre());
            }
        } else {
            throw new Excepciones("No se encontró el nombre del Autor");
        }
    }
//@transaccional (readOnly = true)
//    public List<Autor> listarNombresAutores(){
    //    return perroRepository.buscarActivos();
//    }
//public List<Autor> listartodos(){
    //    return perroRepositry.findAll();

//
}
