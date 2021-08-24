package io.builders.sbootclientes.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchCriteria {

	private String key;
	private String operation;
	private Object value;

	public boolean isOrPredicate() {
		return false;
	}

}
