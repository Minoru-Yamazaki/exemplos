package br.com.exemplos.tenant.outbound.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "br.com.exemplos.tenant.outbound")
@EntityScan(basePackages = "br.com.exemplos.tenant.core.domain.entity")
@ComponentScan(basePackages = { "br.com.exemplos.tenant.outbound" })
public class JPAConfiguration {

}
