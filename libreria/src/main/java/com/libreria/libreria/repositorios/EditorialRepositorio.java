/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Editorial;
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
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

    @Query("SELECT c FROM Editorial c")
    public List<Editorial> listarEditoriales();
    
    @Query("SELECT c FROM Editorial c WHERE c.id = :id")
    public Editorial buscarPorId(@Param("id") String id);
    
    @Query("SELECT c FROM Editorial c WHERE c.nombre LIKE %:nombre%")
    public List<Editorial> listarNombresEditorial(@Param("nombre") String nombre);
    }

