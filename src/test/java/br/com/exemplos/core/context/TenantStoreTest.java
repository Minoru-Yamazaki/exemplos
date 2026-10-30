package br.com.exemplos.core.context;

import br.com.exemplos.core.domain.entity.multitenancy.Persona;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TenantStoreTest {

	// TenantStore tem construtor package-private; instanciamos via TenantContext
	// que faz new TenantStore() internamente — aqui usamos a classe diretamente
	// pois o pacote de teste é o mesmo.

	@Test
	void hasPerfilEmUsoDeveRetornarFalseQuandoPerfilNulo() {
		TenantStore store = new TenantStore();

		assertFalse(store.hasPerfilEmUso());
	}

	@Test
	void hasPerfilEmUsoDeveRetornarTrueQuandoPerfilDefinido() {
		TenantStore store = new TenantStore();
		store.setPerfilEmUso(new Persona());

		assertTrue(store.hasPerfilEmUso());
	}

	@Test
	void deveArmazenarERecuperarIdentificadorUsuario() {
		TenantStore store = new TenantStore();
		store.setIdentificadorUsuario("user-123");

		assertEquals("user-123", store.getIdentificadorUsuario());
	}

	@Test
	void deveArmazenarERecuperarPerfil() {
		TenantStore store = new TenantStore();
		Persona persona = new Persona();
		persona.setTenantId("tenant-abc");

		store.setPerfilEmUso(persona);

		assertSame(persona, store.getPerfilEmUso());
		assertEquals("tenant-abc", store.getPerfilEmUso().getTenantId());
	}

}
