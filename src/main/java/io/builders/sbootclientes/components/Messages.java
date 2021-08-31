package io.builders.sbootclientes.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class Messages {

	@Autowired
	private MessageSource messageSource;

	private MessageSourceAccessor accessor;

	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(messageSource);
	}

	public String get(String code) {
		return accessor.getMessage(code);
	}

	public String resourceNotFound() {
		return get("resource.not.found");
	}

	public String resourceDeletedSuccessfully() {
		return get("resource.deleted.successfully");
	}

}