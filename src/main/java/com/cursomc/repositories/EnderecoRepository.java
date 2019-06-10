package com.cursomc.repositories; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Endereco;

/**
 * 
 * @author Alex
 *
 */
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

}
