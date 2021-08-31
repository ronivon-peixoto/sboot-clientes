package io.builders.sbootclientes.data.vo;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.builders.sbootclientes.data.validation.OnUpdate;
import io.builders.sbootclientes.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({ "id", "nome", "tipoDocumento", "documento", "email", "endereco" })
public class ClienteVO extends RepresentationModel<ClienteVO> implements Serializable {

	private static final long serialVersionUID = -8183798242747049538L;

	@NotNull(message = "{cliente.id.notnull}", groups = OnUpdate.class)
	@JsonProperty("id")
	private Long id;

	@NotNull(message = "{cliente.nome.notnull}")
	@Size(min = 3, max = 255, message = "{cliente.nome.size}")
	@JsonProperty("nome")
	private String nome;

	@NotNull(message = "{cliente.tipodocumento.notnull}")
	@Pattern(regexp = "CPF|CNPJ", message = "{cliente.tipodocumento.pattern}")
	@JsonProperty("tipoDocumento")
	private String tipoDocumento;

	@NotNull(message = "{cliente.documento.notnull}")
	@Pattern(regexp = "^[0-9]*$", message = "{cliente.documento.pattern}")
	@Size(min = 11, max = 14, message = "{cliente.documento.size}")
	@JsonProperty("documento")
	private String documento;

	@Email(message = "{cliente.email.valid}")
	@Size(min = 7, max = 255, message = "{cliente.email.size}")
	@JsonProperty("email")
	private String email;

	@Size(min = 0, max = 255, message = "{cliente.endereco.size}")
	@JsonProperty("endereco")
	private String endereco;

	public static ClienteVO create(Cliente cliente) {
		return new ModelMapper().map(cliente, ClienteVO.class);
	}
}
