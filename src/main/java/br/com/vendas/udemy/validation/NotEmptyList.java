package br.com.vendas.udemy.validation;

import br.com.vendas.udemy.validation.contraintvalidation.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {

    String message () default "Lista não pode ser vazia";
    Class<?> [] groups () default {};
    Class<? extends Payload>[] payload () default {};
}
