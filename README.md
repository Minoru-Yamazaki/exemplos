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

        // Configura os serializadores para chaves e valores
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

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
