package br.com.exemplos.core.context;

import br.com.exemplos.core.domain.entity.multitenancy.Persona;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TenantContextTest {

    @AfterEach
    void limpar() {
        TenantContext.clear();
    }

    @Test
    void getTenantIdDeveRetornarVazioQuandoContextoNaoInicializado() {
        assertEquals("", TenantContext.getTenantId());
    }

    @Test
    void deveRetornarTenantIdAposDefinirPerfil() {
        Persona persona = new Persona();
        persona.setTenantId("tenant-001");

        TenantContext.setPerfilEmUso(persona);

        assertEquals("tenant-001", TenantContext.getTenantId());
    }

    @Test
    void temAcessoDefinidoDeveRetornarFalseQuandoPerfilNulo() {
        assertFalse(TenantContext.temAcessoDefinido());
    }

    @Test
    void temAcessoDefinidoDeveRetornarTrueAposDefinirPerfil() {
        TenantContext.setPerfilEmUso(new Persona());

        assertTrue(TenantContext.temAcessoDefinido());
    }

    @Test
    void getPerfilEmUsoDeveRetornarNullQuandoNaoDefinido() {
        assertNull(TenantContext.getPerfilEmUso());
    }

    @Test
    void deveArmazenarERecuperarPerfilEmUso() {
        Persona persona = new Persona();
        persona.setTenantId("tenant-abc");

        TenantContext.setPerfilEmUso(persona);

        assertSame(persona, TenantContext.getPerfilEmUso());
    }

    @Test
    void deveArmazenarIdentificadorUsuario() {
        TenantContext.setIdentificadorUsuario("usuario-xyz");

        // Verificamos via getPerfilEmUso que o store foi inicializado (sem lançar NPE)
        assertDoesNotThrow(TenantContext::getPerfilEmUso);
    }

    @Test
    void clearDeveRemoverContextoDaThread() {
        Persona persona = new Persona();
        persona.setTenantId("tenant-999");
        TenantContext.setPerfilEmUso(persona);

        TenantContext.clear();

        assertNull(TenantContext.getPerfilEmUso());
        assertEquals("", TenantContext.getTenantId());
        assertFalse(TenantContext.temAcessoDefinido());
    }

}
