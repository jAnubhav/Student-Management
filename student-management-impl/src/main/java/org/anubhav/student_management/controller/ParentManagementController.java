package org.anubhav.student_management.controller;

import org.anubhav.api.ParentManagementInterface;
import org.anubhav.model.CreateParentRequest;
import org.anubhav.model.ParentDetailsResponse;
import org.anubhav.model.SuccessParentResponse;
import org.anubhav.model.UpdateParentRequest;

import org.anubhav.student_management.utility.ValidationChecks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParentManagementController implements ParentManagementInterface {

    @Override
    public ResponseEntity<SuccessParentResponse> createParent(CreateParentRequest createParentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ParentDetailsResponse> getParentById(String parentId) {
        ValidationChecks.validateParameter(parentId, "parentId", 6);
        return null;
    }

    @Override
    public ResponseEntity<SuccessParentResponse> updateParentById(String parentId,
            UpdateParentRequest updateParentRequest) {
        ValidationChecks.validateParameter(parentId, "parentId", 6);
        return null;
    }

}
