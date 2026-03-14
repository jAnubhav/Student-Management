package org.anubhav.student_management.service;

import org.anubhav.model.CreateStudentRequest;
import org.anubhav.model.ParentDetails;
import org.anubhav.model.StudentAssigned;
import org.anubhav.model.StudentDetails;
import org.anubhav.model.UpdateStudentRequest;
import org.anubhav.student_management.exception.DependencyUnavailableException;
import org.anubhav.student_management.entity.StudentEntity;
import org.anubhav.student_management.exception.NotFoundException;
import org.anubhav.student_management.mapper.StudentMapper;
import org.anubhav.student_management.repository.StudentRepository;
import org.anubhav.student_management.utility.Constants;
import org.springframework.stereotype.Service;

/**
 * Business service for student create/read/update operations.
 */
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

    /**
     * Creates a student record from request data.
     *
     * @param createStudentRequest
     *            request payload
     * @return assigned student metadata
     */
    public StudentAssigned createStudent(CreateStudentRequest createStudentRequest) {
        ensureDependenciesAvailable();

        StudentEntity studentEntity = mapper.toEntity(createStudentRequest);
        return mapper.toAssignedDto(repository.save(studentEntity));
    }

    /**
     * Fetches a student record by enrollment number and enriches parent details.
     *
     * @param enrollmentNumber
     *            student enrollment identifier
     * @return complete student details
     */
    public StudentDetails getStudentById(String enrollmentNumber) {
        ensureDependenciesAvailable();
        StudentEntity studentEntity = getStudentEntity(enrollmentNumber);

        String parentId = String.valueOf(studentEntity.getParentId());
        ParentDetails parentDetails = parentManagementService.getParentById(parentId);

        return mapper.toDto(studentEntity).parentDetails(parentDetails);
    }

    /**
     * Updates an existing student record with partial data.
     *
     * @param enrollmentNumber
     *            student enrollment identifier
     * @param updateStudentRequest
     *            patch payload
     * @return assigned student metadata for the updated record
     */
    public StudentAssigned updateStudentById(String enrollmentNumber, UpdateStudentRequest updateStudentRequest) {
        ensureDependenciesAvailable();
        StudentEntity existingStudentEntity = getStudentEntity(enrollmentNumber);

        mapper.updateStudentFromDto(updateStudentRequest, existingStudentEntity);
        return mapper.toAssignedDto(repository.save(existingStudentEntity));
    }

    /**
     * Ensures required collaborators are available before processing.
     */
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

    /**
     * Gets the Student Entity for an Enrollment Number.
     *
     * @param enrollmentNumber
     *            student enrollment identifier
     * @return student entity for the given enrollment number or throws
     *         NotFoundException if not found
     */
    private StudentEntity getStudentEntity(String enrollmentNumber) {
        return repository.findById(enrollmentNumber).orElseThrow(
                () -> new NotFoundException(
                        "Student Details not found for Enrollment Number: " + enrollmentNumber,
                        Constants.ENROLLMENT_NUMBER_PATH_VARIABLE_NAME.toString()
                )
        );
    }

}
