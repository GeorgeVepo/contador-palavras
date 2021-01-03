package com.treinamento.contadorpalavras.controller;

import com.treinamento.contadorpalavras.dto.ContagemPalavrasDto;
import com.treinamento.contadorpalavras.dto.ProcessarDocumentoDto;
import com.treinamento.contadorpalavras.service.TipoAplicacaoService;
import com.treinamento.contadorpalavras.service.TipoDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/tipoaplicacao")
public class TipoAplicacaoController {
    private final TipoAplicacaoService tipoAplicacaoService;

    @PostMapping("/{nomeAplicacao}")
    public ResponseEntity<Long> cadastrarTipoAplicacao(@PathVariable("nomeAplicacao") String nomeAplicacao){
        return ResponseEntity.status(201).body(this.tipoAplicacaoService.cadastrarTipoAplicacao(nomeAplicacao));
    }
}
