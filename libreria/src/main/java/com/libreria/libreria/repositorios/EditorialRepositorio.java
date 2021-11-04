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

    //Por id 
     @Query("SELECT a FROM Editorial a WHERE a.id = :id")
    public Editorial buscarPorId(@Param("id") String id);
    
    // Todos
    @Query("SELECT a FROM Editorial a ORDER BY a.nombre DESC")
    public List<Editorial> listarEditoriales();
    
   // Alta y baja
    @Query("SELECT a FROM Editorial a WHERE a.alta = true ORDER BY a.nombre DESC")
    public List<Editorial> ListarEditorialesAlta();

    @Query("SELECT a FROM Editorial a WHERE a.alta = false ORDER BY a.nombre DESC")
    public List<Editorial> ListarEditorialesBaja();
    
    // Por parametros
    @Query("SELECT a FROM Editorial a WHERE a.nombre LIKE :nombre ORDER BY a.nombre DESC")
    public List<Editorial> listarNombresEditorial(@Param("nombre") String nombre);
    }

