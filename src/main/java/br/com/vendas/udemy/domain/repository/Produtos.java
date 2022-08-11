package br.com.vendas.udemy.domain.repository;

import br.com.vendas.udemy.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
