package com.treinamento.contadorpalavras.Repositorio;

import com.treinamento.contadorpalavras.model.TipoAplicacao;
import com.treinamento.contadorpalavras.model.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoAplicacaoRepositorio extends JpaRepository<TipoAplicacao, Long> {

}
