package com.alura.foro.hub.controller;

import com.alura.foro.hub.domain.usuario.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroUsuario datos){
        String contrasenaEncriptada = passwordEncoder.encode(datos.contrasena());

        var usuario = new Usuario(datos.nombre(), datos.correoElectronico(), contrasenaEncriptada);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @GetMapping
    public ResponseEntity<Page<DatosListarUsuario>> listar(@PageableDefault(size = 10, sort = "nombre")Pageable paginacion){
        var pagina = usuarioRepository.findAll(paginacion).map(DatosListarUsuario::new);
        return ResponseEntity.ok(pagina);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionUsuario datos){
        Usuario usuario = usuarioRepository.getReferenceById(datos.id());

        usuario.actualizarDatos(datos);

        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id){
        var usuario = usuarioRepository.getReferenceById(id);

        usuario.eliminar();

        return ResponseEntity.noContent().build();
    }
}
