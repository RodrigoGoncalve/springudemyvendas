package br.com.vendas.udemy.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "descricao", length = 100)
    @NotEmpty(message = "Campo DESCRICAO é obrigatório")
    private String descricao;

    @Column(name = "preco_unitario")
    @NotNull(message = "O campo PREÇO é obrigatório> ")
    private BigDecimal preco;

}
