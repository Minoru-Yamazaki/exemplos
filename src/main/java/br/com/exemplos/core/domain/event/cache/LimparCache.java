package br.com.exemplos.core.domain.event.cache;

import br.com.exemplos.core.domain.vo.DomainCache;
import org.springframework.context.ApplicationEvent;

public class LimparCache extends ApplicationEvent {

    public LimparCache(DomainCache cache) {
        super(cache);
    }

    public DomainCache getCache(){
        return (DomainCache) this.getSource();
    }

}
