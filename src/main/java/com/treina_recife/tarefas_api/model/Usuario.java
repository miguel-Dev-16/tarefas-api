package com.treina_recife.tarefas_api.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
   
   
}
