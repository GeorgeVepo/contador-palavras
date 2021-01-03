package com.treinamento.contadorpalavras.service;

import com.treinamento.contadorpalavras.Repositorio.TipoAplicacaoRepositorio;
import com.treinamento.contadorpalavras.model.TipoAplicacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipoAplicacaoService {
    private final TipoAplicacaoRepositorio tipoAplicacaoRepositorio;

    public Long cadastrarTipoAplicacao(String nomeAplicacao) {
        TipoAplicacao tipoAplicacao = new TipoAplicacao();
        tipoAplicacao.setNome(nomeAplicacao);

        return tipoAplicacaoRepositorio.save(tipoAplicacao).getId();
    }
}
