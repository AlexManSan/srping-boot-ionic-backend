package com.cursomc.repositories; 


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.domain.Cliente;
import com.cursomc.domain.Pedido;

/**
 * 
 * @author Alex
 *
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);

}
