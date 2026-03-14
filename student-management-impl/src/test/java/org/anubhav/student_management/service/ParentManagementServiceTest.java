package org.anubhav.student_management.service;

import org.anubhav.model.CreateParentRequest;
import org.anubhav.model.ParentAssigned;
import org.anubhav.model.ParentDetails;
import org.anubhav.model.UpdateParentRequest;
import org.anubhav.student_management.entity.ParentEntity;
import org.anubhav.student_management.exception.DependencyUnavailableException;
import org.anubhav.student_management.exception.NotFoundException;
import org.anubhav.student_management.mapper.ParentMapper;
import org.anubhav.student_management.repository.ParentRepository;
import org.anubhav.student_management.utils.Constants;
import org.anubhav.student_management.utils.TestType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

@Tag(TestType.UNIT)
@ExtendWith(MockitoExtension.class)
class ParentManagementServiceTest {

    private final CreateParentRequest createParentRequest = new CreateParentRequest();
    private final UpdateParentRequest updateParentRequest = new UpdateParentRequest();
    private final ParentEntity parentEntity = new ParentEntity();
    private final ParentDetails parentDetails = new ParentDetails();
    private final ParentAssigned parentAssigned = new ParentAssigned();

    @Mock
    private ParentRepository repository;

    @Mock
    private ParentMapper mapper;

    @InjectMocks
    private ParentManagementService service;

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testCreateOrUpdateParent(boolean createOperation) {
        Mockito.when(repository.save(parentEntity)).thenReturn(parentEntity);
        Mockito.when(mapper.toAssignedDto(parentEntity)).thenReturn(parentAssigned);

        ParentAssigned response;
        if (createOperation) {
            Mockito.when(mapper.toEntity(createParentRequest)).thenReturn(parentEntity);
            response = service.createParent(createParentRequest);
        } else {
            Mockito.when(repository.findById("220001")).thenReturn(Optional.of(parentEntity));
            response = service.updateParentById("220001", updateParentRequest);
        }

        Assertions.assertNotNull(response, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertEquals(parentAssigned, response, Constants.PARENT_DETAILS_NOT_MATCH.toString());
        if (!createOperation) {
            Mockito.verify(mapper).updateParentFromDto(updateParentRequest, parentEntity);
        }
    }

    @Test
    void testGetParentById() {
        Mockito.when(repository.findById("220001")).thenReturn(Optional.of(parentEntity));
        Mockito.when(mapper.toDto(parentEntity)).thenReturn(parentDetails);

        ParentDetails response = service.getParentById("220001");
        Assertions.assertNotNull(response, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertEquals(parentDetails, response, Constants.PARENT_DETAILS_NOT_MATCH.toString());
    }

    @Test
    void testGetParentByIdNotFound() {
        Mockito.when(repository.findById("220001")).thenReturn(Optional.empty());
        Assertions.assertThrows(
                NotFoundException.class,
                () -> service.getParentById("220001"),
                Constants.EXCEPTION_NOT_THROWN.toString()
        );
    }

    @ParameterizedTest
    @MethodSource("provideNullDependencies")
    void testParentServiceWhenDependenciesNull(boolean isRepositoryNull, boolean isMapperNull) {
        ParentManagementService nullDependencyService = new ParentManagementService(
                isRepositoryNull ? null : repository,
                isMapperNull ? null : mapper
        );
        Assertions.assertThrows(
                DependencyUnavailableException.class,
                () -> nullDependencyService.getParentById("220001"),
                Constants.EXCEPTION_NOT_THROWN.toString()
        );
    }

    private static Stream<Arguments> provideNullDependencies() {
        return Stream.of(Arguments.of(false, true), Arguments.of(true, false), Arguments.of(true, true));
    }

}
