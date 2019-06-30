package com.cursomc.repositories; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.ItemPedido;
import com.cursomc.domain.ItemPedidoPK;

/**
 * 
 * @author Alex
 *
 */
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK>{

}
