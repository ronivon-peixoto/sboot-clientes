package io.builders.sbootclientes.entity;

public enum TipoDocumento {

	CPF, CNPJ;

	public static TipoDocumento from(Object obj) {
		try {
			return TipoDocumento.valueOf(((String) obj).toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}

}
