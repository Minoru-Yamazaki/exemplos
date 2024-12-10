package br.com.exemplos.inbound.service.cache;

import br.com.exemplos.core.domain.vo.DomainCache;
import br.com.exemplos.core.domain.event.cache.LimparCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    @EventListener
    public void on(LimparCache event){
        DomainCache domainCache = event.getCache();
        Cache cache = cacheManager.getCache(domainCache.getCacheName());
        if (cache != null) {
            log.info("Limpando cache '{}' c/ key={}", domainCache.getCacheName(), domainCache.getKey());
            cache.evict(domainCache.getKey());
        }
    }

}
