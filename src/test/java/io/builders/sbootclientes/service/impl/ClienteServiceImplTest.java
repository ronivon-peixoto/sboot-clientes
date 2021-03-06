package io.builders.sbootclientes.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import io.builders.sbootclientes.data.vo.ClienteVO;
import io.builders.sbootclientes.entity.Cliente;
import io.builders.sbootclientes.exception.ResourceNotFoundException;
import io.builders.sbootclientes.repository.ClienteRepository;

@SpringBootTest
@ActiveProfiles("test")
class ClienteServiceImplTest {

	private static final Long COD_CLIENTE = 1L;
	private static final String NOME_CLIENTE = "Theo e Ruan Alimentos ME";
	private static final String TIPO_DOC_CLIENTE = "CNPJ";
	private static final String DOC_CLIENTE = "13197486000141";

	@MockBean
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteServiceImpl clienteService;

	@Test
	@SuppressWarnings("unchecked")
	void testFindAll() {

		Cliente cliente = new Cliente(COD_CLIENTE, NOME_CLIENTE, TIPO_DOC_CLIENTE, DOC_CLIENTE, null, null);

		doReturn(new PageImpl<Cliente>(List.of(cliente)))
			.when(clienteRepository)
			.findAll(any(Specification.class), any(Pageable.class));

		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "id"));

		Page<ClienteVO> pageResult = clienteService.findAll("search=nome:theo,tipoDocumento:cnpj", pageable);

		assertNotNull(pageResult);
		assertNotNull(pageResult.getContent());
		assertEquals(1, pageResult.getTotalElements());

		ClienteVO cliente1 = pageResult.getContent().get(0);

		assertEquals(COD_CLIENTE, cliente1.getId());
		assertEquals(NOME_CLIENTE, cliente1.getNome());
		assertEquals(TIPO_DOC_CLIENTE, cliente1.getTipoDocumento());
		assertEquals(DOC_CLIENTE, cliente1.getDocumento());
	}

	@Test
	void testFindById() {
		Cliente cliente = new Cliente(COD_CLIENTE, NOME_CLIENTE, TIPO_DOC_CLIENTE, DOC_CLIENTE, null, null);

		doReturn(Optional.of(cliente)).when(clienteRepository).findById(COD_CLIENTE);

		Optional<ClienteVO> optClienteVO = clienteService.findById(COD_CLIENTE);

		assertTrue(optClienteVO.isPresent());

		ClienteVO clienteVO = optClienteVO.get();

		assertEquals(ClienteVO.create(cliente), clienteVO);
		assertEquals(COD_CLIENTE, clienteVO.getId());
		assertEquals(NOME_CLIENTE, clienteVO.getNome());
		assertEquals(TIPO_DOC_CLIENTE, clienteVO.getTipoDocumento());
		assertEquals(DOC_CLIENTE, clienteVO.getDocumento());
	}

	@Test
	void testCreate() {
		ClienteVO clienteVO = ClienteVO.builder()
				.nome(NOME_CLIENTE)
				.tipoDocumento(TIPO_DOC_CLIENTE)
				.documento(DOC_CLIENTE)
				.build();

		doReturn(Cliente.create(clienteVO)).when(clienteRepository).save(any(Cliente.class));

		ClienteVO cliente = clienteService.create(clienteVO);

		assertNotNull(cliente);
		assertEquals(NOME_CLIENTE, cliente.getNome());
		assertEquals(TIPO_DOC_CLIENTE, cliente.getTipoDocumento());
		assertEquals(DOC_CLIENTE, cliente.getDocumento());
	}

	@Test
	void testUpdate_DadosInvalidos() throws Exception {
		ClienteVO clienteVO = ClienteVO.builder()
				.id(COD_CLIENTE)
				.nome(NOME_CLIENTE)
				.tipoDocumento(TIPO_DOC_CLIENTE)
				.documento(DOC_CLIENTE)
				.build();

		doReturn(Optional.empty()).when(clienteRepository).findById(COD_CLIENTE);

		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			clienteService.update(clienteVO);
		});

		String expectedMessage = "Nenhum registro encontrado.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() throws Exception {
		ClienteVO clienteVO = ClienteVO.builder()
				.id(COD_CLIENTE)
				.nome(NOME_CLIENTE)
				.tipoDocumento(TIPO_DOC_CLIENTE)
				.documento(DOC_CLIENTE)
				.build();

		doReturn(Optional.of(Cliente.create(clienteVO))).when(clienteRepository).findById(COD_CLIENTE);
		doReturn(Cliente.create(clienteVO)).when(clienteRepository).save(any(Cliente.class));

		ClienteVO cliente = clienteService.update(clienteVO);

		assertNotNull(cliente);

		assertEquals(COD_CLIENTE, cliente.getId());
		assertEquals(NOME_CLIENTE, cliente.getNome());
		assertEquals(TIPO_DOC_CLIENTE, cliente.getTipoDocumento());
		assertEquals(DOC_CLIENTE, cliente.getDocumento());
	}

	@Test
	void testUpdatePatch_IdInvalido() throws Exception {
		Map<Object, Object> fields = new HashMap<>();

		doReturn(Optional.empty()).when(clienteRepository).findById(COD_CLIENTE);

		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			clienteService.updatePatch(COD_CLIENTE, fields);
		});

		String expectedMessage = "Nenhum registro encontrado.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdatePatch_DadosInvalidos() throws Exception {
		ClienteVO clienteVO = ClienteVO.builder()
				.id(COD_CLIENTE)
				.nome(NOME_CLIENTE)
				.tipoDocumento(TIPO_DOC_CLIENTE)
				.documento(DOC_CLIENTE)
				.build();

		doReturn(Optional.of(Cliente.create(clienteVO))).when(clienteRepository).findById(COD_CLIENTE);

		Map<Object, Object> fields = new HashMap<>();
		fields.put("nome", "");
		fields.put("email", "teste");

		ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
			clienteService.updatePatch(COD_CLIENTE, fields);
		});

		assertEquals(3, ex.getConstraintViolations().size());
		assertEquals(1, ex.getConstraintViolations().stream().filter(e -> e.getPropertyPath().toString().equals("nome")).count());
		assertEquals(2, ex.getConstraintViolations().stream().filter(e -> e.getPropertyPath().toString().equals("email")).count());
	}

	@Test
	void testUpdatePatch() throws Exception {
		ClienteVO clienteVO = ClienteVO.builder()
				.id(COD_CLIENTE)
				.nome(NOME_CLIENTE)
				.tipoDocumento(TIPO_DOC_CLIENTE)
				.documento(DOC_CLIENTE)
				.build();

		doReturn(Optional.of(Cliente.create(clienteVO))).when(clienteRepository).findById(COD_CLIENTE);
		doReturn(Cliente.create(clienteVO)).when(clienteRepository).save(any(Cliente.class));

		Map<Object, Object> fields = new HashMap<>();
		fields.put("nome", NOME_CLIENTE);

		ClienteVO cliente = clienteService.updatePatch(COD_CLIENTE, fields);

		assertNotNull(cliente);

		assertEquals(COD_CLIENTE, cliente.getId());
		assertEquals(NOME_CLIENTE, cliente.getNome());
		assertEquals(TIPO_DOC_CLIENTE, cliente.getTipoDocumento());
		assertEquals(DOC_CLIENTE, cliente.getDocumento());
	}

	@Test
	void testDelete_CodigoInvalido() throws Exception {

		doReturn(Optional.empty()).when(clienteRepository).findById(0L);

		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			clienteService.deleteById(0L);
		});

		String expectedMessage = "Nenhum registro encontrado.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() throws Exception {
		Cliente cliente = new Cliente(COD_CLIENTE, NOME_CLIENTE, TIPO_DOC_CLIENTE, DOC_CLIENTE, null, null);

		doReturn(Optional.of(cliente)).when(clienteRepository).findById(COD_CLIENTE);

		clienteService.deleteById(COD_CLIENTE);

		verify(clienteRepository, times(1)).delete(cliente);
	}

}
