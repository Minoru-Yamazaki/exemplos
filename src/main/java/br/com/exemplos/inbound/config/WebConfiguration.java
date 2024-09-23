package br.com.exemplos.inbound.config;


import br.com.exemplos.inbound.interceptor.TenantInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@AllArgsConstructor
@ComponentScan(basePackages = { "br.com.exemplos.tenant.inbound"})
public class WebConfiguration implements WebMvcConfigurer {

    @Lazy
    private TenantInterceptor tenantInterceptor;

//    @Lazy
//    private TracingInterceptor tracingInterceptor;

//    private static final String SWAGGER_TITLE = "Título";
//
//    private static final String SWAGGER_DESCRIPTION = """
//            Descrição do projeto
//
//            *<b>Nota: É necessário o preenchimento da autorização utilizando o CPF no campo (Value) dentro do bloco Tenant Id (apiKey) encontrado
//            ao clicar no botão (Authorize) localizado no lado direito da tela.<b>*
//            """;

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor);
//        registry.addInterceptor(tracingInterceptor);
    }

//    @Bean
//    public CommonsRequestLoggingFilter requestLoggingFilter() {
//        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
//        loggingFilter.setIncludeClientInfo(true);
//        loggingFilter.setIncludeQueryString(true);
//        loggingFilter.setIncludePayload(true);
//        loggingFilter.setMaxPayloadLength(64000);
//        return loggingFilter;
//    }

//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .addServersItem(
//                        customServer("https://dev.api.veloe.com.br", "Ambiente de desenvolvimento externo da VELOE"))//
//                .addServersItem(
//                        customServer("https://hml.api.veloe.com.br", "Ambiente de homologação externo da VELOE"))//
//                .addServersItem(customServer("https://api.veloe.com.br", "Ambiente de produção externo da VELOE"))//
//                .addServersItem(customServer("http://gestao-vpo.app.dev.veloe.com.br",
//                        "Ambiente de desenvolvimento interna VELOE"))//
//                .addServersItem(
//                        customServer("http://gestao-vpo.app.hml.veloe.com.br", "Ambiente de homologação interna VELOE"))//
//                .addServersItem(
//                        customServer("http://gestao-vpo.app.prd.veloe.com.br", "Ambiente de produção interna VELOE"))//
//                .info(new io.swagger.v3.oas.models.info.Info() //
//                        .title(SWAGGER_TITLE) //
//                        .description(SWAGGER_DESCRIPTION) //
//                        .version("2.6.0")) // campo de versão da API
//                .components(new Components() //
//                        .addSecuritySchemes("TokenLogin", //
//                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT") //
//                                        .in(SecurityScheme.In.HEADER).name("Authorization")) //
//                        .addSecuritySchemes("TenantId", //
//                                new SecurityScheme().type(SecurityScheme.Type.APIKEY).scheme("tenantId") //
//                                        .in(SecurityScheme.In.HEADER).name("x-tenant-id")) //
//                ).addSecurityItem(new SecurityRequirement().addList("TenantId", Arrays.asList("read", "write")))
//                .addSecurityItem(new SecurityRequirement().addList("TokenLogin", Arrays.asList("read", "write")));
//    }

//    public Server customServer(String url, String description) {
//        Server server = new Server();
//        server.setUrl(url);
//        server.setDescription(description);
//        return server;
//    }

}
