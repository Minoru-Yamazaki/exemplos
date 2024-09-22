package br.com.exemplos.tenant.inbound.interceptor;

import br.com.caelum.stella.tinytype.CPF;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.exemplos.exception.DomainException;
import br.com.exemplos.tenant.core.domain.context.TenantContext;
import br.com.exemplos.tenant.core.domain.entity.multitenancy.Persona;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantInterceptor implements HandlerInterceptor {

    private static final String TENANT_ID_HEADER = "x-tenant-id";

//    private static final String TOKEN_HEADER = "token";

    private static final List<String> IGNORED_TENANT_URIs = List.of("/actuator/", "/v1/nome-projeto/doc",
            "/v1/nome-projeto/api-doc", "/v1/nome-projeto/swagger-ui");

    @Override
    public boolean preHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object object) {

        TenantContext.clear();

        if (!IGNORED_TENANT_URIs.stream().anyMatch(uri -> httpRequest.getRequestURI().startsWith(uri))) {
            long startTime = System.currentTimeMillis();
            log.info("Executando TenantInterceptor.");

//            if (!liberarAplicacaoUsoInterno(httpRequest) && !validarPermissaoTenantId(httpRequest)) {
            String tenant = httpRequest.getHeader(TENANT_ID_HEADER);
            if (!idValid(tenant)) {
                log.info(
                        "Execução de TenantInterceptor completada em {} ms. Usuário não autorizado a utilizar o tenant.",
                        System.currentTimeMillis() - startTime);
                throw DomainException.tenantNaoAutorizado();
            }

            setPersonaNoTenantContext(tenant);
            log.info("Execução de TenantInterceptor completada em {} ms. Aplicação interna liberada.",
                    System.currentTimeMillis() - startTime);
        }
        return true;
    }

    private void setPersonaNoTenantContext(String tenant) {
        Persona persona = new Persona();
        persona.setTenantId(tenant);
        persona.setAplicacao("VPO");
        persona.setPapel("EMBARCADOR");
        TenantContext.setPerfilEmUso(persona);
    }

//    private boolean liberarAplicacaoUsoInterno(HttpServletRequest httpServletRequest) {
//
//        // Caso o token não esteja preenchido, quer dizer que a requisição não passou
//        // pelo API Gateway, pois todas as requisições que passam pelo API Gateway validam
//        // o token, dessa forma foi uma requisição interna que não precisa validar acesso
//
//        Optional<String> token = Optional.ofNullable(httpServletRequest.getHeader(TOKEN_HEADER));
//        return token.isEmpty();
//    }

//    private boolean validarPermissaoTenantId(HttpServletRequest httpServletRequest) {
//
//        Optional<String> tenantId = Optional.ofNullable(httpServletRequest.getHeader(TENANT_ID_HEADER));
//        Optional<String> token = Optional.ofNullable(httpServletRequest.getHeader(TOKEN_HEADER));
//
//        if (tenantId.isEmpty() || token.isEmpty()) {
//            return false;
//        }
//
//        return autenticacaoService.podeUtilizarEsseTenantId(token.get(), tenantId.get());
//    }

    private boolean idValid(String tenantId){
        CPF cpf = new CPF(tenantId);
        return cpf.isValido();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
        TenantContext.clear();
    }

}