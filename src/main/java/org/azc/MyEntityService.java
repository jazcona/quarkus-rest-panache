package org.azc;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.h2.util.StringUtils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class MyEntityService {
    private final MyEntityRepository myEntityRepository;

    public List<MyEntity> getMyEntities(String field) {
        if (!StringUtils.isNullOrEmpty(field)) {
            return myEntityRepository.findByField(field);
        }

        return myEntityRepository.listAll();
    }

    public Optional<MyEntity> findMyEntityById(long id) {
        return myEntityRepository.findByIdOptional(id);
    }

    @Transactional
    public void create(MyEntity myEntity) throws InvalidAttributesException {
        if (myEntity == null) {
            throw new InvalidAttributesException("MyEntity can not be null");
        }
        if (myEntity.getId() != null) {
            throw new InvalidAttributesException("Id must not be filled");
        }
        if (StringUtils.isNullOrEmpty(myEntity.getField())) {
            throw new InvalidAttributesException("Field can not be empty");
        }      

        myEntityRepository.persist(myEntity);
    }

    @Transactional
    public MyEntity replace(long myEntityId, MyEntity myEntity) {
        myEntity.setId(myEntityId);
        return myEntityRepository.update(myEntity).orElseThrow(() -> new InvalidParameterException("MyEntity not found"));
    }

    @Transactional
    public boolean delete(long myEntityId) {
        return myEntityRepository.deleteById(myEntityId);
    }
}
