package io.builders.sbootclientes.util;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.builders.sbootclientes.entity.Cliente;

public final class MapperUtil {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private MapperUtil() {
	}

	public static Cliente updateValue(Cliente cliente, Map<Object, Object> fields) {
		try {
			return OBJECT_MAPPER.updateValue(cliente, fields);
		} catch (JsonMappingException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

}
