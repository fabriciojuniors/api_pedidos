package com.api_pedidos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api_pedidos.domain.Cidade;
import com.api_pedidos.domain.Cliente;
import com.api_pedidos.domain.Endereco;
import com.api_pedidos.domain.enums.TipoCliente;
import com.api_pedidos.dto.ClienteDTO;
import com.api_pedidos.dto.ClienteNewDTO;
import com.api_pedidos.repositories.CidadeRepository;
import com.api_pedidos.repositories.ClienteRepository;
import com.api_pedidos.repositories.EnderecoRepository;
import com.api_pedidos.services.exceptions.DataIntegrityException;
import com.api_pedidos.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public Cliente find(Integer id) throws ObjectNotFoundException {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + " Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);	
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos lançados.");
		}
		
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO obj) {
		Cliente cli = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCpfcnpj(), TipoCliente.toEnum(obj.getTipo()));
		//Cidade cidade = new Cidade(obj.getCidadeId(), null, null);
		Optional<Cidade> cidade = cidadeRepository.findById(obj.getCidadeId());
		Endereco end = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(), obj.getBairro(), obj.getCep(), cli, cidade.orElse(new Cidade()));
		cli.getEndereco().add(end);
		cli.getTelefones().add(obj.getTelefone1());
		
		if(obj.getTelefone2() != null) {
			cli.getTelefones().add(obj.getTelefone2());	
		}
		
		if(obj.getTelefone3() != null) {
			cli.getTelefones().add(obj.getTelefone3());	
		}
		
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEndereco());
		return obj;
	}
	
	
}
