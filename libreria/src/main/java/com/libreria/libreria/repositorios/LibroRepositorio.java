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

    //Por Id
    
    @Query("SELECT a FROM Libro a WHERE a.id = :id")
    public List<Libro> buscarLibrosPorId(@Param("id") String id);
    
    @Query("SELECT a FROM Libro a WHERE a.autor.id = :id")
    public List<Libro> buscarLibrosPorAutor(@Param("id") String id);

    @Query("SELECT a FROM Libro a WHERE a.editorial.id = :id")
    public List<Libro> buscarLibrosPorEditorial(@Param("id") String id);

    // Por nombre
    @Query("SELECT a FROM Libro a WHERE a.autor.nombre = :nombre ORDER BY a.autor.nombre DESC")
    public List<Libro> buscarLibrosPorAutorNombre(@Param("nombre") String nombre);

    @Query("SELECT a FROM Libro a WHERE a.editorial.nombre = :nombre ORDER BY a.editorial.nombre DESC")
    public List<Libro> buscarLibrosPorEditorialNombre(@Param("nombre") String nombre);

    //Por libro
    @Query("SELECT a from Libro a WHERE a.alta = true AND a.titulo LIKE :titulo ORDER BY a.titulo DESC")
    public List<Libro> buscarLibrosPorTituloAlta();

    @Query("SELECT a from Libro a WHERE a.alta = false AND a.titulo LIKE :titulo ORDER BY a.titulo DESC")
    public List<Libro> buscarLibrosPorTituloBaja();

    @Query("SELECT a FROM Libro a ORDER BY a.titulo DESC")
    public List<Libro> listarLibros();
}
