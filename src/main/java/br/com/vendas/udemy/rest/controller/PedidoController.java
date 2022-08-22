package br.com.vendas.udemy.rest.controller;

import br.com.vendas.udemy.domain.entity.Pedido;
import br.com.vendas.udemy.rest.dto.PedidoDTO;
import br.com.vendas.udemy.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service)  {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

}
