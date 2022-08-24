package br.com.vendas.udemy.service.impl;

import br.com.vendas.udemy.domain.entity.Cliente;
import br.com.vendas.udemy.domain.entity.ItemPedido;
import br.com.vendas.udemy.domain.entity.Pedido;
import br.com.vendas.udemy.domain.entity.Produto;
import br.com.vendas.udemy.domain.enums.StatusPedido;
import br.com.vendas.udemy.domain.repository.ClienteRepositorie;
import br.com.vendas.udemy.domain.repository.ItemsPedidoRepositorie;
import br.com.vendas.udemy.domain.repository.PedidosRepositorie;
import br.com.vendas.udemy.domain.repository.ProdutosRepositorie;
import br.com.vendas.udemy.exception.PedidoNaoEncontradoException;
import br.com.vendas.udemy.exception.RegraNegocioException;
import br.com.vendas.udemy.rest.dto.ItensPedidoDTO;
import br.com.vendas.udemy.rest.dto.PedidoDTO;
import br.com.vendas.udemy.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidosRepositorie pedidoRepository;
    private final ClienteRepositorie clientesrepository;
    private final ProdutosRepositorie produtosRepositorieRepository;
    private final ItemsPedidoRepositorie itensPedidoRepository;

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
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidos = converterItems(pedido, dto.getItens());
        pedidoRepository.save(pedido);
        itensPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizarStatus(Integer id, StatusPedido statusPedido) {
      pedidoRepository.findById(id)
              .map( pedido -> {
                  pedido.setStatus(statusPedido);
                  return pedidoRepository.save(pedido);
              }).orElseThrow(PedidoNaoEncontradoException::new);
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItensPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possivel realizar um pedido sem items.");
        }
       return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProdutos();
                    Produto produto = produtosRepositorieRepository
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
