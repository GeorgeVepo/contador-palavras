package com.treinamento.contadorpalavras.controller;

import com.treinamento.contadorpalavras.dto.CadastrarUsuarioDto;
import com.treinamento.contadorpalavras.service.TipoUsuarioService;
import com.treinamento.contadorpalavras.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<Long> cadastrarUsuario(@RequestBody @Valid CadastrarUsuarioDto cadastrarUsuarioDto){
        return ResponseEntity.status(201).body(this.usuarioService.cadastrarUsuario(cadastrarUsuarioDto));
    }
}
