package com.cursomc.repositories; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Produto;

/**
 * 
 * @author Alex
 *
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
