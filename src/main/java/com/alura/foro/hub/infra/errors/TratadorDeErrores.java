package com.alura.foro.hub.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    // Error 404: cuando el ID no existe en la base de datos
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }

    // Error 400: cuando fallan las validaciones de los @Valid (campos en blanco, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion:: new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    //Error 500 o excepciones de negocio: como el topico duplicado
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity tratarErrorDeNegocio(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
