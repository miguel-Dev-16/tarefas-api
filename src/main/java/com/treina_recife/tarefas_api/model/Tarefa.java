package com.treina_recife.tarefas_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tbl_tarefa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {
  private String nome;
 	
	
}
