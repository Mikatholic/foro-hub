package com.alura.foro.hub.domain.respuesta;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        String nombreAutor,
        LocalDateTime fechaCreacion
) {
    public DatosDetalleRespuesta(Respuesta respuesta){
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                getNombreAutorSeguro(respuesta),
                respuesta.getFechaCreacion()
        );
    }

    private static String getNombreAutorSeguro(Respuesta respuesta){
        try{
            return respuesta.getAutor().getNombre();
        }catch (jakarta.persistence.EntityNotFoundException | NullPointerException e){
            return "Usuario Eliminado";
        }
    }
}
