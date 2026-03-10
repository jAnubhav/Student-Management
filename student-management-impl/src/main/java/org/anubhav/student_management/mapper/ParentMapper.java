package org.anubhav.student_management.mapper;

import org.anubhav.model.CreateParentRequest;
import org.anubhav.model.ParentAssigned;
import org.anubhav.model.ParentConfigured;
import org.anubhav.model.ParentDetails;
import org.anubhav.student_management.entity.ParentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParentMapper {

    ParentDetails toDto(ParentEntity entity);

    ParentConfigured toConfiguredDto(CreateParentRequest request);

    ParentEntity toEntity(ParentConfigured dto);

    ParentAssigned toAssignedDto(ParentEntity entity);

}
