package io.builders.sbootclientes.resource.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hamcrest.core.IsNull;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.builders.sbootclientes.data.vo.ClienteVO;
import io.builders.sbootclientes.exception.ResourceNotFoundException;
import io.builders.sbootclientes.service.impl.ClienteServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClienteResourceImplTest {

	private static final Long COD_CLIENTE = 1L;
	private static final String NOME_CLIENTE = "Theo e Ruan Alimentos ME";
	private static final String TIPO_DOC_CLIENTE = "CNPJ";
	private static final String DOC_CLIENTE = "13197486000141";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ClienteServiceImpl clienteService;

	@Test
	void testFindById_NotFound() throws Exception {
		given(clienteService.findById(COD_CLIENTE)).willReturn(Optional.empty());

		mockMvc.perform(get("/v1/clientes/{id}", COD_CLIENTE)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testFindById() throws Exception {
    	ClienteVO clienteVO = ClienteVO.builder()
    			.id(COD_CLIENTE)
    			.nome(NOME_CLIENTE)
    			.tipoDocumento(TIPO_DOC_CLIENTE)
    			.documento(DOC_CLIENTE)
    			.build();

		given(clienteService.findById(COD_CLIENTE)).willReturn(Optional.of(clienteVO));

		mockMvc.perform(get("/v1/clientes/{id}", COD_CLIENTE)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(COD_CLIENTE.intValue())))
				.andExpect(jsonPath("$.nome", is(NOME_CLIENTE)))
				.andExpect(jsonPath("$.tipoDocumento", is(TIPO_DOC_CLIENTE)))
				.andExpect(jsonPath("$.documento", is(DOC_CLIENTE)))
				.andExpect(jsonPath("$.email").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.endereco").value(IsNull.nullValue()));
	}

	@Test
	void testFindAll() throws Exception {
		ClienteVO clienteVO = ClienteVO.builder()
    			.id(COD_CLIENTE)
    			.nome(NOME_CLIENTE)
    			.tipoDocumento(TIPO_DOC_CLIENTE)
    			.documento(DOC_CLIENTE)
    			.build();

		String query = "nome:ME,id<5";
		String url = "/v1/clientes?search=".concat(query);

		given(clienteService.findAll(eq(query), any()))
			.willReturn(new PageImpl<ClienteVO>(List.of(clienteVO)));

		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded").exists())
				.andExpect(jsonPath("$._embedded.clienteVOList").exists())
				.andExpect(jsonPath("$._embedded.clienteVOList", hasSize(1)))
				.andExpect(jsonPath("$._embedded.clienteVOList[0].id", is(COD_CLIENTE.intValue())))
				.andExpect(jsonPath("$._embedded.clienteVOList[0].nome", is(NOME_CLIENTE)))
				.andExpect(jsonPath("$._embedded.clienteVOList[0].tipoDocumento", is(TIPO_DOC_CLIENTE)))
				.andExpect(jsonPath("$._embedded.clienteVOList[0].documento", is(DOC_CLIENTE)))
				.andExpect(jsonPath("$._embedded.clienteVOList[0]._links").exists())
				.andExpect(jsonPath("$._links").exists())
				.andExpect(jsonPath("$.page").exists());
	}

	@Test
	void testCreate_DadosInvalidos() throws Exception {
		ClienteVO clienteVO = ClienteVO.builder()
    			.email("teste@teste.com")
    			.build();

		mockMvc.perform(post("/v1/clientes")
				.content(objectMapper.writeValueAsString(clienteVO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.fieldErrors").exists())
				.andExpect(jsonPath("$.fieldErrors.nome", is("O nome deve ser informado.")))
				.andExpect(jsonPath("$.fieldErrors.tipoDocumento", is("O tipo de documento deve ser informado.")))
				.andExpect(jsonPath("$.fieldErrors.documento", is("O documento deve ser informado.")))
				.andExpect(jsonPath("$.details").exists())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void testCreate() throws Exception {
		ClienteVO clienteVO = ClienteVO.builder()
    			.nome(NOME_CLIENTE)
    			.tipoDocumento(TIPO_DOC_CLIENTE)
    			.documento(DOC_CLIENTE)
    			.build();

		given(clienteService.create(clienteVO)).willReturn(clienteVO);

		mockMvc.perform(post("/v1/clientes")
				.content(objectMapper.writeValueAsString(clienteVO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is(NOME_CLIENTE)))
				.andExpect(jsonPath("$.tipoDocumento", is(TIPO_DOC_CLIENTE)))
				.andExpect(jsonPath("$.documento", is(DOC_CLIENTE)))
				.andExpect(jsonPath("$.email").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.endereco").value(IsNull.nullValue()));
	}

	@Test
	void testUpdate_DadosInvalidos() throws Exception {
		ClienteVO clienteVO = ClienteVO.builder()
				.id(COD_CLIENTE)
				.nome(NOME_CLIENTE)
    			.email("teste@teste.com")
    			.build();
		
		mockMvc.perform(put("/v1/clientes")
				.content(objectMapper.writeValueAsString(clienteVO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.fieldErrors").exists())
				.andExpect(jsonPath("$.fieldErrors.tipoDocumento", is("O tipo de documento deve ser informado.")))
				.andExpect(jsonPath("$.fieldErrors.documento", is("O documento deve ser informado.")))
				.andExpect(jsonPath("$.details").exists())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void testUpdate() throws Exception {
		ClienteVO clienteVO = ClienteVO.builder()
				.id(COD_CLIENTE)
    			.nome(NOME_CLIENTE)
    			.tipoDocumento(TIPO_DOC_CLIENTE)
    			.documento(DOC_CLIENTE)
    			.build();

		given(clienteService.update(clienteVO)).willReturn(clienteVO);

		mockMvc.perform(put("/v1/clientes")
				.content(objectMapper.writeValueAsString(clienteVO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(COD_CLIENTE.intValue())))
				.andExpect(jsonPath("$.nome", is(NOME_CLIENTE)))
				.andExpect(jsonPath("$.tipoDocumento", is(TIPO_DOC_CLIENTE)))
				.andExpect(jsonPath("$.documento", is(DOC_CLIENTE)))
				.andExpect(jsonPath("$.email").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.endereco").value(IsNull.nullValue()));
	}

	@Test
	void testUpdatePatch_DadosInvalidos() throws Exception {
		Map<Object, Object> fields = new HashMap<>();
		fields.put("nome", "");

		Set<ConstraintViolation<ClienteVO>> violations = new HashSet<>();
		violations.add(ConstraintViolationImpl.forBeanValidation(null, null, null, "O nome deve ser informado.", null,
				null, null, null, PathImpl.createPathFromString("nome"), null, null));

		doThrow(new ConstraintViolationException(violations)).when(clienteService).updatePatch(any(), any());

		mockMvc.perform(patch("/v1/clientes/{id}", COD_CLIENTE)
				.content(objectMapper.writeValueAsString(fields))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.fieldErrors").exists())
				.andExpect(jsonPath("$.fieldErrors.nome", is("O nome deve ser informado.")))
				.andExpect(jsonPath("$.details").exists())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void testUpdatePatch() throws Exception {
		Map<Object, Object> fields = new HashMap<>();

		ClienteVO clienteVO = ClienteVO.builder()
				.id(COD_CLIENTE)
    			.nome(NOME_CLIENTE)
    			.tipoDocumento(TIPO_DOC_CLIENTE)
    			.documento(DOC_CLIENTE)
    			.build();

		given(clienteService.updatePatch(any(), any())).willReturn(clienteVO);

		mockMvc.perform(patch("/v1/clientes/{id}", COD_CLIENTE)
				.content(objectMapper.writeValueAsString(fields))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(COD_CLIENTE.intValue())))
				.andExpect(jsonPath("$.nome", is(NOME_CLIENTE)))
				.andExpect(jsonPath("$.tipoDocumento", is(TIPO_DOC_CLIENTE)))
				.andExpect(jsonPath("$.documento", is(DOC_CLIENTE)))
				.andExpect(jsonPath("$.email").value(IsNull.nullValue()))
				.andExpect(jsonPath("$.endereco").value(IsNull.nullValue()));
	}

	@Test
	void testDelete_CodigoInvalido() throws Exception {
		String message = "Nenhum registro encontrado.";
		doThrow(new ResourceNotFoundException(message)).when(clienteService).deleteById(0L);

		mockMvc.perform(delete("/v1/clientes/{id}", 0).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is(message)));
	}

	@Test
	void testDelete() throws Exception {
		String message = "Registro excluído com sucesso.";

		mockMvc.perform(delete("/v1/clientes/{id}", COD_CLIENTE).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is(message)));
	}

}
