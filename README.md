# 1 - Redis

<details>
<summary>Cache</summary>

### *dependencies:*
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
### *properties:*
```
#spring.redis.host=localhost
#spring.redis.port=6379
#spring.redis.password=
```
### *config:*
```java
@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        var keySerializer = new StringRedisSerializer();
        var valueSerializer = GenericJacksonJsonRedisSerializer.builder()
                .build();

        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);

        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);

        return template;
    }

    // Configuração de cache
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5)) // Define o TTL para 5 minutos
                .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }

}
```

### Implementar *Serializable*
```java
import java.io.Serializable;

public class ProfessorDTO implements Serializable {

    private String nome;
    private String cpf;
    private List<DisciplinaDTO> disciplinas;
    
    // getters and setters

}
```

### Anotar método com *@Cacheable*
```java
public class ProfessorServiceImpl implements ProfessorService {

    // atributos

    @Cacheable(value = "professores", key = "#id")
    public ProfessorDTO buscarPorId(Long id) {
        // processamento
    }
    
}
```

### Remover Cache com *@CacheEvict*
```java
@CacheEvict(value = "professores", key = "#id")
public void excluir(Long id) {
    repository.deleteById(id);
}
```

### Atualizar Cache com *@CachePut*
```java
@CachePut(value = "professores", key = "#dto.id")
public ProfessorDTO atualizar(ProfessorDTO dto) {
    // atualizar professor
}
```

### Docker: Execute o Redis em um contêiner *Docker*
```bash
docker run --name redis -p 6379:6379 -d redis
```

### Resumo
 - O Redis como cache ajuda a reduzir a latência e a carga no banco de dados.
 - Anotação @Cacheable: Salva automaticamente o resultado do método no cache Redis.
 - Ferramentas como @Cacheable, @CacheEvict, e @CachePut permitem um controle completo do cache.
 - TTL (Time-to-Live): Garante que o cache expira após um tempo configurado.

</details>

# 2 - Application Event

<details>
<summary>Publish Event</summary>

Crie um evento que estenda de *ApplicationEvent* e no construtor passe uma classe que deseje recuperar.
```java
public class LimparCache extends ApplicationEvent {

    public LimparCache(DomainCache cache) {
        super(cache);
    }

    public DomainCache getCache(){
        return (DomainCache) this.getSource();
    }

}
```

Publique o evento no fluxo onde desejar que ocorra a alteração.
```java
public class SalvarDisciplina {
    
    public Disciplina execute(Disciplina disciplina){
        // código ocultado
        applicationEventPublisher.publishEvent(new LimparCache(
                new DomainCache("professores", disciplina.getProfessorId().toString())));    
        return disciplina;
    }

}
```
Crie um *Listener* que reagirá com o evento:
```java
public class CacheService {
    
    @EventListener
    public void on(LimparCache event){
        // código para limpar o cache
    }

}
```

</details>

# 3 - GitHub Actions
<details>
    <summary>Entendendo o código da pipeline</summary>

### 3.1 Nome do workflow

```yaml
name: CI - Java
```   
É o nome que aparecerá na interface do GitHub.
Ele ajuda a identificar a automação na aba Actions do repositório.

### 3.2. Eventos que iniciam a execução
```yaml
on:
  push:
    branches:
      - main

  pull_request:
    branches:
      - main
```   
Esse trecho define quando o workflow será executado.
Temos dois eventos.
Evento 1: push
Quando alguém envia commits diretamente para a branch main, o pipeline é iniciado.
Por exemplo:
```bash
git add .
git commit -m "feat: adiciona cadastro de produtos"
git push origin main
```  

Evento 2: pull_request
Quando alguém abre ou atualiza um Pull Request direcionado à branch main, o pipeline também é iniciado.
Isso é importante porque permite verificar se as alterações passam nos testes antes de serem incorporadas à branch principal.
Observe uma distinção importante: configurar o workflow para executar em Pull Requests não impede, por si só, que alguém incorpore código sem passar pelos testes. Para impor essa regra, precisamos configurar as proteções da branch no GitHub.

### 3.3. Definindo um job
```yaml
jobs:
    build:
        name: Compilar e testar
        runs-on: ubuntu-latest
```  

Aqui definimos um job chamado build.
O atributo:
```yaml
runs-on: ubuntu-latest
```  

determina o ambiente no qual o job será executado.
Nesse caso, o GitHub disponibilizará um runner com Ubuntu.
Pense nele como uma máquina que recebe o código, prepara o ambiente e executa os comandos do pipeline.
O runner não precisa ser o mesmo computador utilizado para desenvolver a aplicação.

### 3.4. Baixando o código
```yaml
- name: Baixar código
  uses: actions/checkout@v4
```  


O runner precisa acessar os arquivos do seu projeto para compilá-lo e executar os testes.
A Action actions/checkout realiza o checkout do repositório no ambiente de execução.
Sem essa etapa, o job normalmente não terá o código do projeto disponível no diretório de trabalho.
O campo uses indica que estamos utilizando uma Action reutilizável, em vez de executar diretamente um comando de shell.

### 3.5. Configurando o Java
```yaml
- name: Configurar Java
  uses: actions/setup-java@v4
  with:
    distribution: temurin
    java-version: '21'
    cache: maven
```  


Essa etapa prepara o ambiente Java.
Cada configuração tem uma função:

| Configuração  |	Função
|---------------|---------------
| distribution: | temurin	Escolhe a distribuição Eclipse Temurin do Java
| java-version: | '21'	Define a versão do Java
| cache: maven  | Habilita o cache de dependências do Maven

O cache pode reduzir o tempo das próximas execuções ao evitar downloads desnecessários de dependências.
Isso não significa que o Maven deixará de verificar ou resolver as dependências necessárias.

### 3.6. Executando os testes
```yaml
- name: Executar testes
  run: mvn -B verify
```  
Aqui executamos um comando no terminal do runner.
O Maven executará as fases necessárias do ciclo de build até verify, incluindo, conforme a configuração do projeto:
- Compilação do código principal.
- Compilação dos testes.
- Execução dos testes unitários.
- Outras verificações e testes associados às fases anteriores.
- Verificações adicionais configuradas para a fase verify.

O parâmetro -B ativa o modo não interativo do Maven, adequado para execução automatizada.
Por que usar verify em vez de apenas test?
O comando mvn test executa as fases necessárias até a fase de testes.
Já mvn verify avança até a fase de verificação, podendo executar etapas adicionais configuradas no projeto, como verificações de integração ou qualidade.
Entretanto, verify não garante automaticamente que todos os testes de integração, verificações de qualidade ou análises de segurança existam. Isso depende das configurações do pom.xml.

</details>

# 4 - Exemplo

<details>
<summary>Clique aqui para expandir</summary>

Conteúdo

</details>