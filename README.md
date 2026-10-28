# 2 -

<details>
<summary>Clique aqui para expandir</summary>

Conteúdo

</details>

# 3 - Redis

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
```
docker run --name redis -p 6379:6379 -d redis
```

### Resumo
 - O Redis como cache ajuda a reduzir a latência e a carga no banco de dados.
 - Anotação @Cacheable: Salva automaticamente o resultado do método no cache Redis.
 - Ferramentas como @Cacheable, @CacheEvict, e @CachePut permitem um controle completo do cache.
 - TTL (Time-to-Live): Garante que o cache expira após um tempo configurado.

</details>

# 4 - Application Event

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
