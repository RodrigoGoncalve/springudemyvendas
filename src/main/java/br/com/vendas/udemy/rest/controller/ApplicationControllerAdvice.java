package br.com.vendas.udemy.rest.controller;

import br.com.vendas.udemy.exception.PedidoNaoEncontradoException;
import br.com.vendas.udemy.exception.RegraNegocioException;
import br.com.vendas.udemy.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegaraNegocioException(RegraNegocioException ex){
        String mensagem = ex.getMessage();
        return new ApiErrors(mensagem);
    }
    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors pedidoNotFoundException(PedidoNaoEncontradoException ex){
        String mensagem = ex.getMessage();
        return new ApiErrors(mensagem);
    }
}
