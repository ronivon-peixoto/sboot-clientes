package io.builders.sbootclientes.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import io.builders.sbootclientes.entity.Cliente;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClienteSpecification implements Specification<Cliente> {

	private static final long serialVersionUID = -3076744933682578090L;

	private transient SearchCriteria criteria;

	@Override
	public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (criteria.getOperation().equalsIgnoreCase(">")) {
			return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());

		} else if (criteria.getOperation().equalsIgnoreCase("<")) {
			return builder.lessThan(root.<String>get(criteria.getKey()), criteria.getValue().toString());

		} else if (criteria.getOperation().equalsIgnoreCase(":")) {
			if (root.get(criteria.getKey()).getJavaType() == String.class) {
				return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
			} else {
				return builder.equal(root.get(criteria.getKey()), criteria.getValue());
			}
		}
		return null;
	}
}
