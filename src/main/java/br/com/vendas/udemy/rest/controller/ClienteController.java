package br.com.vendas.udemy.rest.controller;

import br.com.vendas.udemy.domain.entity.Cliente;
import br.com.vendas.udemy.domain.repository.ClienteRepositorie;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClienteRepositorie clienteRepositorie;

    public ClienteController(ClienteRepositorie clienteRepositorie) {
        this.clienteRepositorie = clienteRepositorie;
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable ("id") Integer id){
        return clienteRepositorie
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado "));

    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@Valid @RequestBody Cliente cliente){
        return clienteRepositorie.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
      clienteRepositorie.findById(id)
              .map(cliente -> {
                   clienteRepositorie.delete(cliente);
                   return cliente;
              })
              .orElseThrow(() ->
                      new ResponseStatusException(HttpStatus.NOT_FOUND,
                              "Cliente não encontrado "));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente){

      clienteRepositorie.findById(id)
              .map(clienteExistente -> {
                  cliente.setId(clienteExistente.getId());
                  clienteRepositorie.save(cliente);
                  return clienteExistente;
        }).orElseThrow(() ->
                      new ResponseStatusException(HttpStatus.NOT_FOUND,
                              "Cliente não encontrado "));
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Example<Cliente> example = Example.of(filtro, matcher);
        return clienteRepositorie.findAll(example);
    }
}
