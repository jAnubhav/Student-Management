package org.anubhav.student_management.mapper;

import org.anubhav.model.CreateStudentRequest;
import org.anubhav.model.StudentAssigned;
import org.anubhav.model.StudentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.anubhav.student_management.entity.StudentEntity;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "parentDetails.parentId", source = "parentId")
    StudentDetails toDto(StudentEntity entity);

    StudentEntity toEntity(CreateStudentRequest request);

    StudentAssigned toAssignedDto(StudentEntity entity);

}
