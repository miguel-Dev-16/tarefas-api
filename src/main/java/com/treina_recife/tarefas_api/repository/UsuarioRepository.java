package com.treina_recife.tarefas_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treina_recife.tarefas_api.model.Usuario;
import java.time.LocalDate;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
	//aqui ele busca por palavras exemplo as duas primeiras letras "mi"
	List<Usuario> findByNomeLike(String nome);
	
	//buscar por email do usuáro
	Optional<Usuario> findByEmail(String email);
	
	//buscar em ma faxa de datas
	List<Usuario> findByDataNascimentoBetween(LocalDate inicio, LocalDate fim);
	
}
