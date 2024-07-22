package com.treina_recife.tarefas_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto2 {
	
	private Long id;
	private String nome;
	private String email;
	private Integer idade;
		

}
