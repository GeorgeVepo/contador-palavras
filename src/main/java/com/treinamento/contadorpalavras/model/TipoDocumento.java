package com.treinamento.contadorpalavras.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "tipo_documento")
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_documento_id")
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "tipoUsuarioDocumento")
    Set<TipoUsuario> tipoDocumentoUsuario;

}
