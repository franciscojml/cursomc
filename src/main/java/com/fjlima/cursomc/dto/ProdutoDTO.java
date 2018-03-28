package com.fjlima.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fjlima.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {

	private Integer id;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;

	private Double preco;

	public ProdutoDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProdutoDTO(Produto obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.preco = obj.getPreco();
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

}
