package br.com.exemplos.core.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DomainCache {

    private String cacheName;

    private String key;

    public DomainCache(String cacheName, String key) {
        this.cacheName = cacheName;
        this.key = key;
    }

}
