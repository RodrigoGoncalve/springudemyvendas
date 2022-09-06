package br.com.vendas.udemy.rest.controller;

import br.com.vendas.udemy.domain.entity.Produto;
import br.com.vendas.udemy.domain.repository.ProdutosRepositorie;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutosRepositorie produtosRepositorie;

    public ProdutoController(ProdutosRepositorie produtosRepositorie) {
        this.produtosRepositorie = produtosRepositorie;
    }

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable Integer id){
        return produtosRepositorie.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado "));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@Valid @RequestBody Produto produto){
        return produtosRepositorie.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        produtosRepositorie.findById(id)
                .map(x -> {
                    produtosRepositorie.delete(x);
                    return x;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,@Valid @RequestBody Produto produto){
        produtosRepositorie.findById(id)
                .map(x -> {
                    produto.setId(x.getId());
                    produtosRepositorie.save(produto);
                    return x;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
    }

    @GetMapping
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Example<Produto> example = Example.of(filtro, matcher);
        return produtosRepositorie.findAll(example);
    }
}
