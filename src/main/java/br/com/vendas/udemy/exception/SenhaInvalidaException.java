package br.com.vendas.udemy.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Senha Invalida.");
    }
}
