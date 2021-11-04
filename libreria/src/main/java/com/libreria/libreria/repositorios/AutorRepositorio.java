/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Autor;
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
public interface AutorRepositorio extends JpaRepository<Autor, String> {

    //Por id
    @Query("SELECT a FROM Autor a WHERE a.id = :id")
    public Autor buscarPorId(@Param("id") String id);

    // Todos
    @Query("SELECT a FROM Autor a ORDER BY a.nombre DESC")
    public List<Autor> listarAutores();

    // Alta y baja
    @Query("SELECT a FROM Autor a WHERE a.alta = true ORDER BY a.nombre DESC")
    public List<Autor> ListarAutoresAlta();

    @Query("SELECT a FROM Autor a WHERE a.alta = false ORDER BY a.nombre DESC")
    public List<Autor> ListarAutoresBaja();

    // Parametros
    @Query("SELECT a FROM Autor a WHERE a.nombre LIKE :nombre ORDER BY a.nombre DESC")
    public List<Autor> listarNombresAutores(@Param("nombre") String nombre);
}
