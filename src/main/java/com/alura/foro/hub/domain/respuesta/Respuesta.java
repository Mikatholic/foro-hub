package com.alura.foro.hub.domain.respuesta;

import com.alura.foro.hub.domain.topico.Topico;
import com.alura.foro.hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @JdbcTypeCode(Types.TINYINT)
    private Boolean solucion = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    public Respuesta(DatosRegistroRespuesta datos, Topico topico, Usuario autor){
        this.mensaje = datos.mensaje();
        this.topico = topico;
        this.autor = autor;
        this.fechaCreacion = LocalDateTime.now();
        this.solucion = false;
    }

    public void marcarComoSolucion(){
        this.solucion = true;
    }

    public void actualizarMensaje(String nuevoMensaje){
        if(nuevoMensaje != null && !nuevoMensaje.isBlank()){
            this.mensaje = nuevoMensaje;
        }
    }
}
