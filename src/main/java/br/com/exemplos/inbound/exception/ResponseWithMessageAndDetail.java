package br.com.exemplos.inbound.exception;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ResponseWithMessageAndDetail {

	private String codigo;

	private String mensagem;

	private String detalhe;

}