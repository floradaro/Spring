/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.utilidades;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.excepciones.Excepciones;
import com.libreria.libreria.servicios.AutorServicio;
import com.libreria.libreria.servicios.EditorialServicio;
import com.libreria.libreria.servicios.LibroServicio;

/**
 *
 * @author flora
 */
public class UtilidadLlenado {

    public void IniciarBiblio() throws Excepciones {
        AutorServicio au = new AutorServicio();
        EditorialServicio eu = new EditorialServicio();
        LibroServicio lu = new LibroServicio();

        Autor aut1 = au.creaAutor("J. R. R. Tolkien", true);
        Autor aut2 = au.creaAutor("Gabriel Garcia Marquez", true);
        Autor aut3 = au.creaAutor("Pahulo Coelho", true);
        Autor aut4 = au.creaAutor("Jorge Luis Borgues", true);
        Autor aut5 = au.creaAutor("Oscar Wilde", true);

        Editorial edit1 = eu.creaEditorial("Minotauro", true);
        Editorial edit2 = eu.creaEditorial("Sudamericana", true);
        Editorial edit3 = eu.creaEditorial("Penguin Random House", true);
        Editorial edit4 = eu.creaEditorial("Bruguera", true);
        Editorial edit5 = eu.creaEditorial("Planeta", true);
        Editorial edit7 = eu.creaEditorial("Emece", true);
        Editorial edit8 = eu.creaEditorial("Siruela", true);
        Editorial edit9 = eu.creaEditorial("Companhia das Letras", true);
        Editorial edit10 = eu.creaEditorial("New Directions", true);
        Editorial edit11 = eu.creaEditorial("Lippincott's Monthly Magazine", true);
        Editorial edit12 = eu.creaEditorial("Santillana", true);
        Editorial edit13 = eu.creaEditorial("Estrada", true);
        Editorial edit14 = eu.creaEditorial("Combel", true);

        Libro lb1 = lu.creaLibro("El señor de los anillos", 1937, 6, true, aut1.getId(), edit1.getId());
        Libro lb2 = lu.creaLibro("El señor de los anillos 2", 1939, 6, true, aut1.getId(), edit1.getId());
        Libro lb3 = lu.creaLibro("El señor de los anillos 3", 1940, 6, true, aut1.getId(), edit1.getId());
        Libro lb4 = lu.creaLibro("Cien años de soledad", 1967, 6, true, aut2.getId(), edit2.getId());
        Libro lb5 = lu.creaLibro("El amor en tiempos del colera", 1985, 6, true, aut2.getId(), edit3.getId());
        Libro lb6 = lu.creaLibro("Cronica de una muerte anunciada", 1981, 6, true, aut2.getId(), edit4.getId());
        Libro lb7 = lu.creaLibro("Del amor y otros demonios", 1994, 6, true, aut2.getId(), edit2.getId());
        Libro lb8 = lu.creaLibro("El alquimista", 1988, 6, true, aut3.getId(), edit5.getId());
        Libro lb9 = lu.creaLibro("Veronika decide morir", 1998, 6, true, aut3.getId(), edit5.getId());
        Libro lb10 = lu.creaLibro("Adulterio", 2014, 6, true, aut3.getId(), edit5.getId());
        Libro lb11 = lu.creaLibro("Manual del guerrero de la luz", 1997, 6, true, aut3.getId(), edit7.getId());
        Libro lb12 = lu.creaLibro("Ficciones", 1944, 6, true, aut3.getId(), edit7.getId());
        Libro lb13 = lu.creaLibro("La biblioteca de Babel", 1941, 6, true, aut4.getId(), edit8.getId());
        Libro lb14 = lu.creaLibro("El libro de arena", 1975, 6, true, aut4.getId(), edit7.getId());
        Libro lb15 = lu.creaLibro("El hacedor", 1917, 6, true, aut4.getId(), edit2.getId());
        Libro lb16 = lu.creaLibro("Labyrinths", 1962, 6, true, aut4.getId(), edit10.getId());
        Libro lb17 = lu.creaLibro("El retrato de Dorian Gray", 1890, 6, true, aut5.getId(), edit11.getId());
        Libro lb18 = lu.creaLibro("El principe feliz y otros cuentos", 1888, 6, true, aut5.getId(), edit12.getId());
        Libro lb19 = lu.creaLibro("El fantasma de Canterville", 1887, 6, true, aut5.getId(), edit13.getId());
        Libro lb20 = lu.creaLibro("El gigante egoista", 1888, 6, true, aut5.getId(), edit14.getId());
    }
}
