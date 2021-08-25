package io.builders.sbootclientes.exception;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ValidationExceptionResponse implements Serializable {

	private static final long serialVersionUID = -1132959971107776780L;

	private Date timestamp;
	private Map<String, String> fieldErrors;
	private String details;

}
