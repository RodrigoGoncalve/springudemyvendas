package br.com.vendas.udemy.rest.controller;

import br.com.vendas.udemy.domain.entity.ItemPedido;
import br.com.vendas.udemy.domain.entity.Pedido;
import br.com.vendas.udemy.domain.enums.StatusPedido;
import br.com.vendas.udemy.rest.dto.AtualizacaoStatusPedidoDTO;
import br.com.vendas.udemy.rest.dto.InformacaoItemPedidoDTO;
import br.com.vendas.udemy.rest.dto.InformacoesPedidoDTO;
import br.com.vendas.udemy.rest.dto.PedidoDTO;
import br.com.vendas.udemy.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service)  {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return service
                .obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id,
                             @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        service.atualizarStatus(id, StatusPedido.valueOf(novoStatus));

    }
    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }
        return itens.stream().map(
                x -> InformacaoItemPedidoDTO
                        .builder()
                        .descricaoProduto(x.getProduto().getDescricao())
                        .precoUnitario(x.getProduto().getPreco())
                        .quantidade(x.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }
}
