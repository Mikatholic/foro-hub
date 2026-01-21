package com.alura.foro.hub.controller;

import com.alura.foro.hub.domain.respuesta.*;
import com.alura.foro.hub.domain.topico.TopicoRepository;
import com.alura.foro.hub.domain.usuario.Usuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroRespuesta datos,
                                    @AuthenticationPrincipal Usuario autor){
        var topico = topicoRepository.getReferenceById(datos.idTopico());

        var respuesta = new Respuesta(datos, topico, autor);
        respuestaRepository.save(respuesta);

        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleRespuesta>> listar(@PageableDefault(size = 10, sort = "fechaCreacion")Pageable paginacion){
        var pagina = respuestaRepository.findAll(paginacion).map(DatosDetalleRespuesta::new);
        return ResponseEntity.ok(pagina);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity marcarComoSolucion(@PathVariable Long id){
        var respuesta = respuestaRepository.getReferenceById(id);
        respuesta.marcarComoSolucion();

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionRespuesta datos){
        var respuesta = respuestaRepository.getReferenceById(datos.id());

        respuesta.actualizarMensaje(datos.mensaje());

        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id){
        if(!respuestaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
