package br.com.vendas.udemy.domain.repository;

import br.com.vendas.udemy.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClienteRepositorie extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNomeLike(String nome);
    @Query("select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNomeJpql(@Param("nome") String nome);

    @Query(value = "select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> encontrarPorNomeNative(@Param("nome") String nome);
    List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);
    @Query(" delete from Cliente c where c.nome =:nome ")
    @Modifying//é obrigatorio em querys que fazem modificações na base de dados
    void deleteByNome(String nome);
    boolean existsByNome(String nome);
    @Query("select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClienteFetchPedido(@Param("id") Integer id);
}
