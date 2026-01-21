package com.alura.foro.hub.domain.usuario;

public record DatosDetalleUsuario(
        Long id,
        String nombre,
        String email,
        Boolean activo
) {
    public DatosDetalleUsuario(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                usuario.getActivo()
        );
    }
}
