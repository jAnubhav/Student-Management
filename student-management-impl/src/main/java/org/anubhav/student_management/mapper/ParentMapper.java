package org.anubhav.student_management.mapper;

import org.anubhav.model.CreateParentRequest;
import org.anubhav.model.ParentAssigned;
import org.anubhav.model.ParentDetails;
import org.anubhav.model.UpdateParentRequest;
import org.anubhav.student_management.entity.ParentEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ParentMapper {

    ParentDetails toDto(ParentEntity entity);

    ParentEntity toEntity(CreateParentRequest request);

    ParentAssigned toAssignedDto(ParentEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateParentFromDto(UpdateParentRequest dto, @MappingTarget ParentEntity entity);

}
