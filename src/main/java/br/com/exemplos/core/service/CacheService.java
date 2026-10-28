package br.com.exemplos.core.service;

import br.com.exemplos.core.event.LimparCache;

public interface CacheService {

	void on(LimparCache event);

}
