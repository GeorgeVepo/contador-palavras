package com.treinamento.contadorpalavras.controller;

import com.treinamento.contadorpalavras.dto.ContagemPalavrasDto;
import com.treinamento.contadorpalavras.dto.ProcessarDocumentoDto;
import com.treinamento.contadorpalavras.service.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documento")
public class DocumentoController {
    private final DocumentoService documentoService;

    @PostMapping("/processar")
    public ResponseEntity<String> processarDocumento(@RequestBody @Valid ProcessarDocumentoDto processarDocumentoDto){
        return ResponseEntity.status(200).body(this.documentoService.processarDocumento(processarDocumentoDto));
    }


}
