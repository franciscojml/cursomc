package com.fjlima.cursomc.dto;

import java.io.Serializable;

import com.fjlima.cursomc.domain.Estado;

public class EstadoDTO implements Serializable {

	private Integer id;

	private String nome;

	public EstadoDTO() {
		// TODO Auto-generated constructor stub
	}

	public EstadoDTO(Estado obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

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

}
