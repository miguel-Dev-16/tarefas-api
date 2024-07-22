package com.treina_recife.tarefas_api.model;

import java.time.LocalDate;
import java.time.Period;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.treina_recife.tarefas_api.dto.UsuarioDto2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tar_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long id;
   
   @Column(nullable = false)
   private  String nome;
   
   @Column(nullable = false, unique = true)
   private  String email;
   
   @Column(nullable = false)
   private  String senha;
   
   @JsonFormat(pattern = "dd/MM/yyyy")
   @Column(nullable = false)
   private  LocalDate dataNascimento;
   
   //metodo para converter usuario para dto
   public UsuarioDto2 convertParaDto() {
	   UsuarioDto2 dto = new UsuarioDto2();
	   dto.setId(id);
	   dto.setNome(nome);
	   dto.setEmail(email);
	   dto.setIdade(Period.between(dataNascimento, LocalDate.now()).getYears());
	
	   return dto;
   }
   
}
