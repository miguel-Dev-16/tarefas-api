package com.treina_recife.tarefas_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treina_recife.tarefas_api.dto.UsuarioDto;
import com.treina_recife.tarefas_api.model.Tarefa;
import com.treina_recife.tarefas_api.model.Usuario;
import com.treina_recife.tarefas_api.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public Usuario cadastrar(Usuario objs) {
	  return usuarioRepository.save(objs);//ele retorna o objeto cadastrado.
	}
	
	public List<UsuarioDto> listarUsuarios(){
		List<Usuario> us =  usuarioRepository.findAll();
		return us.stream().map(x -> new UsuarioDto(x)).toList();
	}
	
	public UsuarioDto pegaUsuario(Long id) {
		Usuario u = usuarioRepository.findById(id).get();
		return new UsuarioDto(u);
	}
	
	/*a diferença do optional para usar o get() é que ele já trata a exceção*/
	public Optional<Usuario> buscarUsuario(Long id){
		return usuarioRepository.findById(id);
	}
	
	public void removerPorId(Long id) {
		usuarioRepository.deleteById(id);
	}
	
	public Optional<Usuario> getUserEmail(String email){
		return usuarioRepository.findByEmail(email);
	}
	
	public List<Usuario> filtrarPorData(LocalDate inicio, LocalDate fim){
		return usuarioRepository.findByDataNascimentoBetween(inicio, fim);
	}
	
}
