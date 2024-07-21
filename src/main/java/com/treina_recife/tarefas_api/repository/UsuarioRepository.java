package com.treina_recife.tarefas_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treina_recife.tarefas_api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
