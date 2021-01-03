package com.treinamento.contadorpalavras.controller;

import com.treinamento.contadorpalavras.dto.CadastrarTipoUsuarioDto;
import com.treinamento.contadorpalavras.service.TipoUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/tipousuario")
public class TipoUsuarioController {
    private final TipoUsuarioService tipoUsuarioService;

    @PostMapping()
    public ResponseEntity<Long> cadastrarTipoUsuario(@RequestBody @Valid CadastrarTipoUsuarioDto cadastrarTipoUsuarioDto){
        return ResponseEntity.status(201).body(this.tipoUsuarioService.cadastrarTipoUsuario(cadastrarTipoUsuarioDto));
    }

}
