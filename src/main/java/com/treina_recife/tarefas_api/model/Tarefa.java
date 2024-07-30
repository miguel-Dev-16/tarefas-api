package com.treina_recife.tarefas_api.model;

import java.time.LocalDate;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_tarefa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String titulo;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(nullable = false)
	private LocalDate dataEntrega;
	
	//quando estamos fazendo associação colocamos JoinColumn e não Column
	//associação de classe usuário.
	@ManyToOne
	@JoinColumn(nullable = false, name="usuario_id")
	private Usuario responsavel;
	
}
