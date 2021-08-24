package io.builders.sbootclientes.data.vo;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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

	@JsonProperty("id")
	private Long id;

	@NotBlank(message = "O nome deve ser informado.")
	@Size(min = 3, max = 255, message = "O nome deve conter entre 3 e 255 caracteres.")
	@JsonProperty("nome")
	private String nome;

	@NotNull(message = "O tipo de documento deve ser informado.")
	@Pattern(regexp = "CPF|CNPJ", message = "Informe corretamente o tipo de documento.")
	@JsonProperty("tipoDocumento")
	private String tipoDocumento;

	@NotBlank(message = "O documento deve ser informado.")
	@Pattern(message = "O documento deve conter apenas números.", regexp = "^[0-9]*$")
	@Size(min = 11, max = 14, message = "O documento deve conter entre 11 e 14 caracteres.")
	@JsonProperty("documento")
	private String documento;

	@Email(message = "Informe um e-mail válido.")
	@Size(min = 7, max = 255, message = "O e-mail deve conter entre 7 e 255 caracteres.")
	@JsonProperty("email")
	private String email;

	@Size(min = 0, max = 255, message = "O endereço não pode ultrapassar 255 caracteres.")
	@JsonProperty("endereco")
	private String endereco;

	public static ClienteVO create(Cliente cliente) {
		return new ModelMapper().map(cliente, ClienteVO.class);
	}
}
