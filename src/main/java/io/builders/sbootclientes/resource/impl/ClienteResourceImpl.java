package io.builders.sbootclientes.resource.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.builders.sbootclientes.components.Messages;
import io.builders.sbootclientes.data.vo.ClienteVO;
import io.builders.sbootclientes.resource.Resource;
import io.builders.sbootclientes.service.IService;
import io.builders.sbootclientes.util.SortCriteria;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/v1/clientes")
public class ClienteResourceImpl implements Resource<ClienteVO> {

	private final IService<ClienteVO> clienteService;
	private final PagedResourcesAssembler<ClienteVO> assembler;
	private final Messages msg;

	@Override
	public ResponseEntity<PagedModel<EntityModel<ClienteVO>>> findAll(String search, int page, int size, String sort) {
		log.info("ClienteResourceImpl - findAll");
		SortCriteria sortCriteria = new SortCriteria(sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortCriteria.getDirection(), sortCriteria.getField()));

		Page<ClienteVO> clientes = clienteService.findAll(search, pageable);
		clientes.stream().forEach(p -> p.add(linkTo(methodOn(ClienteResourceImpl.class).findById(p.getId())).withSelfRel()));

		return ResponseEntity.ok(assembler.toModel(clientes));
	}

	@Override
	public ResponseEntity<ClienteVO> findById(Long id) {
		log.info("ClienteResourceImpl - findById");
		return clienteService.findById(id).map(c -> {
			c.add(linkTo(methodOn(ClienteResourceImpl.class).findById(id)).withSelfRel());
			return ResponseEntity.ok(c);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Override
	public ResponseEntity<ClienteVO> create(ClienteVO cliente) {
		log.info("ClienteResourceImpl - create");
		ClienteVO clienteVO = clienteService.create(cliente);
		clienteVO.add(linkTo(methodOn(ClienteResourceImpl.class).findById(clienteVO.getId())).withSelfRel());
		return ResponseEntity.ok(clienteVO);
	}

	@Override
	public ResponseEntity<ClienteVO> update(ClienteVO cliente) {
		log.info("ClienteResourceImpl - update");
		ClienteVO clienteVO = clienteService.update(cliente);
		clienteVO.add(linkTo(methodOn(ClienteResourceImpl.class).findById(clienteVO.getId())).withSelfRel());
		return ResponseEntity.ok(clienteVO);
	}

	@Override
	public ResponseEntity<ClienteVO> updatePatch(Long id, Map<Object, Object> fields) {
		log.info("ClienteResourceImpl - updatePatch");
		ClienteVO clienteVO = clienteService.updatePatch(id, fields);
		clienteVO.add(linkTo(methodOn(ClienteResourceImpl.class).findById(clienteVO.getId())).withSelfRel());
		return ResponseEntity.ok(clienteVO);
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {
		log.info("ClienteResourceImpl - deleteById");
		clienteService.deleteById(id);
		return ResponseEntity.ok(msg.resourceDeletedSuccessfully());
	}

}
