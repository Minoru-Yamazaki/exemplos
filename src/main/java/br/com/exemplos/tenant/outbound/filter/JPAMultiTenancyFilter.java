package br.com.exemplos.tenant.outbound.filter;

import br.com.exemplos.tenant.core.domain.context.TenantContext;
import br.com.exemplos.tenant.core.domain.entity.multitenancy.FiltroMultiTenancy;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class JPAMultiTenancyFilter {

    @PostConstruct
    public void postConstruct() {
        log.info("JPAMultiTenancyFilter iniciado!");
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Before(value = "execution(* br.com.veloe.vpo.compra.outbound.repository.*.*(..))"
            + " || execution(* org.springframework.data.jpa.repository.support.*.*(..))"
            + " || execution(* br.com.veloe.vpo.compra.core.domain.repository.*.*(..))")
    public void enableFilter(JoinPoint joinPoint) {

        if (!(joinPoint.getThis() instanceof Repository))
            return;

        Session hibernateSession = (Session) entityManager.getDelegate();

        boolean possuiFiltro = hibernateSession.getSessionFactory().getDefinedFilterNames()
                .contains(FiltroMultiTenancy.NOME_FILTRO);

        if (!possuiFiltro)
            return;

        boolean possuiTenantId = !TenantContext.getTenantId().isBlank();

        if (possuiTenantId) {
            if (!hibernateSession.isOpen()) {
                log.error(
                        "Filtro {} não habilitado, sessão não gerenciada pelo springboot. Utilizar @Transactional no método.",
                        FiltroMultiTenancy.NOME_FILTRO);
                return;
            }

            log.info("Filtro {} habilitado. Tenant {}.", FiltroMultiTenancy.NOME_FILTRO, TenantContext.getTenantId());
            Filter filter = hibernateSession.enableFilter(FiltroMultiTenancy.NOME_FILTRO);
            filter.setParameter(FiltroMultiTenancy.NOME_ATRIBUTO, TenantContext.getPerfilEmUso().getTenantId());
        }
        else {
            if (hibernateSession.isOpen()) {
                log.info("Filtro {} desabilitado. Tenant não preenchido.", FiltroMultiTenancy.NOME_FILTRO);
                hibernateSession.disableFilter(FiltroMultiTenancy.NOME_FILTRO);
            }
        }
    }

}
