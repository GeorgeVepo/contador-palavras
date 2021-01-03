package com.treinamento.contadorpalavras.dto;


import lombok.Data;

@Data
public class ProcessarDocumentoDto {
    private String documentoBase64;
    private Long idTipoDocumento;
    private Long idTipoAplicacao;
    private Long idUsuario;
}
