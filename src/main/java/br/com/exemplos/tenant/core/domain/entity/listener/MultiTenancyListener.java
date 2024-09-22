package br.com.exemplos.tenant.core.domain.entity.listener;

import br.com.exemplos.exception.DomainException;
import br.com.exemplos.tenant.core.domain.context.TenantContext;
import br.com.exemplos.tenant.core.domain.entity.multitenancy.MultiTenancyEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class MultiTenancyListener {

	@PrePersist
	public void prePersist(Object entity) {
		setTenantId((MultiTenancyEntity) entity);
	}

	@PreUpdate
	public void preUpdate(Object entity) {
		setTenantId((MultiTenancyEntity) entity);
	}

	private void setTenantId(MultiTenancyEntity entity) {

		if (entity == null)
			return;

		boolean naoPossuiTenantNaSessao = TenantContext.getTenantId().isBlank();
		boolean entidadeSemTenant = entity.isTenantNullOrEmpty();

		if (naoPossuiTenantNaSessao && entidadeSemTenant) {
			throw new DomainException("SEC-1",
					"Não é possível persistir um objeto sem definir o contexto de multitenancy."
							+ "Isto acontece porque sua request não contém o cabeçalho 'x-tenant-id' válido");
		}

		if (entidadeSemTenant) {
			entity.setTenantId(TenantContext.getTenantId());
		}
	}

}
