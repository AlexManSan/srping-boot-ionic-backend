package com.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.domain.ItemPedido;
import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.exceptions.ObjectNotFoundException;
import com.cursomc.repositories.ItemPedidoRepository;
import com.cursomc.repositories.PagamentoRepository;
import com.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository dao;
	
	@Autowired
	private ItemPedidoRepository itemRepo;
	
	@Autowired
	private PagamentoRepository pagRepo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	/**
	 * Método busca o objeto no banco e dispara um excessão caso não exista
	 * @param id
	 * @return
	 */
	public Pedido find(Long id) {
		// buscando por id e transformando em optional
		Optional<Pedido> obj = dao.findById(id); 
		
		// retona a categoria ou uma exceção caso o id não exista 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));

	}
	
	/**
	 * Método de inserção do pedido, inserindo também o pagamento e os itens do pedido
	 * @param obj
	 * @return
	 */
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = dao.save(obj);
		pagRepo.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemRepo.saveAll(obj.getItens());
		emailService.envioEmailConfirmacaoPedido(obj);
		return obj;
		
	}
}
