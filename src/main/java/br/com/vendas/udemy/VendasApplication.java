package br.com.vendas.udemy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Value("${application.name}")
    private String applicationname;

    @GetMapping("/hello")
    public String hellWord(){
        return applicationname;
    }


    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
