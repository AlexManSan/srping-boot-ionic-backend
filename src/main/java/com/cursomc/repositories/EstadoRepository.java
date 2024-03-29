package com.cursomc.repositories; 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.domain.Estado;

/**
 * 
 * @author Alex
 *
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{

	@Transactional(readOnly=true)
	public List<Estado> findAllByOrderByNome();
}
