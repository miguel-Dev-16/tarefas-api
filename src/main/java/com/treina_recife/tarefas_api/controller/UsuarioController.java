package com.treina_recife.tarefas_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treina_recife.tarefas_api.dto.UsuarioDto;
import com.treina_recife.tarefas_api.model.Usuario;
import com.treina_recife.tarefas_api.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
    @Autowired
	private UsuarioService usuarioService;
	
    
    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
    	return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(usuario));   	
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> findAll(){
    	return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id){
    	
    	Optional<Usuario> u = usuarioService.buscarUsuario(id);
    	
    	if(u.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	 return ResponseEntity.ok().body(u.get());
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUser(@PathVariable Long id){
    	
    	Optional<Usuario> u = usuarioService.buscarUsuario(id);
    	
    	if(u.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	
    	//criar o metodo de deletar no service
    	
    	 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    	
    }
    
    
	
}
