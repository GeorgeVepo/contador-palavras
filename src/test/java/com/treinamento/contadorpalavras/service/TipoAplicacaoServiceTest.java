package com.treinamento.contadorpalavras.service;

import com.treinamento.contadorpalavras.Repositorio.TipoAplicacaoRepositorio;
import com.treinamento.contadorpalavras.model.TipoAplicacao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class TipoAplicacaoServiceTest {
    @InjectMocks
    private TipoAplicacaoService tipoAplicacaoService;
    @Mock
    private TipoAplicacaoRepositorio tipoAplicacaoRepositorio;

    TipoAplicacao tipoAplicacao = new TipoAplicacao();

    @Before
    public void init() {
        tipoAplicacao = new TipoAplicacao();
        tipoAplicacao.setNome("website");
        tipoAplicacao.setId(1L);
    }

    @Test()
    public void cadastrarTipoAplicacaoComSucesso(){
        given(tipoAplicacaoRepositorio.save(any()))
                .willReturn(tipoAplicacao);

        var result = tipoAplicacaoService.cadastrarTipoAplicacao("teste");

        assertNotNull(result);
        verify(tipoAplicacaoRepositorio, times(1)).save(any());
    }

}
