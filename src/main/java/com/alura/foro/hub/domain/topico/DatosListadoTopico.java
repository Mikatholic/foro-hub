package com.alura.foro.hub.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopico (
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreaion,
        StatusTopico status,
        String autor,
        String curso
){
    public DatosListadoTopico(Topico topico){
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre());
    }
}
