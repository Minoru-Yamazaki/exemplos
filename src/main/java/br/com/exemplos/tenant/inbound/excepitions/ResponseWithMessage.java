package br.com.exemplos.tenant.inbound.excepitions;

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
