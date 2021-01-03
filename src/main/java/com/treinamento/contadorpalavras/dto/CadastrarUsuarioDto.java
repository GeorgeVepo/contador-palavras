package com.treinamento.contadorpalavras.dto;

import lombok.Data;

import java.util.List;

@Data
public class CadastrarUsuarioDto {
    private String nome;
    private Long idTipoUsuario;
}
