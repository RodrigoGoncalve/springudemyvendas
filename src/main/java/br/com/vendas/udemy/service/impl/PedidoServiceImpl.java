package br.com.vendas.udemy.service.impl;

import br.com.vendas.udemy.domain.entity.Cliente;
import br.com.vendas.udemy.domain.entity.ItemPedido;
import br.com.vendas.udemy.domain.entity.Pedido;
import br.com.vendas.udemy.domain.entity.Produto;
import br.com.vendas.udemy.domain.repository.Clientes;
import br.com.vendas.udemy.domain.repository.ItemsPedido;
import br.com.vendas.udemy.domain.repository.Pedidos;
import br.com.vendas.udemy.domain.repository.Produtos;
import br.com.vendas.udemy.exception.RegraNegocioException;
import br.com.vendas.udemy.rest.dto.ItensPedidoDTO;
import br.com.vendas.udemy.rest.dto.PedidoDTO;
import br.com.vendas.udemy.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos pedidos;
    private final Clientes clientesrepository;
    private final Produtos produtosRepository;
    private final ItemsPedido pedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer id = dto.getCliente();

        Cliente cliente = clientesrepository.findById(id)
                .orElseThrow(() ->
                new RegraNegocioException("Código de cliente invalido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemPedidos = converterItems(pedido, dto.getItens());
        pedidos.save(pedido);
        pedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);

        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItensPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possivel realizar um pedido sem items.");
        }
       return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProdutos();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () ->
                                    new RegraNegocioException("Código de produto invalido: " + idProduto
                                    ));

                    ItemPedido itemsPedido = new ItemPedido();
                    itemsPedido.setQuantidade(dto.getQuantidade());
                    itemsPedido.setPedido(pedido);
                    itemsPedido.setProduto(produto);

                    return itemsPedido;
                }).collect(Collectors.toList());
    }
}
