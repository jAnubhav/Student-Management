package org.anubhav.student_management.controller;

import org.anubhav.api.ParentManagementInterface;
import org.anubhav.model.CreateParentRequest;
import org.anubhav.model.ParentDetailsResponse;
import org.anubhav.model.SuccessParentResponse;
import org.anubhav.model.UpdateParentRequest;
import org.anubhav.student_management.utility.ValidationChecks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.anubhav.student_management.utility.Constants.PARENT_ID_PATH_VARIABLE_NAME;

@RestController
public class ParentManagementController implements ParentManagementInterface {

    @Override
    public ResponseEntity<SuccessParentResponse> createParent(CreateParentRequest createParentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ParentDetailsResponse> getParentById(String parentId) {
        ValidationChecks.validateParameter(parentId, PARENT_ID_PATH_VARIABLE_NAME, 6);
        ValidationChecks.validateParameterValue(parentId, PARENT_ID_PATH_VARIABLE_NAME);

        return null;
    }

    @Override
    public ResponseEntity<SuccessParentResponse> updateParentById(String parentId,
            UpdateParentRequest updateParentRequest) {
        ValidationChecks.validateParameter(parentId, PARENT_ID_PATH_VARIABLE_NAME, 6);
        ValidationChecks.validateParameterValue(parentId, PARENT_ID_PATH_VARIABLE_NAME);

        return null;
    }

}
