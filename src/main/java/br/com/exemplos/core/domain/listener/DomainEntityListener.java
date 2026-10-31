package br.com.exemplos.core.domain.listener;

import br.com.exemplos.core.domain.entity.DomainEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.util.Optional;

public class DomainEntityListener {

	@PrePersist
	public void prePersist(Object entity) {
		DomainEntity domainEntity = (DomainEntity) entity;
		domainEntity.setDataInsercao(Optional.ofNullable(domainEntity.getDataInsercao()).orElse(LocalDateTime.now()));
		domainEntity.setDataAtualizacao(LocalDateTime.now());
	}

	@PreUpdate
	public void preUpdate(Object entity) {
		DomainEntity domainEntity = (DomainEntity) entity;
		domainEntity.setDataAtualizacao(LocalDateTime.now());
	}

}
