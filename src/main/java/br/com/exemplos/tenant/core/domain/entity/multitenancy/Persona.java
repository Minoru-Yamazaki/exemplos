package br.com.exemplos.tenant.core.domain.entity.multitenancy;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa uma pessoa com um papel definido utilizando uma aplicação. <br>
 * Exemplo: <br>
 * Gerente de vendas navegando um ERP de um.
 */
@Getter
@Setter
public class Persona {

	private String papel;

	private String aplicacao;

	private String tenantId;

}
