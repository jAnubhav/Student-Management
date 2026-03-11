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
/**
 * Maps parent API models to persistence entities and back.
 */
public interface ParentMapper {

    /**
     * Converts a parent entity into a detailed API response model.
     *
     * @param entity source parent entity
     * @return mapped parent details model
     */
    ParentDetails toDto(ParentEntity entity);

    /**
     * Converts a create-parent request payload into a persistence entity.
     *
     * @param request source create request
     * @return mapped parent entity
     */
    ParentEntity toEntity(CreateParentRequest request);

    /**
     * Converts a parent entity into assigned-id metadata.
     *
     * @param entity source parent entity
     * @return mapped parent assigned model
     */
    ParentAssigned toAssignedDto(ParentEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    /**
     * Applies non-null update fields onto an existing parent entity.
     *
     * @param dto update request payload
     * @param entity target entity to mutate
     */
    void updateParentFromDto(UpdateParentRequest dto, @MappingTarget ParentEntity entity);

}
