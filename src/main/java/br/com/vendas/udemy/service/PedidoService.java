package br.com.vendas.udemy.service;

import br.com.vendas.udemy.domain.entity.Pedido;
import br.com.vendas.udemy.domain.enums.StatusPedido;
import br.com.vendas.udemy.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizarStatus(Integer id, StatusPedido statusPedido);

}
