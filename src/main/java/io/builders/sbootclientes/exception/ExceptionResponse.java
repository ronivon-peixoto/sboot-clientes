package io.builders.sbootclientes.exception;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 6986899997042135472L;

	private Date timestamp;
	private String message;
	private String details;

}
