package com.devoliga.ifood.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devoliga.ifood.model.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	public Optional<Usuario> findByUsuario(String usuario);
}
