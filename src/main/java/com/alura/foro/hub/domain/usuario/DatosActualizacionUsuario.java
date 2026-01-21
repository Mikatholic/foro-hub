package com.alura.foro.hub.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionUsuario(
        @NotNull Long id,
        String nombre,
        String email,
        String contrasena
) {
}
