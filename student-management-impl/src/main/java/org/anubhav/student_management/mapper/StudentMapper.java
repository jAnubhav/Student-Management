package org.anubhav.student_management.mapper;

import org.anubhav.model.CreateStudentRequest;
import org.anubhav.model.StudentAssigned;
import org.anubhav.model.StudentDetails;
import org.anubhav.model.UpdateStudentRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.anubhav.student_management.entity.StudentEntity;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Maps student API models to persistence entities and back.
 */
@Mapper(componentModel = "spring")
public interface StudentMapper {

    /**
     * Converts a student entity into a detailed API response model.
     *
     * @param entity
     *            source student entity
     * @return mapped student details model
     */
    @Mapping(target = "parentDetails.parentId", source = "parentId")
    StudentDetails toDto(StudentEntity entity);

    /**
     * Converts a create-student request payload into a persistence entity.
     *
     * @param request
     *            source create request
     * @return mapped student entity
     */
    StudentEntity toEntity(CreateStudentRequest request);

    /**
     * Converts a student entity into assigned-id metadata.
     *
     * @param entity
     *            source student entity
     * @return mapped student assigned model
     */
    StudentAssigned toAssignedDto(StudentEntity entity);

    /**
     * Applies non-null update fields onto an existing student entity.
     *
     * @param dto
     *            update request payload
     * @param entity
     *            target entity to mutate
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDto(UpdateStudentRequest dto, @MappingTarget StudentEntity entity);

}
