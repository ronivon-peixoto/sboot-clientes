package io.builders.sbootclientes.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Generic resource service interface.
 */
public interface IService<T> {

	/**
	 * Search the data based on filtering, paging and sorting criteria.
	 * 
	 * @param search   - Filtering criteria.
	 * @param pageable - Paging and sorting data.
	 * @return Returns paged data.
	 */
	Page<T> findAll(String search, Pageable pageable);

	/**
	 * Try to find the entity by ID
	 * 
	 * @param id - Primary key.
	 * @return If found, returns an Optional object containing entity data,
	 *         otherwise returns an empty Optional.
	 */
	Optional<T> findById(Long id);

	/**
	 * Insertion of entity data.
	 * 
	 * @param t - Entity
	 * @return Returns the new entity.
	 */
	T create(T t);

	/**
	 * Complete entity update.
	 * 
	 * @param t - Entity
	 * @return Returns the updated entity.
	 */
	T update(T t);

	/**
	 * Partial update of the entity.
	 * 
	 * @param id     - Primary key.
	 * @param fields - Fields to be changed;
	 * @return Returns the updated entity.
	 */
	T updatePatch(Long id, Map<Object, Object> fields);

	/**
	 * Remove entity from ID
	 * 
	 * @param id - Primary key.
	 */
	void deleteById(Long id);

}
