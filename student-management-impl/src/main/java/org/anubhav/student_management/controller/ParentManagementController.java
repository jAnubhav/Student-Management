package org.anubhav.student_management.controller;

import org.anubhav.api.ParentManagementInterface;
import org.anubhav.model.CreateParentRequest;
import org.anubhav.model.ParentAssigned;
import org.anubhav.model.ParentDetails;
import org.anubhav.model.ParentDetailsResponse;
import org.anubhav.model.SuccessParentResponse;
import org.anubhav.model.UpdateParentRequest;
import org.anubhav.student_management.exception.DependencyUnavailableException;
import org.anubhav.student_management.service.ParentManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * REST controller for parent endpoints.
 */
public class ParentManagementController implements ParentManagementInterface {

    private final ParentManagementService service;

    ParentManagementController(ParentManagementService service) {
        this.service = service;
    }

    /**
     * Creates a parent and returns assigned parent metadata.
     *
     * @param createParentRequest validated create-parent payload
     * @return successful response containing assigned parent details
     */
    @Override
    public ResponseEntity<SuccessParentResponse> createParent(CreateParentRequest createParentRequest) {
        ensureDependenciesAvailable();

        ParentAssigned parentDetails = service.createParent(createParentRequest);
        SuccessParentResponse response = new SuccessParentResponse(
                SuccessParentResponse.RequestStatusEnum.SUCCESS,
                parentDetails
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves parent details by parent ID.
     *
     * @param parentId unique parent identifier
     * @return successful response containing full parent details
     */
    @Override
    public ResponseEntity<ParentDetailsResponse> getParentById(String parentId) {
        ensureDependenciesAvailable();

        ParentDetails parentDetails = service.getParentById(parentId);
        ParentDetailsResponse response = new ParentDetailsResponse(
                ParentDetailsResponse.RequestStatusEnum.SUCCESS,
                parentDetails
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Updates parent details by parent ID.
     *
     * @param parentId unique parent identifier
     * @param updateParentRequest partial update payload
     * @return successful response containing assigned parent metadata
     */
    @Override
    public ResponseEntity<SuccessParentResponse> updateParentById(String parentId,
            UpdateParentRequest updateParentRequest) {
        ensureDependenciesAvailable();

        ParentAssigned parentDetails = service.updateParentById(parentId, updateParentRequest);
        SuccessParentResponse response = new SuccessParentResponse(
                SuccessParentResponse.RequestStatusEnum.SUCCESS,
                parentDetails
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Ensures required collaborators are available before processing.
     */
    private void ensureDependenciesAvailable() {
        if (service == null) {
            throw new DependencyUnavailableException("Parent service is unavailable.");
        }
    }

}
