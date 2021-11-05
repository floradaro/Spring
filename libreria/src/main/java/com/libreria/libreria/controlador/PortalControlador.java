/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.controlador;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.repositorios.AutorRepositorio;
import com.libreria.libreria.repositorios.EditorialRepositorio;
import com.libreria.libreria.repositorios.UsuarioRepositorio;
import com.libreria.libreria.servicios.AutorServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import com.libreria.libreria.servicios.LibroServicio;
import com.libreria.libreria.servicios.UsuarioServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    //-------LOGIN
    
     @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if(error != null ){
        modelo.put("error", "Nombre de usuario o clave incorrectos");
        }
        return "login.html";
    }

    //-------REGISTRO DE USUARIO
    @GetMapping("/registro")
    public String registro() {
        return "registro.html";
    }

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
    @GetMapping("/editreg")
    public String editreg() {
        return "editreg.html";
    }

    @PostMapping("/editreg")
    public String editreg(ModelMap modelo,@RequestParam String nombre) {

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
    @GetMapping("/autreg")
    public String autreg() {
        return "autreg.html";
    }

    @PostMapping("/autreg")
    public String autreg(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido) {
        String completo = nombre + " " + apellido;
        try {
            autorServicio.crearAutor(null, completo);
              modelo.put("exito", "Registro Exitoso!");
            return "autreg.html";
        } catch (Excepciones ex) {
            modelo.put("error", "Debe completar todos los campos");
            return "autreg.html";
        }
    }

//    //-------MODIFICAR 
//    @GetMapping("/Modificar/{id}")
//    public String modificar(@PathVariable String id, ModelMap modelo) {
//
//        return ("/modificar");
//
//    }

//    //---------------LIBROS---------------
//    @GetMapping("/libini")
//    public String libini(ModelMap modelo) {
//        return "libini.html";
//    }

    //-------REGISTRO DE LIBROS
    @GetMapping("/libreg")
    public String libreg(ModelMap modelo) {

        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);

        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);

        return "libreg";
    }

    //////////////CORREGIR ERROR AL ENVIAR DATOS NULOS//////////////
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
//    
//    @GetMapping ("/lista")
//    public String lista (MoelMap modelo){
//        list <PErro> perros = perroSerivce.listarTodos();
//        
//        modelo.addAtribute("perros",perros);
//        return "list-perro";
//    }
}
