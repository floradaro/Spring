package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Cliente;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.entidades.Prestamo;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.repositorios.AutorRepositorio;
import com.libreria.libreria.repositorios.ClienteRepositorio;
import com.libreria.libreria.repositorios.EditorialRepositorio;
import com.libreria.libreria.repositorios.LibroRepositorio;
import com.libreria.libreria.repositorios.PrestamoRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private PrestamoServicio prestamoServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    

    //----------CREAR lIBRO-----------
    
    @Transactional
    public void crearLibro(MultipartFile archivo, String titulo, Integer anio, Integer ejemplares, boolean alta, String idAutor, String idEditorial) throws Excepciones {

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        validarLibro(titulo, anio, ejemplares);

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresRestantes(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setAlta(true);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        Foto foto = fotoServicio.guardar(archivo);
        libro.setFoto(foto);

        libroRepositorio.save(libro);
    }

//    @Transactional
//    public void modificarLibro(MultipartFile archivo, String idLibro, String idAutor, String idEditorial, String titulo, Integer anio, Integer ejemplares) throws Excepciones {
//
//        validarLibro(titulo, anio, ejemplares);
//
//        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
//        if (respuesta.isPresent()) {
//
//            Libro libro = respuesta.get();
//            if (libro.getAutor().getId().equals(idAutor)) {
//                if (libro.getEditorial().getId().equals(idEditorial)) {
//                    libro.setTitulo(titulo);
//                    libro.setAnio(anio);
//                    libro.setEjemplares(ejemplares);
//                    libro.setEjemplaresRestantes(ejemplares);
//                    libro.setEjemplaresPrestados(0);
//
//                    String idFoto = null;
//                    if (libro.getFoto() != null) {
//                        idFoto = libro.getFoto().getId();
//                    }
//                    Foto foto = fotoServicio.actualizar(idFoto, archivo);
//                    libro.setFoto(foto);
//
//                    libroRepositorio.save(libro);
//
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

    //------MODIFICACIONES----------------
    @Transactional
    public void modificarLibro(String idLibro, String titulo, Integer anio, Integer ejemplares, String idautor, String ideditorial, MultipartFile archivo) throws Excepciones {

        Autor autor = autorRepositorio.findById(idautor).get();
        Editorial editorial = editorialRepositorio.findById(ideditorial).get();

        validarLibro(titulo, anio, ejemplares);

        Libro libro = buscarLibroId(idLibro);
        if (libro != null) {

            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresRestantes(ejemplares);
            libro.setEjemplaresPrestados(0);
            libro.setAlta(true);

            String idFoto = null;
            if (libro.getFoto() != null) {
                idFoto = libro.getFoto().getId();
            }

            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            libro.setFoto(foto);

            libroRepositorio.save(libro);
        } else {
            throw new Excepciones("No se encontro el Libro");
        }
    }
    
    //-----------VALIDACIONES---------

    private void validarLibro(String titulo, Integer anio, Integer ejemplares) throws Excepciones {

        if (titulo == null || titulo.isEmpty()) {
            throw new Excepciones("El titulo del libro no puede ser nulo");
        }
        if (anio == null || anio <= 0) {
            throw new Excepciones("Se debe especificar el año de Alta");
        }
        if (ejemplares == null || ejemplares < 0) {
            throw new Excepciones("Debe indicar el número de ejemplares");
        }
    }

    private void validarEjemplaresPrestados(Integer ejemplaresPrestados) throws Excepciones {
        if (ejemplaresPrestados == null || ejemplaresPrestados < 0) {
            throw new Excepciones("Debe indicar el número de ejemplares");
        }
    }

    private void validarLibroId(String id) throws Excepciones {

        if (id == null || id.isEmpty()) {
            throw new Excepciones("El titulo del libro no puede ser nulo");
        }
    }
       
    private void validarPrestamoId(String idCliente) throws Excepciones {
        
        if (idCliente == null || idCliente.isEmpty()) {
            throw new Excepciones("El nombre del cliente no puede ser nulo");
        }
    }

    //----------ALTA Y BAJA----------
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})

    public void darDeBaja(String id) throws Excepciones {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(Boolean.FALSE);
            libroRepositorio.save(libro);
        } else {
            throw new Excepciones("No se encontro el libro");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void darDeAlta(String id) throws Excepciones {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(Boolean.TRUE);
            libroRepositorio.save(libro);
        } else {
            throw new Excepciones("No se encontro el libro");
        }
    }

    //------BUSQUEDA----------------
    public Libro buscarLibroId(String id) throws Excepciones {
        validarLibroId(id);

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        Libro libro = null;
        if (respuesta.isPresent()) {
            libro = respuesta.get();
        } else {
            throw new Excepciones("No se encontró el id del Libro");
        }
        return libro;
    }
    
    public List<Libro> buscarPorParametro(String titulo) {
		return libroRepositorio.searchAssetsByParam(titulo);
	}

    @Transactional
    public List<Libro> busquedaLibroNombre(String titulo) throws Excepciones {       
        List<Libro> respuesta = libroRepositorio.buscarLibrosPorTitulo(titulo);
        return respuesta;
    }
    @Transactional
    public List<Libro> busquedaLibroAutor(String titulo) throws Excepciones {       

        List<Libro> respuesta = libroRepositorio.buscarLibrosPorAutorNombre(titulo);
        return respuesta;
    }
    @Transactional
    public List<Libro> busquedaLibroEditorial(String titulo) throws Excepciones {       
        List<Libro> respuesta = libroRepositorio.buscarLibrosPorEditorialNombre(titulo);
        return respuesta;
    }

    @Transactional(readOnly = true)
    public Libro getOne(String id) {
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
    public void prestarLibro(String idLibro,String idCliente) throws Excepciones {
        Integer ejemplaresPresta = 1;
        validarLibroId(idLibro);

        Libro libro = buscarLibroId(idLibro);
        Cliente cliente = clienteServicio.buscarClienteId(idCliente);
        libro.setCliente(cliente);

        if (libro != null) {
            if (libro.getEjemplaresRestantes() <= 0) {
                throw new Excepciones("No quedan libros para prestar");
            }
            if (ejemplaresPresta <= libro.getEjemplaresRestantes()) {
                libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - ejemplaresPresta);
                libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + ejemplaresPresta);
            } else {
                throw new Excepciones("No hay suficientes libros para prestar");
            }
        }
        libroRepositorio.save(libro);
    }
    
    
    
    
//    @Transactional
//    public void crearPrestamo(String idLibro, String idCliente, Integer ejemplaresPrestados) throws Excepciones {
//
//        Libro libro = libroRepositorio.findById(idLibro).get();
//        Cliente cliente = clienteRepositorio.findById(idCliente).get();
//        
//        validarPrestamoId(idCliente);
//        
//        Prestamo prestamo = new Prestamo();
//        
//        prestamo.setFechaPrestamo(new Date());
//        prestamo.setFechaDevolucion(null);
//        prestamo.setAlta(true);
//        prestamo.setLibro(libro);
//        prestamo.setCliente(cliente);
//       
//        prestamoRepositorio.save(prestamo);
//    }
    
//    @Transactional
//    public void prestarLibro(String idLibro, Integer ejemplaresPrestados) throws Excepciones {
//          
//        validarLibroId(idLibro);
//        validarEjemplaresPrestados(ejemplaresPrestados);
//
//        Libro libro = buscarLibroId(idLibro);
//
//        if (libro != null) {
//            if (libro.getEjemplaresRestantes() <= 0) {
//                throw new Excepciones("No quedan libros para prestar");
//            } else {
//                if (ejemplaresPrestados < libro.getEjemplaresRestantes()) {
//
//                    libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - ejemplaresPrestados);
//                    libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + ejemplaresPrestados);
//                } else {
//                    throw new Excepciones("No hay suficientes libros para prestar");
//                }
//            }
//            
//            libroRepositorio.save(libro);
//        }
//    }
    
    //----------------DEVOLUCIONES-----------------------

    @Transactional
    public void devolverLibro(String idLibro, Integer ejemplaresPrestados) throws Excepciones {
        validarLibroId(idLibro);

        Libro libro = buscarLibroId(idLibro);

        Integer valor = libro.getEjemplaresRestantes() + ejemplaresPrestados;

        if (libro != null) {
            if (libro.getEjemplares() >= valor) {
                libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() + ejemplaresPrestados);
                libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() - ejemplaresPrestados);
            } else {
                throw new Excepciones("Se estan devolviendo mas libros que los prestados");
            }
        }
        
         libro.setCliente(null);
        libroRepositorio.save(libro);
    }
}
