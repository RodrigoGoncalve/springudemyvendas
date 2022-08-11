package br.com.vendas.udemy.domain.repository;

import br.com.vendas.udemy.domain.entity.Cliente;
import br.com.vendas.udemy.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

    List<Pedido>findByCliente(Cliente cliente);
}
