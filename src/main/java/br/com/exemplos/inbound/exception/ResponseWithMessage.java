package br.com.exemplos.inbound.exception;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWithMessage {

	private String codigo;

	private String mensagem;

}
