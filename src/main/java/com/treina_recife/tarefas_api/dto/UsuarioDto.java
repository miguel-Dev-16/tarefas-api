package com.treina_recife.tarefas_api.dto;

import com.treina_recife.tarefas_api.model.Usuario;

import lombok.Getter;

@Getter
public class UsuarioDto {
	private  Long id;
	private  String nome;
	private  String email;
	
	public UsuarioDto(Usuario entity) {
		id   = entity.getId();
		nome = entity.getNome();
		email = entity.getEmail();
	}
	
}
