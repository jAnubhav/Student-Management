package org.anubhav.student_management.service;

import org.anubhav.model.CreateStudentRequest;
import org.anubhav.model.ParentDetails;
import org.anubhav.model.StudentAssigned;
import org.anubhav.model.StudentDetails;
import org.anubhav.student_management.exception.DependencyUnavailableException;
import org.anubhav.student_management.entity.StudentEntity;
import org.anubhav.student_management.exception.NotFoundException;
import org.anubhav.student_management.mapper.StudentMapper;
import org.anubhav.student_management.repository.StudentRepository;
import org.anubhav.student_management.utility.Constants;
import org.springframework.stereotype.Service;

@Service
public class StudentManagementService {

    private final ParentManagementService parentManagementService;
    private final StudentRepository repository;
    private final StudentMapper mapper;

    public StudentManagementService(ParentManagementService parentManagementService, StudentRepository repository,
            StudentMapper mapper) {
        this.parentManagementService = parentManagementService;
        this.repository = repository;
        this.mapper = mapper;
    }

    public StudentAssigned createStudent(CreateStudentRequest createStudentRequest) {
        ensureDependenciesAvailable();

        StudentEntity studentEntity = mapper.toEntity(createStudentRequest);
        return mapper.toAssignedDto(repository.save(studentEntity));
    }

    public StudentDetails getStudentById(String enrollmentNumber) {
        ensureDependenciesAvailable();

        StudentEntity studentEntity = repository.findById(enrollmentNumber).orElseThrow(
                () -> new NotFoundException(
                        "Student Details not found for Enrollment Number: " + enrollmentNumber,
                        Constants.ENROLLMENT_NUMBER_PATH_VARIABLE_NAME
                )
        );

        String parentId = String.valueOf(studentEntity.getParentId());
        ParentDetails parentDetails = parentManagementService.getParentById(parentId);

        return mapper.toDto(studentEntity).parentDetails(parentDetails);
    }

    private void ensureDependenciesAvailable() {
        if (parentManagementService == null) {
            throw new DependencyUnavailableException("Parent service is unavailable.");
        }
        if (repository == null) {
            throw new DependencyUnavailableException("Student repository is unavailable.");
        }
        if (mapper == null) {
            throw new DependencyUnavailableException("Student mapper is unavailable.");
        }
    }

}
