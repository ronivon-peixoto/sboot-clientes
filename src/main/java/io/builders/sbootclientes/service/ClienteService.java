package io.builders.sbootclientes.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.builders.sbootclientes.data.vo.ClienteVO;
import io.builders.sbootclientes.entity.Cliente;
import io.builders.sbootclientes.exception.ResourceNotFoundException;
import io.builders.sbootclientes.repository.ClienteRepository;
import io.builders.sbootclientes.util.ClienteSpecificationsBuilder;

@Service
public class ClienteService {

	private static final String RESOURCE_NOT_FOUND_MESSAGE = "Nenhum registro encontrado com o ID informado!";

	private final ClienteRepository clienteRepository;

	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public ClienteVO create(ClienteVO clienteVO) {
		return ClienteVO.create(clienteRepository.save(Cliente.create(clienteVO)));
	}

	public Page<ClienteVO> findAll(String search, Pageable pageable) {
		ClienteSpecificationsBuilder builder = new ClienteSpecificationsBuilder();
		Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+)", Pattern.UNICODE_CHARACTER_CLASS);
		Matcher matcher = pattern.matcher(search + ",");
		while (matcher.find()) {
			builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
		}

		Specification<Cliente> spec = builder.build();

		return clienteRepository.findAll(spec, pageable).map(ClienteVO::create);
	}

	public ClienteVO findById(Long id) {
		var entity = clienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE));
		return ClienteVO.create(entity);
	}

	public ClienteVO update(ClienteVO clienteVO) {
		final Optional<Cliente> optCliente = clienteRepository.findById(clienteVO.getId());
		if (!optCliente.isPresent()) {
			throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE);
		}
		return ClienteVO.create(clienteRepository.save(Cliente.create(clienteVO)));
	}

	public void delete(Long id) {
		var entity = clienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE));
		clienteRepository.delete(entity);
	}

}
