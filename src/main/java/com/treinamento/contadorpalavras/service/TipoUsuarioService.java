package com.treinamento.contadorpalavras.service;

import com.treinamento.contadorpalavras.Repositorio.TipoAplicacaoRepositorio;
import com.treinamento.contadorpalavras.Repositorio.TipoDocumentoRepositorio;
import com.treinamento.contadorpalavras.Repositorio.TipoUsuarioRepositorio;
import com.treinamento.contadorpalavras.dto.CadastrarTipoUsuarioDto;
import com.treinamento.contadorpalavras.exception.BadRequestException;
import com.treinamento.contadorpalavras.model.TipoAplicacao;
import com.treinamento.contadorpalavras.model.TipoDocumento;
import com.treinamento.contadorpalavras.model.TipoUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoUsuarioService {
    private final TipoUsuarioRepositorio tipoUsuarioRepositorio;
    private final TipoAplicacaoRepositorio tipoAplicacaoRepositorio;
    private final TipoDocumentoRepositorio tipoDocumentoRepositorio;

    public Long cadastrarTipoUsuario(CadastrarTipoUsuarioDto cadastrarTipoUsuarioDto) {
        List<TipoAplicacao> listaTipoAplicacao = tipoAplicacaoRepositorio
                .findAllById(cadastrarTipoUsuarioDto.getListaAplicacaoPermitidos());;
        List<TipoDocumento> listaTipoDocumento = tipoDocumentoRepositorio
                .findAllById(cadastrarTipoUsuarioDto.getListaTipoDocumentosPermitidos());

        verificaDadosInformados(listaTipoAplicacao, listaTipoDocumento);

        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setNome(cadastrarTipoUsuarioDto.getNome());
        tipoUsuario.setTipoUsuarioAplicacao(new HashSet<>(listaTipoAplicacao));
        tipoUsuario.setTipoUsuarioDocumento(new HashSet<>(listaTipoDocumento));

        return tipoUsuarioRepositorio.save(tipoUsuario).getId();
    }

    public void verificaDadosInformados(List<TipoAplicacao> listaTipoAplicacao, List<TipoDocumento> listaTipoDocumento){
        if(listaTipoAplicacao.isEmpty()){
            throw new BadRequestException("Nenhum tipo de aplicação informado é válido.");
        }

        if(listaTipoDocumento.isEmpty()){
            throw new BadRequestException("Nenhum tipo de documento informado é válido.");
        }
    }

}
