package io.builders.sbootclientes.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import io.builders.sbootclientes.data.vo.ClienteVO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = -4345379995200279690L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false, length = 255)
	private String nome;

	@Column(name = "tipo_documento", nullable = false, length = 4)
	private String tipoDocumento;

	@Column(name = "documento", nullable = false, length = 14)
	private String documento;

	@Column(name = "email", nullable = true, length = 255)
	private String email;

	@Column(name = "endereco", nullable = true, length = 255)
	private String endereco;

	public static Cliente create(ClienteVO clienteVO) {
		return new ModelMapper().map(clienteVO, Cliente.class);
	}
}
