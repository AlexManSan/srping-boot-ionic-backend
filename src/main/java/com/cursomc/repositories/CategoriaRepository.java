package com.cursomc.repositories; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Categoria;

/**
 * 
 * @author Alex
 *
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
