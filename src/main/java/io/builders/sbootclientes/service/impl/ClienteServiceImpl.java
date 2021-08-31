package io.builders.sbootclientes.service.impl;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.builders.sbootclientes.components.Messages;
import io.builders.sbootclientes.data.vo.ClienteVO;
import io.builders.sbootclientes.entity.Cliente;
import io.builders.sbootclientes.exception.ResourceNotFoundException;
import io.builders.sbootclientes.repository.ClienteRepository;
import io.builders.sbootclientes.service.IService;
import io.builders.sbootclientes.util.ClienteSpecificationsBuilder;
import io.builders.sbootclientes.util.MapperUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements IService<ClienteVO> {

	private final ClienteRepository clienteRepository;
	private final Validator validator;
	private final Messages msg;

	@Override
	public Page<ClienteVO> findAll(String search, Pageable pageable) {
		ClienteSpecificationsBuilder builder = new ClienteSpecificationsBuilder();
		Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+)", Pattern.UNICODE_CHARACTER_CLASS);
		Matcher matcher = pattern.matcher(search + ",");
		while (matcher.find()) {
			builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
		}
		return clienteRepository.findAll(builder.build(), pageable).map(ClienteVO::create);
	}

	@Override
	public Optional<ClienteVO> findById(Long id) {
		return clienteRepository.findById(id).map(ClienteVO::create);
	}

	@Override
	public ClienteVO create(ClienteVO clienteVO) {
		return ClienteVO.create(clienteRepository.save(Cliente.create(clienteVO)));
	}

	@Override
	public ClienteVO update(ClienteVO clienteVO) {
		final Optional<Cliente> optCliente = clienteRepository.findById(clienteVO.getId());
		if (!optCliente.isPresent()) {
			throw new ResourceNotFoundException(msg.resourceNotFound());
		}
		return ClienteVO.create(clienteRepository.save(Cliente.create(clienteVO)));
	}

	@Override
	public ClienteVO updatePatch(Long id, Map<Object, Object> fields) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(msg.resourceNotFound()));

		MapperUtil.updateValue(cliente, fields);

		Set<ConstraintViolation<ClienteVO>> violations = validator.validate(ClienteVO.create(cliente));
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
		return ClienteVO.create(clienteRepository.save(cliente));
	}

	@Override
	public void deleteById(Long id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(msg.resourceNotFound()));
		clienteRepository.delete(cliente);
	}

}
