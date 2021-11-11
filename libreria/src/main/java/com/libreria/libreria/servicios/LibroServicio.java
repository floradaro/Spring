/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.repositorios.AutorRepositorio;
import com.libreria.libreria.repositorios.EditorialRepositorio;
import com.libreria.libreria.repositorios.LibroRepositorio;
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
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void crearLibro(MultipartFile archivo, String titulo, Integer anio, Integer ejemplares, boolean alta, String idAutor, String idEditorial) throws Excepciones {

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

        Foto foto = fotoServicio.guardar(archivo);
        libro.setFoto(foto);

        libroRepositorio.save(libro);
    }

    @Transactional
    public Libro creaLibro(String titulo, Integer anio, Integer ejemplares, boolean alta, String idAutor, String idEditorial) throws Excepciones {

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
        return libro;
    }

    @Transactional
    public void modificarLibro(MultipartFile archivo, String idLibro, String idAutor, String idEditorial, String titulo, Integer anio, Integer ejemplares) throws Excepciones {

        validarLibro(titulo, anio, ejemplares);

        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            if (libro.getAutor().getId().equals(idAutor)) {
                if (libro.getEditorial().getId().equals(idEditorial)) {
                    libro.setTitulo(titulo);
                    libro.setAnio(anio);
                    libro.setEjemplares(ejemplares);

                    String idFoto = null;
                    if (libro.getFoto() != null) {
                        idFoto = libro.getFoto().getId();
                    }

                    Foto foto = fotoServicio.actualizar(idFoto, archivo);
                    libro.setFoto(foto);

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
    
    //------MODIFICACIONES
    @Transactional
    public void modificarLibro(String idLibro,String titulo,Integer anio,Integer ejemplares,String idautor, String ideditorial) throws Excepciones {
        
        validarLibro(titulo, anio, ejemplares);
        Autor autor = autorRepositorio.findById(idautor).get();
        Editorial editorial = editorialRepositorio.findById(ideditorial).get();

        Libro libro = buscarLibroId(idLibro);
        if(libro != null){
            
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            
            libroRepositorio.save(libro);
        } else {
            throw new Excepciones("No se encontro el Libro");
        }
    }
    private void validarLibro(String titulo, Integer anio, Integer ejemplares) throws Excepciones {

        if (titulo == null || titulo.isEmpty()) {
            throw new Excepciones("El titulo del libro no puede ser nulo");
        }
        if (anio == null || anio <= 0 ) {
            throw new Excepciones("Se debe especificar el año de Alta");
        }
        if (ejemplares == null || ejemplares <=0) {
            throw new Excepciones("Debe indicar el número de ejemplares");
        }
    }

    private void validarLibroId(String id) throws Excepciones {

        if (id == null || id.isEmpty()) {
            throw new Excepciones("El titulo del libro no puede ser nulo");
        }
    }

//    @Transactional
//    private void darDeBajaLibro(String idLibro, String idAutor, String idEditorial) throws Excepciones {
//
//        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
//        if (respuesta.isPresent()) {
//            Libro libro = respuesta.get();
//            if (libro.getAutor().getId().equals(idAutor)) {
//                if (libro.getEditorial().getId().equals(idEditorial)) {
//                    libro.setAlta(false);
//                    libroRepositorio.save(libro);
//                } else {
//                    throw new Excepciones("No tiene permisos suficientes para modificar (Editorial)");
//                }
//            } else {
//                throw new Excepciones("No tiene permisos suficientes(Autor)");
//            }
//        } else {
//            throw new Excepciones("No se encontró el titulo del Libro");
//        }
//
//    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Libro alta(String id) {

        Libro entidad = libroRepositorio.getOne(id);

        entidad.setAlta(true);
        return libroRepositorio.save(entidad);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Libro baja(String id) {

        Libro entidad = libroRepositorio.getOne(id);

        entidad.setAlta(false);
        return libroRepositorio.save(entidad);
    }
   //------BUSQUEDA----------------
    public Libro buscarLibroId(String id) throws Excepciones {
        validarLibroId(id);
        
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        Libro libro = null;
        if (respuesta.isPresent()) {
            libro = respuesta.get();
        } else {
//            throw new Excepcion("No se encontró el id del Libro");
        }
        return libro;
    }

    private void buscarLibrotitulo(String id) throws Excepciones {

        validarLibroId(id);
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            libro.getTitulo();

        } else {
            throw new Excepciones("No se encontró el titulo del Libro");
        }
    }
    
    @Transactional(readOnly=true)
    public Libro getOne(String id){
        return libroRepositorio.getOne(id);
    }
    

    @Transactional(readOnly = true)
    public List<Libro> listarActivos() {
        return libroRepositorio.buscarActivos();
    }

    @Transactional(readOnly = true)
    public List<Libro> listarTodos() {
        return libroRepositorio.findAll();
    }

    public void imprimirLibros() throws Exception {
        List<Libro> respuesta = libroRepositorio.buscarLibrosPorTituloAlta();
        for (Libro libro : respuesta) {
            if (respuesta != null) {
                for (Libro a : respuesta) {
                    System.out.println("Nombre: " + a.getTitulo());
                }
            } else {
                throw new Excepciones("No se encontró el nombre del Autor");
            }
        }
    }
    //----------------PRESTAMOS-----------------------
    @Transactional
    public void prestarLibro(String idLibro,Integer ejemplaresPresta) throws Excepciones{
        validarLibroId(idLibro);
        
        Libro libro = buscarLibroId(idLibro);
        
        if(libro!=null){
            if(libro.getEjemplaresRestantes()<=0){
                throw new Excepciones("No quedan libros para prestar");
            } if(ejemplaresPresta<libro.getEjemplaresRestantes()){
                libro.setEjemplaresRestantes(libro.getEjemplaresRestantes()-ejemplaresPresta);
                libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()+ejemplaresPresta);
            } else {
                throw new Excepciones("No hay suficientes libros para prestar");
            }
        }
        libroRepositorio.save(libro);
    }
    //----------------DEVOLUCIONES-----------------------
    @Transactional
    public void devolverLibro(String idLibro,Integer ejemplaresVuelta) throws Excepciones{
        validarLibroId(idLibro);
        
        Libro libro = buscarLibroId(idLibro);
        
        Integer valor = libro.getEjemplaresRestantes()+ejemplaresVuelta;
        
        if(libro!=null){
            if(libro.getEjemplares()>=valor){
                libro.setEjemplaresRestantes(libro.getEjemplaresRestantes()+ejemplaresVuelta);
                libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()-ejemplaresVuelta);
            } else {
                throw new Excepciones("Se estan devolviendo mas libros que los prestados");
            }
        }
        libroRepositorio.save(libro);
    }
}
