package com.treinamento.contadorpalavras.Repositorio;

import com.treinamento.contadorpalavras.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoDocumentoRepositorio extends JpaRepository<TipoDocumento, Long> {

}
