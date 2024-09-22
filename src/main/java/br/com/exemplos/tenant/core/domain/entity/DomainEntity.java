package br.com.exemplos.tenant.core.domain.entity;

import br.com.exemplos.tenant.core.domain.entity.listener.DomainEntityListener;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(DomainEntityListener.class)
public abstract class DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataInsercao;

    private LocalDateTime dataAtualizacao;

//    @Override
//    public boolean equals(Object object) {
//        if (this == object)
//            return true;
//
//        if (object == null || getClass() != object.getClass())
//            return false;
//
//        DomainEntity that = (DomainEntity) object;
//
//        return new EqualsBuilder().append(id, that.id).isEquals();
//    }
//
//    @Override
//    public int hashCode() {
//        return new HashCodeBuilder(17, 37).append(id).toHashCode();
//    }

}
