package com.treina_recife.tarefas_api.dto;

import java.time.LocalDate;

import com.treina_recife.tarefas_api.model.Tarefa;

import lombok.Getter;

@Getter
public class TarefaDto {
	private Long id;
	private String titulo;
	private LocalDate dataEntrega;
	
	public TarefaDto(Tarefa entity) {
		id = entity.getId();
		titulo = entity.getTitulo();
		dataEntrega = entity.getDataEntrega();
	}
}
