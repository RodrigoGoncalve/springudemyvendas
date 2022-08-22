package br.com.vendas.udemy.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDTO {

    private Integer cliente;
    private BigDecimal total;
    private List<ItensPedidoDTO> itens;

}
