package com.treinamento.contadorpalavras.Repositorio;

import com.treinamento.contadorpalavras.model.TipoDocumento;
import com.treinamento.contadorpalavras.model.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoUsuarioRepositorio extends JpaRepository<TipoUsuario, Long> {
    @Query("SELECT COUNT(u) FROM TipoUsuario u left join u.tipoUsuarioDocumento d WHERE u.id=:idUsuario AND d.id=:idTipoDocumento")
    Integer possuiPermissaoTipoDocumento(@Param("idUsuario") Long idTipoUsuario, @Param("idTipoDocumento") Long idTipoDocumento);

    @Query("SELECT COUNT(u) FROM TipoUsuario u left join u.tipoUsuarioAplicacao a WHERE u.id=:idUsuario AND a.id=:idTipoAplicativo")
    Integer possuiPermissaoTipoAplicacao(@Param("idUsuario") Long idTipoUsuario, @Param("idTipoAplicativo") Long idTipoAplicativo);
}
