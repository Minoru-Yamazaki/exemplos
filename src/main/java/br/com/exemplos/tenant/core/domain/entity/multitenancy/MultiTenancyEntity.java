package br.com.exemplos.tenant.core.domain.entity.multitenancy;

import br.com.exemplos.tenant.core.domain.entity.DomainEntity;
import br.com.exemplos.tenant.core.domain.entity.listener.MultiTenancyListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(MultiTenancyListener.class)
@FilterDef(name = FiltroMultiTenancy.NOME_FILTRO,
        parameters = { @ParamDef(name = FiltroMultiTenancy.NOME_ATRIBUTO, type = String.class) })
@Filter(name = FiltroMultiTenancy.NOME_FILTRO,
        condition = FiltroMultiTenancy.NOME_COLUNA_BD + "= :" + FiltroMultiTenancy.NOME_ATRIBUTO)
public class MultiTenancyEntity extends DomainEntity {

    private String tenantId;

    public boolean isTenantNullOrEmpty() {
        return this.tenantId == null || this.tenantId.isEmpty();
    }

}
