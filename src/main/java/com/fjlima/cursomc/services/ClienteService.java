package com.fjlima.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fjlima.cursomc.domain.Cidade;
import com.fjlima.cursomc.domain.Cliente;
import com.fjlima.cursomc.domain.Endereco;
import com.fjlima.cursomc.domain.enums.TipoCliente;
import com.fjlima.cursomc.dto.ClienteDTO;
import com.fjlima.cursomc.dto.ClienteNewDTO;
import com.fjlima.cursomc.repository.CidadeRepository;
import com.fjlima.cursomc.repository.ClienteRepository;
import com.fjlima.cursomc.services.exceptions.DataIntegrityException;
import com.fjlima.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private CidadeRepository cidadeRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(objDTO.getTipo()));
		Optional<Cidade> cid = cidadeRepository.findById(objDTO.getCidadeId());
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
				objDTO.getBairro(), objDTO.getCep(), cli, cid.get());
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());

		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}

		if (objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}

		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setEmail(obj.getEmail());
		newObj.setNome(obj.getNome());
	}

}
