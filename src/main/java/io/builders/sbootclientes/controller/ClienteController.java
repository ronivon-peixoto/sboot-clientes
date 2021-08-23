package io.builders.sbootclientes.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.builders.sbootclientes.data.vo.ClienteVO;
import io.builders.sbootclientes.service.ClienteService;
import io.builders.sbootclientes.util.SortCriteria;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {

	private final ClienteService clienteService;
	private final PagedResourcesAssembler<ClienteVO> assembler;

	@Autowired
	public ClienteController(ClienteService clienteService, PagedResourcesAssembler<ClienteVO> assembler) {
		this.clienteService = clienteService;
		this.assembler = assembler;
	}

	@GetMapping(value = "/{id}", produces = { "application/json" })
	public ClienteVO findById(@PathVariable("id") Long id) {
		ClienteVO clienteVO = clienteService.findById(id);
		clienteVO.add(linkTo(methodOn(ClienteController.class).findById(id)).withSelfRel());
		return clienteVO;
	}

	@GetMapping(produces = { "application/json" })
	public ResponseEntity<PagedModel<EntityModel<ClienteVO>>> search(
			@RequestParam(value = "search", defaultValue = "", required = false) String search,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "sort", defaultValue = "id,asc") String sort) {

		SortCriteria sortCriteria = new SortCriteria(sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortCriteria.getDirection(), sortCriteria.getField()));

		Page<ClienteVO> clientes = clienteService.findAll(search, pageable);
		clientes.stream().forEach(p -> p.add(linkTo(methodOn(ClienteController.class).findById(p.getId())).withSelfRel()));

		PagedModel<EntityModel<ClienteVO>> pagedModel = assembler.toModel(clientes);

		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}

	@PostMapping(produces = { "application/json" }, consumes = { "application/json" })
	public ClienteVO create(@Valid @RequestBody ClienteVO cliente) {
		ClienteVO clienteVO = clienteService.create(cliente);
		clienteVO.add(linkTo(methodOn(ClienteController.class).findById(clienteVO.getId())).withSelfRel());
		return clienteVO;
	}

	@PutMapping(produces = { "application/json" }, consumes = { "application/json" })
	public ClienteVO update(@Valid @RequestBody ClienteVO cliente) {
		ClienteVO clienteVO = clienteService.update(cliente);
		clienteVO.add(linkTo(methodOn(ClienteController.class).findById(clienteVO.getId())).withSelfRel());
		return clienteVO;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		clienteService.delete(id);
		return new ResponseEntity<>("Registro removido com sucesso!", HttpStatus.OK);
	}

}
