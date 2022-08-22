package br.com.vendas.udemy.service;

import br.com.vendas.udemy.domain.entity.Pedido;
import br.com.vendas.udemy.rest.dto.PedidoDTO;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

}
