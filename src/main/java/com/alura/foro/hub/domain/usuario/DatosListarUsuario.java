package com.alura.foro.hub.domain.usuario;

public record DatosListarUsuario (
        Long id,
        String nombre,
        String correoElectronico
) {
    public DatosListarUsuario (Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico()
        );
    }
}
