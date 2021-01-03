package com.treinamento.contadorpalavras.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Data
@Entity
@Table(name = "tipo_usuario")
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_usuario_id")
    private Long id;

    private String nome;

    @ManyToMany
    @JoinTable(
            name = "tipo_usuario_aplicacao",
            joinColumns = @JoinColumn(name = "tipo_usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_aplicacao_id"))
    Set<TipoAplicacao> tipoUsuarioAplicacao;

    @ManyToMany
    @JoinTable(
            name = "tipo_usuario_documento",
            joinColumns = @JoinColumn(name = "tipo_usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_documento_id"))
    Set<TipoDocumento> tipoUsuarioDocumento;


}
