package com.treina_recife.tarefas_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treina_recife.tarefas_api.dto.TarefaDto;
import com.treina_recife.tarefas_api.model.Tarefa;
import com.treina_recife.tarefas_api.repository.TarefaRepositoy;

@Service
public class TarefaService {

	@Autowired
	private TarefaRepositoy tarefaRepositoy;

	public Tarefa salvar(Tarefa obj) {
		return tarefaRepositoy.save(obj);
	}

	public List<TarefaDto> findAllTarefa() {
		List<Tarefa> t = tarefaRepositoy.findAll();
		return t.stream().map(x -> new TarefaDto(x)).toList();
	}

	public Optional<Tarefa> buscarUsuario(Long id) {
		return tarefaRepositoy.findById(id);
	}

	public void removerPorId(Long id) {
		tarefaRepositoy.deleteById(id);
	}
	
	
	public List<Tarefa> listarTarefaPorDataEntrega(LocalDate inicio, LocalDate fim){
		return tarefaRepositoy.findByDataEntregaBetween(inicio, fim);
	}
	
	public Optional<Tarefa> buscarTitulo(String txt){
		return tarefaRepositoy.findByTituloLike(txt);
	}

}
