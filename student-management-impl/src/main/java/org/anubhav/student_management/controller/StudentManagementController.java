package org.anubhav.student_management.controller;

import org.anubhav.api.StudentManagementInterface;
import org.anubhav.model.CreateStudentRequest;
import org.anubhav.model.StudentDetailsResponse;
import org.anubhav.model.SuccessStudentResponse;
import org.anubhav.model.UpdateStudentRequest;
import org.anubhav.student_management.utility.ValidationChecks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.anubhav.student_management.utility.Constants.ENROLLMENT_NUMBER_PATH_VARIABLE_NAME;

@RestController
public class StudentManagementController implements StudentManagementInterface {

    @Override
    public ResponseEntity<SuccessStudentResponse> createStudent(CreateStudentRequest createStudentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<StudentDetailsResponse> getStudentById(String enrollmentNumber) {
        ValidationChecks.validateParameter(enrollmentNumber, ENROLLMENT_NUMBER_PATH_VARIABLE_NAME, 8);
        ValidationChecks.validateParameterValue(enrollmentNumber.substring(2), ENROLLMENT_NUMBER_PATH_VARIABLE_NAME);

        return null;
    }

    @Override
    public ResponseEntity<SuccessStudentResponse> updateStudent(String enrollmentNumber,
            UpdateStudentRequest updateStudentRequest) {
        ValidationChecks.validateParameter(enrollmentNumber, ENROLLMENT_NUMBER_PATH_VARIABLE_NAME, 8);
        ValidationChecks.validateParameterValue(enrollmentNumber.substring(2), ENROLLMENT_NUMBER_PATH_VARIABLE_NAME);

        return null;
    }

}
