package com.cursomc.repositories; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Estado;

/**
 * 
 * @author Alex
 *
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{

}
