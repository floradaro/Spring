/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Prestamo;
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
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {
    
    
     @Query("SELECT a FROM Prestamo a WHERE a.id = :id")
    public Prestamo buscarPorId(@Param("id") String id);

    @Query("SELECT a from Prestamo a WHERE a.alta = true ")
    public List<Prestamo> buscarActivos();

    @Query("SELECT a from Prestamo a WHERE a.alta = false ")
    public List<Prestamo> buscarBajas();
    
    
}
