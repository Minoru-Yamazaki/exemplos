package br.com.exemplos.core.context;

import br.com.exemplos.core.domain.entity.multitenancy.Persona;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantStore {

	TenantStore() {
	}

	private Persona perfilEmUso;

	private String identificadorUsuario;

	public boolean hasPerfilEmUso() {
		return perfilEmUso != null;
	}

}
