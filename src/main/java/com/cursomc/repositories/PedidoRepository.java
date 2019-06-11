package com.cursomc.repositories; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Pedido;

/**
 * 
 * @author Alex
 *
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
