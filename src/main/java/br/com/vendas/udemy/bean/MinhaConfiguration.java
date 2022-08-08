package br.com.vendas.udemy.bean;

import br.com.vendas.udemy.bean.Dev;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
@Dev
public class MinhaConfiguration {

    @Bean
    public CommandLineRunner executar(){
        return args -> {
            System.out.println("Rodando no profile de desenvolvimento");
        };
    }


}
