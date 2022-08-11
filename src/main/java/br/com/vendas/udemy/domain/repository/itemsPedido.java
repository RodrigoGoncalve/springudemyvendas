package br.com.vendas.udemy.domain.repository;

import br.com.vendas.udemy.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface itemsPedido extends JpaRepository<ItemPedido, Integer> {
}
