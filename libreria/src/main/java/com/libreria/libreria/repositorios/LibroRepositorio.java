/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author flora
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    @Query("SELECT c FROM Libro c WHERE c.autor.id = :id")
    public List<Libro> buscarLibrosPorAutor(@Param("id") String id);

    @Query("SELECT c FROM Libro c WHERE c.editorial.id = :id")
    public List<Libro> buscarLibrosPorEditorial(@Param("id") String id);

    @Query("SELECT c FROM Libro c WHERE c.autor.nombre = :nombre")
    public List<Libro> buscarLibrosPorAutorNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Libro c WHERE c.editorial.nombre = :nombre")
    public List<Libro> buscarLibrosPorEditorialNombre(@Param("nombre") String nombre);
         
    @Query("SELECT c FROM Libro c WHERE c.titulo= :titulo")
    public List<Libro> buscarLibrosPorTitulo(@Param("titulo") String titulo);
}
