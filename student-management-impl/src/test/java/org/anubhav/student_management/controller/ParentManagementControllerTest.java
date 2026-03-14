package org.anubhav.student_management.controller;

import org.anubhav.model.CreateParentRequest;
import org.anubhav.model.ParentAssigned;
import org.anubhav.model.ParentDetails;
import org.anubhav.model.ParentDetailsResponse;
import org.anubhav.model.SuccessParentResponse;
import org.anubhav.model.UpdateParentRequest;
import org.anubhav.student_management.exception.DependencyUnavailableException;
import org.anubhav.student_management.service.ParentManagementService;
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
class ParentManagementControllerTest {

    private final CreateParentRequest createParentRequest = new CreateParentRequest();
    private final UpdateParentRequest updateParentRequest = new UpdateParentRequest();
    private final ParentAssigned parentAssigned = new ParentAssigned();
    private final ParentDetails parentDetails = new ParentDetails();

    @Mock
    private ParentManagementService service;

    @InjectMocks
    private ParentManagementController controller;

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testCreateOrUpdateParent(boolean createParent) {
        Mockito.when(
                createParent
                        ? service.createParent(createParentRequest)
                        : service.updateParentById("220001", updateParentRequest)
        ).thenReturn(parentAssigned);

        ResponseEntity<SuccessParentResponse> response = createParent
                ? controller.createParent(createParentRequest)
                : controller.updateParentById("220001", updateParentRequest);;
        Assertions.assertNotNull(response, Constants.RESPONSE_NOT_NULL.toString());

        SuccessParentResponse successParentResponse = response.getBody();
        Assertions.assertNotNull(successParentResponse, Constants.SUCCESS_RESPONSE_NOT_NULL.toString());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), Constants.INVALID_STATUS_CODE.toString());
        Assertions.assertEquals(
                SuccessParentResponse.RequestStatusEnum.SUCCESS,
                successParentResponse.getRequestStatus(),
                Constants.REQUEST_STATUS_SUCCESS.toString()
        );
        Assertions.assertEquals(
                parentAssigned,
                successParentResponse.getParentDetails(),
                Constants.PARENT_DETAILS_NOT_MATCH.toString()
        );
    }

    @Test
    void testGetParentById() {
        Mockito.when(service.getParentById("220001")).thenReturn(parentDetails);

        ResponseEntity<ParentDetailsResponse> response = controller.getParentById("220001");
        Assertions.assertNotNull(response, Constants.RESPONSE_NOT_NULL.toString());

        ParentDetailsResponse detailsResponse = response.getBody();
        Assertions.assertNotNull(detailsResponse, Constants.SUCCESS_RESPONSE_NOT_NULL.toString());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), Constants.INVALID_STATUS_CODE.toString());
        Assertions.assertEquals(
                ParentDetailsResponse.RequestStatusEnum.SUCCESS,
                detailsResponse.getRequestStatus(),
                Constants.REQUEST_STATUS_SUCCESS.toString()
        );
        Assertions.assertEquals(
                parentDetails,
                detailsResponse.getParentDetails(),
                Constants.PARENT_DETAILS_NOT_MATCH.toString()
        );
    }

    @Test
    void testParentControllerWhenServiceNull() {
        ParentManagementController nullServiceController = new ParentManagementController(null);

        Assertions.assertThrows(
                DependencyUnavailableException.class,
                () -> nullServiceController.getParentById("220001"),
                Constants.EXCEPTION_NOT_THROWN.toString()
        );
    }

}
