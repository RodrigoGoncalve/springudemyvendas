package br.com.vendas.udemy;

import br.com.vendas.udemy.domain.entity.Cliente;
import br.com.vendas.udemy.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes) {
        return args -> {
            System.out.println("------------- Salvando Clientes -------------");
            System.out.println("");

            clientes.salvar(new Cliente("Rodrigo"));
            clientes.salvar(new Cliente("Paula"));

            List<Cliente> list = clientes.obterTodos();
            list.forEach(System.out::println);

            System.out.println("------------- Atualizando Clientes -------------");
            System.out.println("");

            list.forEach(c -> {
                c.setNome(c.getNome() + " atualizar.");
                clientes.atualizar(c);
            });

            list = clientes.obterTodos();
            list.forEach(System.out::println);

            System.out.println("------------- Buscando Clientes -------------");
            System.out.println("");
            clientes.buscarPorNome("ri").forEach(System.out::println);

            System.out.println("-----------------------------------------------");
            System.out.println("");

            System.out.println("------------- Deletando Clientes -------------");
            System.out.println("");

            clientes.obterTodos().forEach(c -> {
                clientes.deletar(2);
            });

            System.out.println("--------------------FIM---------------------------");

            list = clientes.obterTodos();
            if (list.isEmpty()){
                System.out.println("Nenhum cliente encontrado");
            }else {
                list.forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
