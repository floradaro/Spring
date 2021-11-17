/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.servicios;

import com.libreria.libreria.entidades.Cliente;
import com.libreria.libreria.entidades.Foto;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.repositorios.ClienteRepositorio;
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
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void crearCliente(MultipartFile archivo, String nombre, Long documento, Long telefono) throws Excepciones {

        validarCliente (nombre, documento, telefono);

        Cliente cliente = new Cliente();
        
        cliente.setNombre(nombre);
        cliente.setDocumento(documento);
        cliente.setTelefono(telefono);
        cliente.setAlta(true);

        Foto foto = fotoServicio.guardar(archivo);
        cliente.setFoto(foto);
        
        clienteRepositorio.save(cliente);
    }
//-------------VALIDACIONES--------------
private void validarCliente(String nombre, Long documento, Long telefono) throws Excepciones {

        if (nombre == null || nombre.isEmpty()) {
            throw new Excepciones("El nombre del cliente no puede ser nulo");
        }
        
        if (documento == null || documento <= 0) {
            throw new Excepciones("Se debe especificar el año de Alta");
        }
        if (telefono == null || telefono < 0) {
            throw new Excepciones("Debe indicar el número de ejemplares");
        }
    }

private void validarClienteId(String id) throws Excepciones {

        if (id == null || id.isEmpty()) {
            throw new Excepciones("El nombre del cliente no puede ser nulo");
        }
    }
private void validarClienteNombre(String nombre, String apellido) throws Excepciones {

        if (nombre == null || nombre.isEmpty()) {
            throw new Excepciones("El nombre del cliente no puede ser nulo");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new Excepciones("El apellido del cliente no puede ser nulo");
        }
    }
//-----------------------modificar------------
@Transactional
    public void modificarclienteID(String id,String nombrenuevo,String apellidoNuevo, MultipartFile archivo ) throws Excepciones {
        validarClienteId(id);
        validarClienteNombre(nombrenuevo, apellidoNuevo);

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Cliente cliente = respuesta.get();
            cliente.setNombre(nombrenuevo);
            cliente.setApellido(apellidoNuevo);
            
            String idFoto = null;
            if (cliente.getFoto() != null) {
                idFoto = cliente.getFoto().getId();
            }
            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            cliente.setFoto(foto);

            clienteRepositorio.save(cliente);
        } else {
            throw new Excepciones("No se encontró el id del Cliente");
        }
    }
    //---------------Busqueda-----------
    
    public Cliente buscarClienteId(String id) throws Excepciones {
        validarClienteId(id);

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        Cliente cliente = null;
        if (respuesta.isPresent()) {
            cliente = respuesta.get();
        } else {
            throw new Excepciones("No se encontró el id del Cliente");
        }
        return cliente;
    }
    
   // ----------- ALTA y baja---------------------
    
 @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void darDeBaja(String id) throws Excepciones {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setAlta(Boolean.FALSE);
            clienteRepositorio.save(cliente);
        } else {
            throw new Excepciones("No se encontro el cliente");
        }
    }

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void darDeAlta(String id) throws Excepciones {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setAlta(Boolean.TRUE);
            clienteRepositorio.save(cliente);
        } else {
            throw new Excepciones("No se encontro el cliente");
        }
    }
}
