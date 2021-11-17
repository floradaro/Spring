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
import org.springframework.transaction.annotation.Propagation;
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

//    @Transactional
//    public void modificarAutor(MultipartFile archivo, String id, String nombre) throws Excepciones {
//
//        validarAutorNombre(nombre);
//
//        Optional<Autor> respuesta = autorRepositorio.findById(id);
//        if (respuesta.isPresent()) {
//
//            Autor autor = respuesta.get();
//            autor.setNombre(nombre);
//
//            String idFoto = null;
//            if (autor.getFoto() != null) {
//                idFoto = autor.getFoto().getId();
//            }
//            Foto foto = fotoServicio.actualizar(idFoto, archivo);
//            autor.setFoto(foto);
//
//            autorRepositorio.save(autor);
//        } else {
//            throw new Excepciones("No se encontró el nombre del Autor");
//        }
//    }
    
    @Transactional
    public void modificarAutorID(String id,String nombrenuevo, MultipartFile archivo ) throws Excepciones {
        validarAutorId(id);
        validarAutorNombre(nombrenuevo);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.setNombre(nombrenuevo);
            
            String idFoto = null;
            if (autor.getFoto() != null) {
                idFoto = autor.getFoto().getId();
            }
            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            autor.setFoto(foto);

            autorRepositorio.save(autor);
        } else {
            throw new Excepciones("No se encontró el id del Autor");
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
//-------------BUSQUEDA----------
    public Autor buscarAutorId(String id) throws Excepciones {
        validarAutorId(id);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        Autor autor = null;
        if (respuesta.isPresent()) {
            autor = respuesta.get();
        } else {
            throw new Excepciones("No se encontró el id del Autor");
        }
        return autor;
    }

    public void buscarAutorNombre(String nombre) throws Excepciones {
        validarAutorNombre(nombre);
        
        List<Autor> respuesta = autorRepositorio.listarNombresAutores(nombre);
        if (respuesta != null) {
            for (Autor a : respuesta) {
                a.getNombre();
            }
        } else {
            throw new Excepciones("No se encontró el nombre del Autor");
        }
    }
 @Transactional
    public List<Autor> listarAutores() throws Exception{
        List<Autor> autores = autorRepositorio.listarAutores();
        
        return autores;
    }
    //---------------ALYA Y BAJA ------------
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    
    public void darDeBaja(String id) throws Excepciones {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(Boolean.FALSE);
            autorRepositorio.save(autor);
        } else {
            throw new Excepciones("No se encontro el autor");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void darDeAlta(String id) throws Excepciones {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(Boolean.TRUE);
            autorRepositorio.save(autor);
        } else {
            throw new Excepciones("No se encontro el autor");
        }
    }
    
    @Transactional(readOnly=true)
    public Autor getOne(String id){
        return autorRepositorio.getOne(id);
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listarAltas() {
        return autorRepositorio.buscarActivos();
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listarBajas() {
        return autorRepositorio.buscarBajas();
    }

    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        return autorRepositorio.findAll();
    }
    
    
}
