package br.com.exemplos.core.domain.entity.multitenancy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FiltroMultiTenancy {

	public static final String NOME_FILTRO = "FiltroMultiTenancy";

	public static final String NOME_ATRIBUTO = "tenantId";

	public static final String NOME_COLUNA_BD = "tenantId";

}
