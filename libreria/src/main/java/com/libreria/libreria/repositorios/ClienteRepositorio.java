/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.repositorios;

import com.libreria.libreria.entidades.Cliente;
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
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
    
     @Query("SELECT a FROM Cliente a WHERE a.id = :id")
    public Cliente buscarPorId(@Param("id") String id);

    @Query("SELECT a from Cliente a WHERE a.alta = true ")
    public List<Cliente> buscarActivos();

    @Query("SELECT a from Cliente a WHERE a.alta = false ")
    public List<Cliente> buscarBajas();


}
