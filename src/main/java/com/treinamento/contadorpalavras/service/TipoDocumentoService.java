package com.treinamento.contadorpalavras.service;

import com.treinamento.contadorpalavras.Repositorio.TipoDocumentoRepositorio;
import com.treinamento.contadorpalavras.model.TipoAplicacao;
import com.treinamento.contadorpalavras.model.TipoDocumento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipoDocumentoService {
    private final TipoDocumentoRepositorio tipoDocumentoRepositorio;

    public Long cadastrarTipoDocumento(String nomeTipoDocumento) {
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setNome(nomeTipoDocumento);

        return tipoDocumentoRepositorio.save(tipoDocumento).getId();
    }
}
