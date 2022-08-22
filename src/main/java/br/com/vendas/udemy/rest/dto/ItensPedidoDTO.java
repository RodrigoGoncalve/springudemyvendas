package br.com.vendas.udemy.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItensPedidoDTO {

    private Integer produtos;
    private Integer quantidade;
}
