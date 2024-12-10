package br.com.exemplos.core.context;

import br.com.exemplos.core.domain.entity.multitenancy.Persona;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TenantContext {

    private static final ThreadLocal<TenantStore> current = new ThreadLocal<>();

    private static void init() {
        if (current.get() == null) {
            current.set(new TenantStore());
        }
    }

    public static void clear() {
        current.remove();
    }

    public static void setIdentificadorUsuario(String identificadorUsuario) {
        init();
        current.get().setIdentificadorUsuario(identificadorUsuario);
    }

    public static boolean temAcessoDefinido() {
        return Optional.of(current).map(ThreadLocal::get).map(TenantStore::hasPerfilEmUso).orElse(false);
    }

    public static void setPerfilEmUso(Persona perfilEmUso) {
        init();
        current.get().setPerfilEmUso(perfilEmUso);
    }

    public static Persona getPerfilEmUso() {
        return Optional.of(current).map(ThreadLocal::get).map(TenantStore::getPerfilEmUso).orElse(null);
    }

    public static String getTenantId() {
        return Optional.ofNullable(getPerfilEmUso()).map(Persona::getTenantId).orElse("");
    }

}
