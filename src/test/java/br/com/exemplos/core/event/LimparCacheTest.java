package br.com.exemplos.core.event;

import br.com.exemplos.core.domain.vo.DomainCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LimparCacheTest {

	@Test
	void deveCriarEventoComDomainCacheCorreto() {
		DomainCache domainCache = new DomainCache("professores", "1");

		LimparCache event = new LimparCache(domainCache);

		assertNotNull(event.getCache());
		assertSame(domainCache, event.getCache());
	}

	@Test
	void getCacheDeveRetornarMesmoDomainCachePassadoNoConstrutor() {
		DomainCache domainCache = new DomainCache("disciplinas", "99");

		LimparCache event = new LimparCache(domainCache);

		assertEquals("disciplinas", event.getCache().getCacheName());
		assertEquals("99", event.getCache().getKey());
	}

	@Test
	void getSourceDeveSerMesmoDomainCache() {
		DomainCache domainCache = new DomainCache("cache", "key");

		LimparCache event = new LimparCache(domainCache);

		assertSame(domainCache, event.getSource());
	}

}
