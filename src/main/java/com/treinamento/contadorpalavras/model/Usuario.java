package com.treinamento.contadorpalavras.model;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_usuario_id", referencedColumnName = "tipo_usuario_id")
    private TipoUsuario tipoUsuario;

}
