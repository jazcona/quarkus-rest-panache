package org.azc;

import java.util.List;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyEntityRepository implements PanacheRepository<MyEntity>{
    public List<MyEntity> findByField(String field) {
        return this.list(field);
    }

    public Optional<MyEntity> update(MyEntity myEntity) {
        final var id = myEntity.getId();
        var savedOpt = this.findByIdOptional(id);
        if (savedOpt.isEmpty()) {
            return Optional.empty();
        }
    
        var saved = savedOpt.get();
        saved.setField(myEntity.getField());
    
        return Optional.of(saved);
    }
}
