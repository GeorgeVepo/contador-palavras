package com.treinamento.contadorpalavras.controller;


import com.treinamento.contadorpalavras.service.TipoDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/tipodocumento")
public class TipoDocumentoController {
    private final TipoDocumentoService tipodocumentoService;

    @PostMapping("/{nomeTipoDocumento}")
    public ResponseEntity<Long> cadastrarTipoDocumento(@PathVariable("nomeTipoDocumento") String nomeTipoDocumento){
        return ResponseEntity.status(201).body(this.tipodocumentoService.cadastrarTipoDocumento(nomeTipoDocumento));
    }
}
