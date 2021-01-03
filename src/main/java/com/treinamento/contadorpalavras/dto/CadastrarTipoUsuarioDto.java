package com.treinamento.contadorpalavras.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CadastrarTipoUsuarioDto {
    private String nome;
    private Set<Long> listaTipoDocumentosPermitidos;
    private Set<Long> listaAplicacaoPermitidos;
}
