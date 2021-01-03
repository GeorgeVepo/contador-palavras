package com.treinamento.contadorpalavras.service;

import com.treinamento.contadorpalavras.Repositorio.TipoUsuarioRepositorio;
import com.treinamento.contadorpalavras.Repositorio.UsuarioRepositorio;
import com.treinamento.contadorpalavras.dto.CadastrarUsuarioDto;
import com.treinamento.contadorpalavras.exception.BadRequestException;
import com.treinamento.contadorpalavras.model.TipoDocumento;
import com.treinamento.contadorpalavras.model.TipoUsuario;
import com.treinamento.contadorpalavras.model.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepositorio usuarioRepositorio;
    @Mock
    private TipoUsuarioRepositorio tipousuarioRepositorio;

    Usuario usuario = new Usuario();
    TipoUsuario tipoUsuario = new TipoUsuario();
    CadastrarUsuarioDto cadastrarUsuarioDto = new CadastrarUsuarioDto();

    @Before
    public void init() {
        usuario = new Usuario();
        usuario.setNome("teste");
        usuario.setId(1L);

        tipoUsuario = new TipoUsuario();
        tipoUsuario.setNome("teste");
        tipoUsuario.setId(1L);

        cadastrarUsuarioDto = new CadastrarUsuarioDto();
        cadastrarUsuarioDto.setIdTipoUsuario(1L);
        cadastrarUsuarioDto.setNome("teste");
    }

    @Test()
    public void cadastrarUsuarioComSucesso(){
        given(usuarioRepositorio.save(any()))
                .willReturn(usuario)
        ;
        given(tipousuarioRepositorio.findById(anyLong()))
                .willReturn(Optional.of(tipoUsuario));

        var result = usuarioService.cadastrarUsuario(cadastrarUsuarioDto);

        assertNotNull(result);
        verify(usuarioRepositorio, times(1)).save(any());
    }

    @Test(expected = BadRequestException.class)
    public void obterTipoUsuarioComFalha() {
        given(tipousuarioRepositorio.findById(anyLong()))
                .willReturn(Optional.empty());

        var result = usuarioService.obterTipoUsuario(1L);

        assertNull(result);
        verify(tipousuarioRepositorio, times(1)).findById(any());

    }
}
