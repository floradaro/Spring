/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.controlador;

import com.libreria.libreria.excepciones.Excepciones;
//import com.libreria.libreria.servicios.LibroServicio;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author flora
 */
@Controller
@RequestMapping("/")  //Configura cual es la url
public class PortalControlador {

//@Autowired
//    private LibroServicio libroServicio;
    
    @GetMapping("/")
    public String index() throws Exception {
//        try{
//            libroServicio.imprimirLibros();
//        } catch(Excepciones e) {  
//        }
        return "index.html";
    }
}