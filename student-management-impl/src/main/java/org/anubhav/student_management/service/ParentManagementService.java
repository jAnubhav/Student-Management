package org.anubhav.student_management.service;

import org.anubhav.model.CreateParentRequest;
import org.anubhav.model.ParentAssigned;
import org.anubhav.model.ParentConfigured;
import org.anubhav.model.ParentDetails;
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
        ParentConfigured parentConfigured = mapper.toConfiguredDto(createParentRequest);
        ParentEntity parentEntity = mapper.toEntity(parentConfigured);
        return mapper.toAssignedDto(repository.save(parentEntity));
    }

    public ParentDetails getParentById(String parentId) {
        ParentEntity parentEntity = repository.findById(parentId).orElseThrow(
                () -> new NotFoundException(
                        "Parent Details not found for Parent ID: " + parentId,
                        Constants.PARENT_ID_PATH_VARIABLE_NAME
                )
        );
        return mapper.toDto(parentEntity);
    }

}
