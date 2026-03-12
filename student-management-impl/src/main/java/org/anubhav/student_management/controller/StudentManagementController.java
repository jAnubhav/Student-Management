package org.anubhav.student_management.controller;

import org.anubhav.api.StudentManagementInterface;
import org.anubhav.model.CreateStudentRequest;
import org.anubhav.model.StudentAssigned;
import org.anubhav.model.StudentDetails;
import org.anubhav.model.StudentDetailsResponse;
import org.anubhav.model.SuccessStudentResponse;
import org.anubhav.model.UpdateStudentRequest;
import org.anubhav.student_management.exception.DependencyUnavailableException;
import org.anubhav.student_management.service.StudentManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for student endpoints.
 */
@RestController
public class StudentManagementController implements StudentManagementInterface {

    private final StudentManagementService service;

    public StudentManagementController(StudentManagementService service) {
        this.service = service;
    }

    /**
     * Creates a student and returns assigned enrollment metadata.
     *
     * @param createStudentRequest
     *            validated create-student payload
     * @return successful response containing assigned student details
     */
    @Override
    public ResponseEntity<SuccessStudentResponse> createStudent(CreateStudentRequest createStudentRequest) {
        ensureDependenciesAvailable();

        StudentAssigned studentAssigned = service.createStudent(createStudentRequest);
        SuccessStudentResponse response = new SuccessStudentResponse(
                SuccessStudentResponse.RequestStatusEnum.SUCCESS,
                studentAssigned
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves student details by enrollment number.
     *
     * @param enrollmentNumber
     *            unique student enrollment identifier
     * @return successful response containing full student details
     */
    @Override
    public ResponseEntity<StudentDetailsResponse> getStudentById(String enrollmentNumber) {
        ensureDependenciesAvailable();

        StudentDetails studentDetails = service.getStudentById(enrollmentNumber);
        StudentDetailsResponse response = new StudentDetailsResponse(
                StudentDetailsResponse.RequestStatusEnum.SUCCESS,
                studentDetails
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Updates student details by enrollment number.
     *
     * @param enrollmentNumber
     *            unique student enrollment identifier
     * @param updateStudentRequest
     *            partial update payload
     * @return successful response containing assigned student metadata
     */
    @Override
    public ResponseEntity<SuccessStudentResponse> updateStudent(String enrollmentNumber,
            UpdateStudentRequest updateStudentRequest) {
        ensureDependenciesAvailable();

        StudentAssigned studentAssigned = service.updateStudentById(enrollmentNumber, updateStudentRequest);
        SuccessStudentResponse response = new SuccessStudentResponse(
                SuccessStudentResponse.RequestStatusEnum.SUCCESS,
                studentAssigned
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Ensures required collaborators are available before processing.
     */
    private void ensureDependenciesAvailable() {
        if (service == null) {
            throw new DependencyUnavailableException("Student service is unavailable.");
        }
    }

}
