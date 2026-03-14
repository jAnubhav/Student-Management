package org.anubhav.student_management.controller;

import org.anubhav.model.CreateStudentRequest;
import org.anubhav.model.StudentAssigned;
import org.anubhav.model.StudentDetails;
import org.anubhav.model.StudentDetailsResponse;
import org.anubhav.model.SuccessStudentResponse;
import org.anubhav.model.UpdateStudentRequest;
import org.anubhav.student_management.exception.DependencyUnavailableException;
import org.anubhav.student_management.service.StudentManagementService;
import org.anubhav.student_management.utils.Constants;
import org.anubhav.student_management.utils.TestType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag(TestType.UNIT)
@ExtendWith(MockitoExtension.class)
class StudentManagementControllerTest {

    private final CreateStudentRequest createStudentRequest = new CreateStudentRequest();
    private final UpdateStudentRequest updateStudentRequest = new UpdateStudentRequest();
    private final StudentAssigned studentAssigned = new StudentAssigned();
    private final StudentDetails studentDetails = new StudentDetails();

    @Mock
    private StudentManagementService service;

    @InjectMocks
    private StudentManagementController controller;

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testCreateOrUpdateStudent(boolean createStudent) {
        Mockito.when(
                createStudent
                        ? service.createStudent(createStudentRequest)
                        : service.updateStudentById("220001", updateStudentRequest)
        ).thenReturn(studentAssigned);

        ResponseEntity<SuccessStudentResponse> response = createStudent
                ? controller.createStudent(createStudentRequest)
                : controller.updateStudent("220001", updateStudentRequest);
        Assertions.assertNotNull(response, Constants.RESPONSE_NOT_NULL.toString());

        SuccessStudentResponse successStudentResponse = response.getBody();
        Assertions.assertNotNull(successStudentResponse, Constants.SUCCESS_RESPONSE_NOT_NULL.toString());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), Constants.INVALID_STATUS_CODE.toString());
        Assertions.assertEquals(
                SuccessStudentResponse.RequestStatusEnum.SUCCESS,
                successStudentResponse.getRequestStatus(),
                Constants.REQUEST_STATUS_SUCCESS.toString()
        );
        Assertions.assertEquals(
                studentAssigned,
                successStudentResponse.getStudentDetails(),
                Constants.STUDENT_ASSIGNED_NOT_MATCH.toString()
        );
    }

    @Test
    void testGetStudentById() {
        Mockito.when(service.getStudentById("220001")).thenReturn(studentDetails);

        ResponseEntity<StudentDetailsResponse> response = controller.getStudentById("220001");
        Assertions.assertNotNull(response, Constants.RESPONSE_NOT_NULL.toString());

        StudentDetailsResponse detailsResponse = response.getBody();
        Assertions.assertNotNull(detailsResponse, Constants.SUCCESS_RESPONSE_NOT_NULL.toString());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), Constants.INVALID_STATUS_CODE.toString());
        Assertions.assertEquals(
                StudentDetailsResponse.RequestStatusEnum.SUCCESS,
                detailsResponse.getRequestStatus(),
                Constants.REQUEST_STATUS_SUCCESS.toString()
        );
        Assertions.assertEquals(
                studentDetails,
                detailsResponse.getStudentDetails(),
                Constants.STUDENT_DETAILS_NOT_MATCH.toString()
        );
    }

    @Test
    void testStudentControllerWhenServiceNull() {
        StudentManagementController nullServiceController = new StudentManagementController(null);
        Assertions.assertThrows(
                DependencyUnavailableException.class,
                () -> nullServiceController.getStudentById("220001"),
                Constants.EXCEPTION_NOT_THROWN.toString()
        );
    }

}
