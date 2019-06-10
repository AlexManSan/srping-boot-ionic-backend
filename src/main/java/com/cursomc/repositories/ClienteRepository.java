package com.cursomc.repositories; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Cliente;

/**
 * 
 * @author Alex
 *
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
