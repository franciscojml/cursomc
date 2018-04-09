package com.fjlima.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fjlima.cursomc.domain.Cidade;
import com.fjlima.cursomc.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;

	public List<Cidade> findByEstado(Integer estado_id) {
		return repo.findCidades(estado_id);
	}

}
