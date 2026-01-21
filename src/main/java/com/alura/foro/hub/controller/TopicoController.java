package com.alura.foro.hub.controller;

import com.alura.foro.hub.domain.curso.CursoRepository;
import com.alura.foro.hub.domain.topico.*;
import com.alura.foro.hub.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder){
        if (repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())){
            throw new RuntimeException("Topico duplicado: Ya existe un topico con el mismo tìtulo y mensaje");
        }
        var autor = usuarioRepository.findById(datos.idAutor())
                .orElseThrow(() -> new RuntimeException("El autor con ID " + datos.idAutor() + " no existe"));

        var curso = cursoRepository.findById(datos.idCurso())
                .orElseThrow(() -> new RuntimeException("El curso con ID " + datos.idCurso() + " no existe"));

        Topico topico = repository.save(new Topico(datos, autor, curso));
        System.out.println("Topico registrado" + datos.titulo());

        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos (
            @RequestParam(required = false) String nombreCurso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC)Pageable paginacion){

        Page<DatosListadoTopico> pagina;
        if (nombreCurso != null && anio != null){
            pagina = repository.findByCursoNombreAndAnio(nombreCurso, anio, paginacion).map(DatosListadoTopico::new);
        } else{
            pagina = repository.findAllByStatusNot(StatusTopico.CERRADO, paginacion).map(DatosListadoTopico::new);
        }
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> retornarDatosTopico(@PathVariable Long id){
        Topico topico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos){
        var optionalTopico = repository.findById(id);
        if(!optionalTopico.isPresent()){
        return ResponseEntity.notFound().build();
        }
        var topico = optionalTopico.get();

        if(datos.titulo() != null && datos.mensaje() != null){
            if(repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())){
                throw new RuntimeException("Actualización fallida: ya existe otro topico con este titulo y mensaje");
            }
        }
        topico.actualizarDatos(datos);

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public  ResponseEntity eliminar(@PathVariable Long id){
        var optionalTopico = repository.findById(id);

        if(!optionalTopico.isPresent()){
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
