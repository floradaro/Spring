/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.controlador;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.servicios.AutorServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import com.libreria.libreria.servicios.LibroServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author flora
 */
@Controller
@RequestMapping("/foto")
public class FotoControlador {
    
@Autowired
    private LibroServicio libroServicio;

@Autowired
private AutorServicio autorServicio;

@Autowired
private EditorialServicio editorialServicio;
    
    @GetMapping("/libro")
    public ResponseEntity<byte[]> foto(@RequestParam String id) {

        try {
            Libro libro = libroServicio.buscarLibroId(id);
            if(libro.getFoto() == null){
                throw new Excepciones("El libro no tiene una imagen");
            }
            byte[] foto = libro.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (Excepciones ex) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/autor")
    public ResponseEntity<byte[]> fotoAutor(@RequestParam String id){
    try {
        Autor autor = autorServicio.buscarAutorId(id);
        if(autor.getFoto() == null){
                throw new Excepciones("El Autor no tiene una imagen");
            }
        
        byte [] foto = autor.getFoto().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(foto, headers, HttpStatus.OK);
    } catch (Excepciones ex) {
        Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE, null, ex);
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
        
        
    }
    
    @GetMapping("/editorial")
    public ResponseEntity<byte[]> fotoEditorial(@RequestParam String id){
    try {
        Editorial editorial = editorialServicio.buscarEditorialId(id);
        if(editorial.getFoto() == null){
                throw new Excepciones("La Editorial  no tiene una imagen");
            }
        
        byte [] foto = editorial.getFoto().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(foto, headers, HttpStatus.OK);
    } catch (Excepciones ex) {
        Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE, null, ex);
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
        
        
    }
}
