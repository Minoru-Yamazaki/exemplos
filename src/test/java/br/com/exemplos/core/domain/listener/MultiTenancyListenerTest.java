package br.com.exemplos.core.domain.listener;

import br.com.exemplos.core.context.TenantContext;
import br.com.exemplos.core.domain.entity.Disciplina;
import br.com.exemplos.core.domain.entity.multitenancy.MultiTenancyEntity;
import br.com.exemplos.core.domain.entity.multitenancy.Persona;
import br.com.exemplos.core.domain.exception.DomainException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiTenancyListenerTest {

	private MultiTenancyListener listener;

	@BeforeEach
	void setUp() {
		listener = new MultiTenancyListener();
		TenantContext.clear();
	}

	@AfterEach
	void tearDown() {
		TenantContext.clear();
	}

	@Test
	void prePersistDeveDefinirTenantIdDaContextQuandoEntidadeNaoTemTenant() {
		configurarTenant("tenant-001");
		Disciplina disciplina = new Disciplina();

		listener.prePersist(disciplina);

		assertEquals("tenant-001", disciplina.getTenantId());
	}

	@Test
	void prePersistNaoDeveSubstituirTenantIdJaDefinidoNaEntidade() {
		configurarTenant("tenant-001");
		Disciplina disciplina = new Disciplina();
		disciplina.setTenantId("tenant-entidade");

		listener.prePersist(disciplina);

		assertEquals("tenant-entidade", disciplina.getTenantId());
	}

	@Test
	void prePersistDeveLancarExcecaoQuandoNemContextoNemEntidadeTenantDefinido() {
		// Contexto sem tenant (blank), entidade sem tenant
		Disciplina disciplina = new Disciplina();

		DomainException exception = assertThrows(DomainException.class, () -> listener.prePersist(disciplina));

		assertEquals("SEC-1", exception.getCode());
	}

	@Test
	void prePersistNaoDeveFalharQuandoEntidadeJaTemTenantSemContexto() {
		// Contexto sem tenant, mas entidade já tem tenant definido
		Disciplina disciplina = new Disciplina();
		disciplina.setTenantId("tenant-pre-existente");

		assertDoesNotThrow(() -> listener.prePersist(disciplina));
		assertEquals("tenant-pre-existente", disciplina.getTenantId());
	}

	@Test
	void preUpdateDeveDefinirTenantIdDaContextQuandoEntidadeNaoTemTenant() {
		configurarTenant("tenant-update");
		Disciplina disciplina = new Disciplina();

		listener.preUpdate(disciplina);

		assertEquals("tenant-update", disciplina.getTenantId());
	}

	@Test
	void preUpdateDeveLancarExcecaoQuandoNemContextoNemEntidadeTenantDefinido() {
		Disciplina disciplina = new Disciplina();

		DomainException exception = assertThrows(DomainException.class, () -> listener.preUpdate(disciplina));

		assertEquals("SEC-1", exception.getCode());
	}

	private void configurarTenant(String tenantId) {
		Persona persona = new Persona();
		persona.setTenantId(tenantId);
		TenantContext.setPerfilEmUso(persona);
	}

}
