package com.alura.foro.hub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    @Query("""
            SELECT t FROM Topico t
            WHERE t.curso.nombre = :nombreCurso
            AND YEAR(t.fechaCreacion) = :anio
            """)
    Page<Topico> findByCursoNombreAndAnio(String nombreCurso, int anio, Pageable paginacion);

    Page<Topico> findAllByStatusNot(StatusTopico status, Pageable paginacion);

    Page<Topico> findAll(Pageable paginacion);

}
