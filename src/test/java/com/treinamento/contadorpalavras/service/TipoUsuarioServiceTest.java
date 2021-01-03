package com.treinamento.contadorpalavras.service;

import com.treinamento.contadorpalavras.Repositorio.TipoAplicacaoRepositorio;
import com.treinamento.contadorpalavras.Repositorio.TipoDocumentoRepositorio;
import com.treinamento.contadorpalavras.Repositorio.TipoUsuarioRepositorio;
import com.treinamento.contadorpalavras.dto.CadastrarTipoUsuarioDto;
import com.treinamento.contadorpalavras.exception.BadRequestException;
import com.treinamento.contadorpalavras.model.TipoAplicacao;
import com.treinamento.contadorpalavras.model.TipoDocumento;
import com.treinamento.contadorpalavras.model.TipoUsuario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class TipoUsuarioServiceTest {
    @InjectMocks
    private TipoUsuarioService tipoUsuarioService;
    @Mock
    private TipoUsuarioRepositorio tipoUsuarioRepositorio;
    @Mock
    private TipoDocumentoRepositorio tipoDocumentoRepositorio;
    @Mock
    private TipoAplicacaoRepositorio tipoAplicacaoRepositorio;


    TipoUsuario tipoUsuario = new TipoUsuario();
    TipoDocumento tipoDocumento = new TipoDocumento();
    TipoAplicacao tipoAplicacao = new TipoAplicacao();
    CadastrarTipoUsuarioDto cadastrarTipoUsuarioDto = new CadastrarTipoUsuarioDto();
    Set<TipoDocumento> setTipoDocumento = new HashSet<>();
    Set<TipoAplicacao> setTipoAplicacao = new HashSet<>();
    List<TipoDocumento> listTipoDocumento = new ArrayList<>();
    List<TipoAplicacao> listTipoAplicacao = new ArrayList<>();


    @Before
    public void init() {
        tipoAplicacao = new TipoAplicacao();
        tipoAplicacao.setNome("website");
        tipoAplicacao.setId(1L);

        tipoDocumento = new TipoDocumento();
        tipoDocumento.setNome("pdf");
        tipoDocumento.setId(1L);

        setTipoDocumento = new HashSet<TipoDocumento>();
        setTipoDocumento.add(tipoDocumento);
        setTipoAplicacao = new HashSet<TipoAplicacao>();
        setTipoAplicacao.add(tipoAplicacao);

        listTipoDocumento = new ArrayList<>();
        listTipoDocumento.add(tipoDocumento);
        listTipoAplicacao = new ArrayList<>();
        listTipoAplicacao.add(tipoAplicacao);


        tipoUsuario = new TipoUsuario();
        tipoUsuario.setNome("teste");
        tipoUsuario.setTipoUsuarioDocumento(setTipoDocumento);
        tipoUsuario.setTipoUsuarioAplicacao(setTipoAplicacao);
        tipoUsuario.setId(1L);

        Set<Long> listaTipoDocumentoIds = new HashSet<Long>();
        listaTipoDocumentoIds.add(tipoDocumento.getId());
        Set<Long> listaTipoAplicacaoIds = new HashSet<Long>();
        listaTipoAplicacaoIds.add(tipoAplicacao.getId());

        cadastrarTipoUsuarioDto = new CadastrarTipoUsuarioDto();
        cadastrarTipoUsuarioDto.setNome(tipoUsuario.getNome());
        cadastrarTipoUsuarioDto.setListaTipoDocumentosPermitidos(listaTipoDocumentoIds);
        cadastrarTipoUsuarioDto.setListaAplicacaoPermitidos(listaTipoAplicacaoIds);

    }

    @Test()
    public void cadastrarTipoUsuarioComSucesso(){
        given(tipoUsuarioRepositorio.save(any()))
                .willReturn(tipoUsuario);

        given(tipoAplicacaoRepositorio.findAllById(anyIterable()))
                .willReturn(listTipoAplicacao);

        given(tipoDocumentoRepositorio.findAllById(anyIterable()))
                .willReturn(listTipoDocumento);


        var result = tipoUsuarioService.cadastrarTipoUsuario(cadastrarTipoUsuarioDto);

        assertNotNull(result);
        verify(tipoUsuarioRepositorio, times(1)).save(any());
    }

    @Test(expected = BadRequestException.class)
    public void verificaTipoAplicacaoComFalha() {
        tipoUsuarioService.verificaDadosInformados(new ArrayList<>(), listTipoDocumento);
    }

    @Test(expected = BadRequestException.class)
    public void verificaTipoDocumentoComFalha() {
        tipoUsuarioService.verificaDadosInformados(listTipoAplicacao, new ArrayList<>());
    }

}
