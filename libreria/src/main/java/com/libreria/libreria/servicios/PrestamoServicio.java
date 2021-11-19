/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Cliente;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.entidades.Prestamo;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.repositorios.ClienteRepositorio;
import com.libreria.libreria.repositorios.LibroRepositorio;
import com.libreria.libreria.repositorios.PrestamoRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author flora
 */
@Service
public class PrestamoServicio {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;
   
//    
//    //----------Crear prestamo ---------
//    

    @Transactional
    public void prestarLibro(String idLibro, String idCliente, Integer ejemplaresPrestados) throws Excepciones {

//        Libro libro = libroRepositorio.findById(idLibro).get();
//        Cliente cliente = clienteRepositorio.findById(idCliente).get();
//        
//        validarClienteId(idCliente);
//        validarLibroId(idLibro);
//        
//        Prestamo prestamo = new Prestamo();
//        
//        prestamo.setFechaPrestamo(new Date());
//        prestamo.setFechaDevolucion(null);
//        prestamo.setAlta(true);
//        prestamo.setCliente(cliente);
//        
//        libroServicio.prestarLibro(idLibro, ejemplaresPrestados);
//       prestamo.setLibro(libro);
//       
//        prestamoRepositorio.save(prestamo);
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaPrestamo(new Date());

        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            if (libro.getEjemplares().equals(ejemplaresPrestados)) {
                throw new Excepciones("No hay ejemplares disponibles");
            } else {
                prestamo.setLibro(libro);
            }
        } else {
            throw new Excepciones("No se encuentra el libro");
        }

        Optional<Cliente> respuesta2 = clienteRepositorio.findById(idCliente);
        if (respuesta2.isPresent()) {
            Cliente cliente = respuesta2.get();
            prestamo.setCliente(cliente);
            prestamo.setAlta(true);
        } else {
            throw new Excepciones("No se encuentra el Cliente");
        }
        prestamoRepositorio.save(prestamo);
    }

    //-------OTRO PRESTAMO SOBRE EL LIBRO
    public void devolucion(String idPrestamo, String idCliente) throws Excepciones {

        Optional<Prestamo> respuesta = prestamoRepositorio.findById(idPrestamo);
        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();
            prestamo.setFechaDevolucion(new Date());

            if (prestamo.getCliente().getId().equals(idCliente)) {
                prestamo.setAlta(false);
                prestamo.setCliente(null);
                prestamo.setLibro(null);
                prestamoRepositorio.save(prestamo);
            } else {
                throw new Excepciones("No se encuentra el Cliente solicitado");
            }
        } else {
            throw new Excepciones("No existe el prestamo solicitado");
        }

    }
//    
// //-------------VALIDACIONES--------------
//
//
//    private void validarClienteId(String idCliente) throws Excepciones {
//
//        if (idCliente == null || idCliente.isEmpty()) {
//            throw new Excepciones("El nombre del cliente no puede ser nulo");
//        }
//    }
//
//    private void validarIdPrestamo(String id) throws Excepciones {
//
//        if (id == null || id.isEmpty()) {
//            throw new Excepciones("No se puede identificar el prestamo");
//        }
//    }
//
//    private void validarLibroId(String idLibro) throws Excepciones {
//
//        if (idLibro == null || idLibro.isEmpty()) {
//            throw new Excepciones("El nombre del cliente no puede ser nulo");
//        }
//    }
////-----------------------modificar------------
//@Transactional
//    public void devolucion(String id) throws Excepciones {
//        
//        validarIdPrestamo(id);
//
//        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);
//        if (respuesta.isPresent()) {
//
//            Prestamo prestamo = respuesta.get();
//            prestamo.setFechaDevolucion(new Date());
//            prestamo.setAlta(false);
//            prestamo.setLibro(null);
//            prestamo.setCliente(null);
//
//            prestamoRepositorio.save(prestamo);
//        } else {
//            throw new Excepciones("No se encontró el id del Prestamo");
//        }
//    }
//    
//     //---------------Busqueda-----------
//    
//    public Prestamo buscarPrestamoId(String id) throws Excepciones {
//        validarIdPrestamo(id);
//
//        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);
//        Prestamo prestamo  = null;
//        if (respuesta.isPresent()) {
//            prestamo = respuesta.get();
//        } else {
//            throw new Excepciones("No se encontró el id del Cliente");
//        }
//        return prestamo;
//    }
}
