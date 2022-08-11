package br.com.vendas.udemy.domain.repository;

import br.com.vendas.udemy.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
}
