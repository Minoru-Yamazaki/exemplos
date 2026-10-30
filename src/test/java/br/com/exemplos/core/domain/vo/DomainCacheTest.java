package br.com.exemplos.core.domain.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainCacheTest {

    @Test
    void deveCriarDomainCacheComValoresCorretos() {
        DomainCache domainCache = new DomainCache("professores", "42");

        assertEquals("professores", domainCache.getCacheName());
        assertEquals("42", domainCache.getKey());
    }

    @Test
    void devePermitirAtualizarCacheName() {
        DomainCache domainCache = new DomainCache("original", "key");

        domainCache.setCacheName("atualizado");

        assertEquals("atualizado", domainCache.getCacheName());
    }

    @Test
    void devePermitirAtualizarKey() {
        DomainCache domainCache = new DomainCache("cache", "antiga");

        domainCache.setKey("nova");

        assertEquals("nova", domainCache.getKey());
    }

    @Test
    void toStringDeveConterCacheNameEKey() {
        DomainCache domainCache = new DomainCache("professores", "99");

        String result = domainCache.toString();

        assertTrue(result.contains("professores"));
        assertTrue(result.contains("99"));
    }

}
