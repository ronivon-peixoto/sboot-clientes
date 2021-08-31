package io.builders.sbootclientes.resource;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.builders.sbootclientes.data.validation.OnUpdate;

/**
 * Generic resource interface.
 */
public interface Resource<T> {

	/**
	 * Search the data based on filtering, paging and sorting criteria.
	 * 
	 * @param search - Filtering criteria.
	 * @param page   - Current page number.
	 * @param size   - Number of elements on the page.
	 * @param sort   - Page ordering criteria.
	 * @return Returns paged data.
	 */
	@GetMapping
	public ResponseEntity<PagedModel<EntityModel<T>>> findAll(
			@RequestParam(value = "search", defaultValue = "", required = false) String search,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "sort", defaultValue = "id,asc") String sort);

	/**
	 * Try to find the entity by ID
	 * 
	 * @param id - Primary key.
	 * @return Returns entity data.
	 */
	@GetMapping("{id}")
	ResponseEntity<T> findById(@PathVariable Long id);

	/**
	 * Insertion of entity data.
	 * 
	 * @param t - Entity
	 * @return Returns the new entity.
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<T> create(@Valid @RequestBody T t);

	/**
	 * Complete entity update.
	 * 
	 * @param t - Entity
	 * @return Returns the updated entity.
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<T> update(@Validated({ OnUpdate.class }) @RequestBody T t);

	/**
	 * Partial update of the entity.
	 * 
	 * @param id     - Primary key.
	 * @param fields - Fields to be changed;
	 * @return Returns the updated entity.
	 */
	@PatchMapping("{id}")
	ResponseEntity<T> updatePatch(@PathVariable Long id, @RequestBody Map<Object, Object> fields);

	/**
	 * Remove entity from ID
	 * 
	 * @param id - Primary key.
	 */
	@DeleteMapping("{id}")
	ResponseEntity<String> deleteById(@PathVariable Long id);

}
