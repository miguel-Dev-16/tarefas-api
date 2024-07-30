package com.treina_recife.tarefas_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treina_recife.tarefas_api.model.Usuario;
import java.time.LocalDate;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
	List<Usuario> findByNomeLike(String nome);
	
	Optional<Usuario> findByEmail(String email);
	
	List<Usuario> findByDataNascimentoBetween(LocalDate inicio, LocalDate fim);
	
}
