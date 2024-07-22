package com.treina_recife.tarefas_api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treina_recife.tarefas_api.model.Tarefa;
import com.treina_recife.tarefas_api.model.Usuario;

public interface TarefaRepositoy extends JpaRepository<Tarefa, Long>{
	
  //	findBy nome do atributo e quando você coloca o _ você pega o atributo do atributo.
  /*ele vai pegar o valor do atributo responsavel na classe tarefa, mais se eu colocasse so
   * isso eu teria que passar o objeto completo exemplo:
   * List<Tarefa> findByResponsavel(Usuario obj); e eu não quero ter que passar um objeto 
   * completo para buscar o responsavel, por boas práticas eu passo o id ai no caso 
   * eu pego o atributo responsavel da classe tarefa e depois pego o id atraves do proprio
   * atributo que tem a associação que seria responsalvel_id colocamos o _ para separar 
   * a busca com atributos.
   * */	
	
	List<Tarefa> findByResponsavel_Id(long id);
	
	Optional<Tarefa> findByTituloLike(String titulo);
	
	List<Tarefa> findByDataEntregaBetween(LocalDate inicio, LocalDate fim);
    
}
