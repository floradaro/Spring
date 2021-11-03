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

    @Query("SELECT c FROM Autor c ")
    public List<Autor> listarAutores();

    @Query("SELECT c FROM Autor c WHERE c.id = :id")
    public Autor buscarPorId(@Param("id") String id);

    @Query("SELECT c FROM Autor c WHERE c.nombre LIKE :nombre")
    public List<Autor> listarNombresAutores(@Param("nombre") String nombre);
}

//  @Query("SELECT c FROM Autor c WHERE c.id = true ")
  //  public List<Autor> listarNombresAutores();