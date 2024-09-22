package br.com.exemplos.tenant.core.domain.context;

import br.com.exemplos.tenant.core.domain.entity.multitenancy.Persona;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TenantStore {

    TenantStore() {
    }

    @Setter
    private Persona perfilEmUso;

    @Setter
    private String identificadorUsuario;

    public boolean hasPerfilEmUso() {
        return perfilEmUso != null;
    }

}
