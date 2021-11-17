/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.controlador;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Cliente;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.repositorios.AutorRepositorio;
import com.libreria.libreria.repositorios.ClienteRepositorio;
import com.libreria.libreria.repositorios.EditorialRepositorio;
import com.libreria.libreria.repositorios.LibroRepositorio;
import com.libreria.libreria.repositorios.UsuarioRepositorio;
import com.libreria.libreria.servicios.AutorServicio;
import com.libreria.libreria.servicios.ClienteServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import com.libreria.libreria.servicios.LibroServicio;
import com.libreria.libreria.servicios.UsuarioServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author flora
 */
@Controller
@RequestMapping("/")  //Configura cual es la url
public class PortalControlador {

    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    //-------LOGIN

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Nombre de usuario o clave incorrectos");
        }
        if (logout != null) {
            modelo.put("logout", "Has salido correctamente");
        }
        return "login.html";
    }

    //-------REGISTRO DE USUARIO
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/registro")
    public String registro() {
        return "registro.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2) {
        try {
            usuarioServicio.registrar(null, nombre, apellido, mail, clave1, clave2);
            modelo.put("exito", "Registro Exitoso!");
            return "login.html";

        } catch (Excepciones e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);

            return "registro.html";
        }

    }

    //-------REGISTRO DE EDITORIALES
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/editreg")
    public String editreg() {
        return "editreg.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/editreg")
    public String editreg(ModelMap modelo, @RequestParam String nombre) {

        try {
            editorialServicio.crearEditorial(null, nombre);
            modelo.put("exito", "Registro Exitoso!");
            return "editreg.html";
        } catch (Excepciones ex) {
            modelo.put("error", "Debe completar todos los campos");
            return "editreg.html";
        }
    }

    //--------REGISTRO DE AUTORES
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/autreg")
    public String autreg() {
        return "autreg.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/autreg")
    public String autreg(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, MultipartFile archivo) {
        String completo = nombre + " " + apellido;
        try {
            autorServicio.crearAutor(archivo, completo);
            modelo.put("exito", "Registro Exitoso!");
            return "autreg.html";
        } catch (Excepciones ex) {
            modelo.put("error", "Debe completar todos los campos");
            return "autreg.html";
        }
    }

    //-------REGISTRO DE LIBROS
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/libreg")
    public String libreg(ModelMap modelo) {

        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);

        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);

        return "libreg";
    }

    //////////////CORREGIR ERROR AL ENVIAR DATOS NULOS//////////////
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/libreg")
    public String libreg(ModelMap modelo, MultipartFile archivo, @RequestParam String titulo, @RequestParam String idautor, @RequestParam String ideditorial, @RequestParam Integer anio, @RequestParam Integer ejemplares) {

        try {
            libroServicio.crearLibro(archivo, titulo, anio, ejemplares, true, idautor, ideditorial);
            modelo.put("exito", "Libro registrado con exito");
            List<Editorial> editoriales = editorialRepositorio.findAll();
            modelo.put("editoriales", editoriales);

            List<Autor> autores = autorRepositorio.findAll();
            modelo.put("autores", autores);
        } catch (Excepciones ex) {
            List<Editorial> editoriales = editorialRepositorio.findAll();
            modelo.put("editoriales", editoriales);

            List<Autor> autores = autorRepositorio.findAll();
            modelo.put("autores", autores);

            modelo.put("error", "Error en la carga de Libro, faltan datos");
            return "libreg";
        }
        return "libreg";
    }

//    //--------------MODIFICAR AUTOR--------------
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicioautor")
    public String inicioautor(ModelMap modelo) {
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        return "inicioautor.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/modificarautor/{id}")
    public String modificarautor(@PathVariable String id, ModelMap modelo) throws Excepciones {
        modelo.put("autores", autorServicio.buscarAutorId(id));
        return "/modificarautor";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/modificarautor/{id}")
    public String modificarautor(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, MultipartFile archivo) throws Excepciones {
        try {
            autorServicio.modificarAutorID(id, nombre, archivo);
            modelo.put("exito", "Autor modificada con exito");
        } catch (Excepciones ex) {
            modelo.put("error", "Ingrese el nombre del Autor");
            modelo.put("autores", autorServicio.buscarAutorId(id));
            return "/modificarautor";
        }
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        return "inicioautor";
    }

//    //--------------MODIFICAR EDITORIAL--------------
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicioeditorial")
    public String inicioeditorial(ModelMap modelo) {

        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        return "inicioeditorial.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/modificareditorial/{id}")
    public String modificareditorial(@PathVariable String id, ModelMap modelo) throws Excepciones {
        modelo.put("editoriales", editorialServicio.buscarEditorialId(id));
        return "/modificareditorial";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/modificareditorial/{id}")
    public String modificareditorial(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, MultipartFile archivo) throws Excepciones {
        try {
            editorialServicio.modificarEditorialId(id, nombre, archivo);
            modelo.put("exito", "Editorial modificada con exito");
        } catch (Excepciones ex) {
            modelo.put("error", "Ingrese el nombre de la Editorial ");
            modelo.put("editoriales", editorialServicio.buscarEditorialId(id));
            return "/modificareditorial";
        }
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        return "inicioeditorial";
    }
//    //--------------MODIFICAR Libros--------------

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/iniciolibro")
    public String iniciolibro(ModelMap modelo) {
        List<Libro> libros = libroRepositorio.findAll();
        modelo.put("libros", libros);
        return "iniciolibro.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/modificarlibro/{id}")
    public String modificarlibro(@PathVariable String id, ModelMap modelo) throws Excepciones {

        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        modelo.put("libros", libroServicio.buscarLibroId(id));
        return "/modificarlibro";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/modificarlibro/{id}")
    public String modificarlibro(@PathVariable String id, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String idautor, @RequestParam String ideditorial, ModelMap modelo, MultipartFile archivo) throws Excepciones {
        try {
            libroServicio.modificarLibro(id, titulo, anio, ejemplares, idautor, ideditorial, archivo);
            modelo.put("exito", "Libro modificado con exito");
            List<Libro> libros = libroRepositorio.findAll();
            modelo.put("libros", libros);
            return "/iniciolibro";

        } catch (Excepciones ex) {
            modelo.put("libros", libroServicio.buscarLibroId(id));
            modelo.put("error", ex.getMessage());
            return "/modificarlibro.html";
        }
    }

    //-----------BUSCAR------------
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/busqueda")
    public String busquedaLibroNombre(ModelMap modelo, @RequestParam String titulo) throws Excepciones {

        List<Libro> libros = libroServicio.buscarPorParametro(titulo);

        if (libros != null) {
            modelo.put("libros", libros);
            return "/busqueda";
        }
        return "/busqueda";
    }
    
    
    @GetMapping("/busqueda1")
    public String busquedaLibroNombre1(ModelMap modelo, @RequestParam String titulo) throws Excepciones {

        List<Libro> libros = libroServicio.buscarPorParametro(titulo);

        if (libros != null) {
            modelo.put("libros", libros);
            return "/busqueda1";
        }
        return "/busqueda1";
    }

    //-------------------PRESTAMO LIBROS------------
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicioprestamo")
    public String inicioprestamo(ModelMap modelo) {
        List<Libro> libros = libroRepositorio.findAll();
        modelo.put("libros", libros);
        return "inicioprestamo.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/prestarlibro/{id}")
    public String prestarlibro(@PathVariable String id, ModelMap modelo) throws Excepciones {
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        List<Cliente> clientes = clienteRepositorio.findAll();
        modelo.put("clientes", clientes);
        modelo.put("libros", libroServicio.buscarLibroId(id));
        return "/prestarlibro";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/prestarlibro/{id}")
    public String prestarlibro(@PathVariable String id, @RequestParam Integer ejemplaresPresta, ModelMap modelo) throws Excepciones {
        try {
            libroServicio.prestarLibro(id, ejemplaresPresta);
            modelo.put("exito", "Libro prestado con exito");
            List<Libro> libros = libroRepositorio.findAll();
            modelo.put("libros", libros);
        } catch (Excepciones ex) {
            modelo.put("error", "Error al prestar libro");
            modelo.put("libros", libroServicio.buscarLibroId(id));
            return "/prestarlibro";
        }
        return "inicioprestamo.html";
    }

    //--------------------DEVOLVER LIBRO------------
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/devolverlibro/{id}")
    public String devolverlibro(@PathVariable String id, ModelMap modelo) throws Excepciones {
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        List<Cliente> clientes = clienteRepositorio.findAll();
        modelo.put("clientes", clientes);
        modelo.put("libros", libroServicio.buscarLibroId(id));
        return "/devolverlibro";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/devolverlibro/{id}")
    public String devolverlibro(@PathVariable String id, @RequestParam Integer ejemplaresVuelta, ModelMap modelo) throws Excepciones {
        try {
            libroServicio.devolverLibro(id, ejemplaresVuelta);
            modelo.put("exito", "Libro devuelto con exito");
            List<Libro> libros = libroRepositorio.findAll();
            modelo.put("libros", libros);
        } catch (Excepciones ex) {
            modelo.put("error", "Error al devolver libro, la cantidad es mayor que los ejemplares originales");
            modelo.put("libros", libroServicio.buscarLibroId(id));
            return "/devolverlibro";
        }
        return "inicioprestamo";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable String id) {
        try {
            libroServicio.darDeBaja(id);
            return "redirect:/iniciolibro";
        } catch (Excepciones e) {
            return "redirect:/";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id) {

        try {
            libroServicio.darDeAlta(id);
            return "redirect:/iniciolibro";
        } catch (Excepciones e) {
            return "redirect:/";
        }
    }
    //----------------ALTA Y BAJA AUTOR

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/bajaAutor/{id}")
    public String bajaAutor(ModelMap modelo, @PathVariable String id) {
        try {
            autorServicio.darDeBaja(id);
            return "redirect:/inicioautor";
        } catch (Excepciones e) {
            return "redirect:/";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/altaAutor/{id}")
    public String altaAutor(ModelMap modelo, @PathVariable String id) {

        try {
            autorServicio.darDeAlta(id);
            return "redirect:/inicioautor";
        } catch (Excepciones e) {
            return "redirect:/";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/bajaEditorial/{id}")
    public String bajaEditorial(ModelMap modelo, @PathVariable String id) {
        try {
            editorialServicio.darDeBaja(id);
            return "redirect:/inicioeditorial";
        } catch (Excepciones e) {
            return "redirect:/";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/altaEditorial/{id}")
    public String altaEditorial(ModelMap modelo, @PathVariable String id) {

        try {
            editorialServicio.darDeAlta(id);
            return "redirect:/inicioeditorial";
        } catch (Excepciones e) {
            return "redirect:/";
        }
    }
    //--------------clientes--------

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/clientes")
    public String clientes() {
        return "clientes.html";
    }
    
     @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/iniciocliente")
    public String iniciocliente(ModelMap modelo) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        modelo.put("clientes", clientes);
        return "iniciocliente.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/clientes")
    public String clientes(MultipartFile archivo, ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam Long documento, @RequestParam Long telefono ){
        String completo = nombre + " " + apellido;
        try {
            clienteServicio.crearCliente(archivo, nombre, documento, telefono);
            modelo.put("exito", "Registro Exitoso!");
            return "clientes.html";

        } catch (Excepciones e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("documento", documento);
            modelo.put("telefono", telefono);

   

            return "clientes.html";
        }

    }
    
   
}
