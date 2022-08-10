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

            clientes.save(new Cliente("Rodrigo"));
            clientes.save(new Cliente("Paula"));

            List<Cliente> list = clientes.findByNomeLike("Rodrigo");
            list.forEach(System.out::println);


            boolean existe = clientes.existsByNome("Rodrigo");
            System.out.println("Existe um cliente com este nome ? "+existe);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
