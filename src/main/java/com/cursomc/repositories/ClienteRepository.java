package com.cursomc.repositories; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.domain.Cliente;

/**
 * 
 * @author Alex
 *
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Transactional(readOnly=true) //não será envolvida nas transações do banco de dados ficando mais rápido
	Cliente findByEmail(String email);
}
