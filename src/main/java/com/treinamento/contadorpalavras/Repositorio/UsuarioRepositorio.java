package com.treinamento.contadorpalavras.Repositorio;

import com.treinamento.contadorpalavras.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
}
