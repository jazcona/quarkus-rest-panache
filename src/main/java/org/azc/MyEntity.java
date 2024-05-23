package org.azc;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="myentity")
@Data
@EqualsAndHashCode(callSuper=false)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MyEntity extends PanacheEntityBase{
    @Id
    @GeneratedValue
    public Long id;
    
    @Column(name="field")
    public String field;
}

