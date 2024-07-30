package com.treina_recife.tarefas_api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.treina_recife.tarefas_api.dto.UsuarioDto;
import com.treina_recife.tarefas_api.model.Usuario;
import com.treina_recife.tarefas_api.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(usuario));
	}

	@GetMapping
	public ResponseEntity<List<UsuarioDto>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> buscar(@PathVariable Long id) {
        
		Optional<Usuario> usuario = usuarioService.buscarUsuario(id);

		if (usuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok().body(new UsuarioDto(usuario.get()));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarUser(@PathVariable Long id) {

		Optional<Usuario> usuario = usuarioService.buscarUsuario(id);

		if (Objects.isNull(usuario)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		usuarioService.removerPorId(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
		Optional<Usuario> obj = usuarioService.getUserEmail(email);
		return ResponseEntity.ok().body(obj.get());
	}

	@GetMapping("/data")
	public ResponseEntity<List<Usuario>> filtrarUsuarioPorData(@RequestParam LocalDate inicio,
			@RequestParam LocalDate fim) {
		return ResponseEntity.ok().body(usuarioService.filtrarPorData(inicio, fim));
	}
   
	/**TODO atualizar usuario*/
	
	@PutMapping
	public ResponseEntity<UsuarioDto> atualizar(@RequestBody Usuario usuario){
		  
		if(usuario.getId() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		UsuarioDto usDto = new UsuarioDto(usuarioService.cadastrar(usuario));
		
		return ResponseEntity.ok().body(usDto);
	}
	
	
	@GetMapping("/pesquisa")
	public ResponseEntity<List<UsuarioDto>> getUsuarioNome(@RequestParam String nome){
		var usuario = usuarioService.buscarPorNome(nome);
		return ResponseEntity.ok()
				.body(usuario.stream().map(x -> new UsuarioDto(x)).toList());
	}
	
}
