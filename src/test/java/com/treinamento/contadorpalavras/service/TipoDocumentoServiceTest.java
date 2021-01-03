package com.treinamento.contadorpalavras.service;

import com.treinamento.contadorpalavras.Repositorio.TipoDocumentoRepositorio;
import com.treinamento.contadorpalavras.model.TipoAplicacao;
import com.treinamento.contadorpalavras.model.TipoDocumento;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class TipoDocumentoServiceTest {
    @InjectMocks
    private TipoDocumentoService tipoDocumentoService;
    @Mock
    private TipoDocumentoRepositorio tipoDocumentoRepositorio;

    TipoDocumento tipoDocumento = new TipoDocumento();

    @Before
    public void init() {
        tipoDocumento = new TipoDocumento();
        tipoDocumento.setNome("pdf");
        tipoDocumento.setId(1L);
    }

    @Test()
    public void cadastrarTipoDocumentoComSucesso(){
        given(tipoDocumentoRepositorio.save(any()))
                .willReturn(tipoDocumento);

        var result = tipoDocumentoService.cadastrarTipoDocumento("teste");

        assertNotNull(result);
        verify(tipoDocumentoRepositorio, times(1)).save(any());
    }

}
