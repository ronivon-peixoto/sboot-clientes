package io.builders.sbootclientes.util;

import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;

@Getter
public class SortCriteria {

	String field;
	Direction direction;

	public SortCriteria(String criteria) {
		String[] sort = criteria.split(",");
		this.field = sort[0];
		this.direction = Direction.fromString(sort[1].toUpperCase());
	}

}
