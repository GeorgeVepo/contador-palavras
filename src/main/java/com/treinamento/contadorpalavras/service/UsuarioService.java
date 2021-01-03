package com.treinamento.contadorpalavras.service;

import com.treinamento.contadorpalavras.Repositorio.TipoUsuarioRepositorio;
import com.treinamento.contadorpalavras.Repositorio.UsuarioRepositorio;
import com.treinamento.contadorpalavras.dto.CadastrarUsuarioDto;
import com.treinamento.contadorpalavras.exception.BadRequestException;
import com.treinamento.contadorpalavras.model.TipoDocumento;
import com.treinamento.contadorpalavras.model.TipoUsuario;
import com.treinamento.contadorpalavras.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepositorio usuarioRepositorio;
    private final TipoUsuarioRepositorio tipoUsuarioRepositorio;

    public Long cadastrarUsuario(CadastrarUsuarioDto cadastrarUsuarioDto) {
        TipoUsuario tipoUsuario = obterTipoUsuario(cadastrarUsuarioDto.getIdTipoUsuario());
        Usuario usuario = new Usuario();
        usuario.setNome(cadastrarUsuarioDto.getNome());
        usuario.setTipoUsuario(tipoUsuario);

        return usuarioRepositorio.save(usuario).getId();
    }


    public TipoUsuario obterTipoUsuario(Long idTipoUsuario) {
        return tipoUsuarioRepositorio.findById(idTipoUsuario)
                .orElseThrow(() -> new BadRequestException("O tipo de usuário informado não existe!"));
    }
}
