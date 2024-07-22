package com.treina_recife.tarefas_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treina_recife.tarefas_api.model.Tarefa;
import com.treina_recife.tarefas_api.service.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
	
    @Autowired
	private TarefaService tarefaService;
	
    @PostMapping
    public ResponseEntity<Tarefa> salvar(@RequestBody Tarefa obj){
    	return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.salvar(obj));
    }
    
 
    
}
