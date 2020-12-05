package com.api_pedidos.dto;

import java.io.Serializable;

import com.api_pedidos.domain.Categoria;

public class categoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public categoriaDTO() {
		
	}
	
	public categoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
	}
	
	
}
