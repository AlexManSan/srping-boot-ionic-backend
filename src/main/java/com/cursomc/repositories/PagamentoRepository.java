package com.cursomc.repositories; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Pagamento;

/**
 * OBS: Basta criar o repository da superclasse que já serve para as subclasses
 * @author Alex
 *
 */
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

}
