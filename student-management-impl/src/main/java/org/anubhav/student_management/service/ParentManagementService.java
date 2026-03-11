package org.anubhav.student_management.service;

import org.anubhav.model.CreateParentRequest;
import org.anubhav.model.ParentAssigned;
import org.anubhav.model.ParentDetails;
import org.anubhav.model.UpdateParentRequest;
import org.anubhav.student_management.exception.DependencyUnavailableException;
import org.anubhav.student_management.entity.ParentEntity;
import org.anubhav.student_management.exception.NotFoundException;
import org.anubhav.student_management.mapper.ParentMapper;
import org.anubhav.student_management.repository.ParentRepository;
import org.anubhav.student_management.utility.Constants;
import org.springframework.stereotype.Service;

@Service
public class ParentManagementService {

    private final ParentRepository repository;
    private final ParentMapper mapper;

    public ParentManagementService(ParentRepository repository, ParentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ParentAssigned createParent(CreateParentRequest createParentRequest) {
        ensureDependenciesAvailable();

        ParentEntity parentEntity = mapper.toEntity(createParentRequest);
        return mapper.toAssignedDto(repository.save(parentEntity));
    }

    public ParentDetails getParentById(String parentId) {
        ensureDependenciesAvailable();

        ParentEntity parentEntity = repository.findById(parentId).orElseThrow(
                () -> new NotFoundException(
                        "Parent Details not found for Parent ID: " + parentId,
                        Constants.PARENT_ID_PATH_VARIABLE_NAME
                )
        );
        return mapper.toDto(parentEntity);
    }

    public ParentAssigned updateParentById(String parentId, UpdateParentRequest updateParentRequest) {
        ensureDependenciesAvailable();

        ParentEntity existingParentEntity = repository.findById(parentId).orElseThrow(
                () -> new NotFoundException(
                        "Parent Details not found for Parent ID: " + parentId,
                        Constants.PARENT_ID_PATH_VARIABLE_NAME
                )
        );

        mapper.updateParentFromDto(updateParentRequest, existingParentEntity);
        return mapper.toAssignedDto(repository.save(existingParentEntity));
    }

    private void ensureDependenciesAvailable() {
        if (repository == null) {
            throw new DependencyUnavailableException("Parent repository is unavailable.");
        }
        if (mapper == null) {
            throw new DependencyUnavailableException("Parent mapper is unavailable.");
        }
    }

}
