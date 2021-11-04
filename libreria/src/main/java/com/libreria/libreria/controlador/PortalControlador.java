/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.controlador;


import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.servicios.AutorServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    
    //-------LOGIN
    
      @GetMapping("/")
    public String login() {
        return "login.html";
    }
    //-------ReGISTRO
       @GetMapping("/")
    public String registro() {
        return "registro.html";
    }
    //-------REGISTRO DE EDITORIALES
    @GetMapping("/editreg")
    public String editreg() {
        return "editreg.html";
    }
    
    @PostMapping("/editreg")
    public String editreg (@RequestParam String nombre){

        try {
            editorialServicio.creaEditorial(nombre, true);
        } catch (Excepciones ex) {
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "editreg.html";
        }
        return "index.html";
    }
    //--------REGISTRO DE AUTORES
    @GetMapping("/autreg")
    public String autreg() {
        return "autreg.html";
    }
    @PostMapping("/autreg")
    public String autreg (@RequestParam String nombre,@RequestParam String apellido) {
        String completo = nombre + " "+apellido;

        try {
            autorServicio.creaAutor(completo, true);
        } catch (Excepciones ex) {
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "autreg.html";
        }
        return "index.html";
    }
    
}
