package br.com.exemplos.core.domain.entity.multitenancy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FiltroMultiTenancy {

	public static final String NOME_FILTRO = "FiltroMultiTenancy";

	public static final String NOME_ATRIBUTO = "tenantId";

	/**
	 * O padrão seria tenant_id, mas alteramos para seguir a padronização de nomes
	 * enviados pelos DBAs do time do Dorighelo
	 * (https://muralisti-my.sharepoint.com/:w:/g/personal/lucas_israel_muralis_com_br/EUkRDp4gXQBIl4gbL_DT4S8BmLA4Vl1Tg-6t0Sk3Qr4L6A?e=mQPYjV,
	 * página: 3 - Tableas e Colunas) <br>
	 * Documento enviado por Andrey (aalmeida@veloe.com.br)
	 */
	public static final String NOME_COLUNA_BD = "tenantId";

}
