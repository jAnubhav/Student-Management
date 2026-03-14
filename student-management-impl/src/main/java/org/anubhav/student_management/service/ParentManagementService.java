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

/**
 * Business service for parent create/read/update operations.
 */
@Service
public class ParentManagementService {

    private final ParentRepository repository;
    private final ParentMapper mapper;

    public ParentManagementService(ParentRepository repository, ParentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Creates a parent record from request data.
     *
     * @param createParentRequest
     *            request payload
     * @return assigned parent metadata
     */
    public ParentAssigned createParent(CreateParentRequest createParentRequest) {
        ensureDependenciesAvailable();

        ParentEntity parentEntity = mapper.toEntity(createParentRequest);
        return mapper.toAssignedDto(repository.save(parentEntity));
    }

    /**
     * Fetches a parent record by ID.
     *
     * @param parentId
     *            parent identifier
     * @return complete parent details
     */
    public ParentDetails getParentById(String parentId) {
        ensureDependenciesAvailable();

        ParentEntity parentEntity = getParentEntity(parentId);
        return mapper.toDto(parentEntity);
    }

    /**
     * Updates an existing parent record with partial data.
     *
     * @param parentId
     *            parent identifier
     * @param updateParentRequest
     *            patch payload
     * @return assigned parent metadata for the updated record
     */
    public ParentAssigned updateParentById(String parentId, UpdateParentRequest updateParentRequest) {
        ensureDependenciesAvailable();
        ParentEntity existingParentEntity = getParentEntity(parentId);

        mapper.updateParentFromDto(updateParentRequest, existingParentEntity);
        return mapper.toAssignedDto(repository.save(existingParentEntity));
    }

    /**
     * Ensures required collaborators are available before processing.
     */
    private void ensureDependenciesAvailable() {
        if (repository == null) {
            throw new DependencyUnavailableException("Parent repository is unavailable.");
        }
        if (mapper == null) {
            throw new DependencyUnavailableException("Parent mapper is unavailable.");
        }
    }

    /**
     * Gets the Parent Details for a Parent ID.
     *
     * @param parentId
     *            parent identifier
     * @return parent entity for the given ID or throws NotFoundException if not
     *         found
     */
    private ParentEntity getParentEntity(String parentId) {
        return repository.findById(parentId).orElseThrow(
                () -> new NotFoundException(
                        "Parent Details not found for Parent ID: " + parentId,
                        Constants.PARENT_ID_PATH_VARIABLE_NAME.toString()
                )
        );
    }

}
