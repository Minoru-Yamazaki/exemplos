package br.com.exemplos.inbound.service;

import br.com.exemplos.core.domain.vo.DomainCache;
import br.com.exemplos.core.event.LimparCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CacheServiceImplTest {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private CacheServiceImpl service;

    @Test
    void deveEvictarCacheQuandoCacheExiste() {
        DomainCache domainCache = new DomainCache("meuCache", "minha-key");
        LimparCache event = new LimparCache(domainCache);

        when(cacheManager.getCache("meuCache")).thenReturn(cache);

        service.on(event);

        verify(cacheManager).getCache("meuCache");
        verify(cache).evict("minha-key");
    }

    @Test
    void naoDeveEvictarCacheQuandoCacheNaoEncontrado() {
        DomainCache domainCache = new DomainCache("cacheInexistente", "qualquer-key");
        LimparCache event = new LimparCache(domainCache);

        when(cacheManager.getCache("cacheInexistente")).thenReturn(null);

        service.on(event);

        verify(cacheManager).getCache("cacheInexistente");
        verifyNoInteractions(cache);
    }

}
