package com.treinamento.contadorpalavras.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Data
@Entity
@Table(name = "tipo_aplicacao")
public class TipoAplicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_aplicacao_id")
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "tipoUsuarioAplicacao")
    Set<TipoUsuario> tipoAplicacaoUsuario;
}
