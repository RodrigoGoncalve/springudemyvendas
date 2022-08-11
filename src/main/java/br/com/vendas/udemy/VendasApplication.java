package br.com.vendas.udemy;

import br.com.vendas.udemy.domain.entity.Cliente;
import br.com.vendas.udemy.domain.entity.Pedido;
import br.com.vendas.udemy.domain.repository.Clientes;
import br.com.vendas.udemy.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(
            @Autowired Clientes clientes,
            @Autowired Pedidos peidos) {

        return args -> {
            System.out.println("------------- Salvando Clientes -------------");
            System.out.println("");

            Cliente fulano = new Cliente("Fulano");
            clientes.save(fulano);

            Pedido p = new Pedido();
            p.setCliente(fulano);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));

            peidos.save(p);

 //           Cliente cliente = clientes.findClienteFetchPedido(fulano.getId());
 //           System.out.println(cliente);
 //           System.out.println(cliente.getPedidos());
            peidos.findByCliente(fulano).forEach(System.out::println/);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
