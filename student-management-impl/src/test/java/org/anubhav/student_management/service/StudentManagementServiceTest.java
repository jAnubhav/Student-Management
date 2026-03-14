package org.anubhav.student_management.service;

import org.anubhav.model.CreateStudentRequest;
import org.anubhav.model.ParentDetails;
import org.anubhav.model.StudentAssigned;
import org.anubhav.model.StudentDetails;
import org.anubhav.model.UpdateStudentRequest;
import org.anubhav.student_management.entity.StudentEntity;
import org.anubhav.student_management.exception.DependencyUnavailableException;
import org.anubhav.student_management.exception.NotFoundException;
import org.anubhav.student_management.mapper.StudentMapper;
import org.anubhav.student_management.repository.StudentRepository;
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
class StudentManagementServiceTest {

    private final CreateStudentRequest createStudentRequest = new CreateStudentRequest();
    private final UpdateStudentRequest updateStudentRequest = new UpdateStudentRequest();
    private final StudentEntity studentEntity = new StudentEntity();
    private final StudentDetails studentDetails = new StudentDetails();
    private final StudentAssigned studentAssigned = new StudentAssigned();
    private final ParentDetails parentDetails = new ParentDetails();

    @Mock
    private ParentManagementService parentManagementService;

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentMapper mapper;

    @InjectMocks
    private StudentManagementService service;

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testCreateOrUpdateStudent(boolean createOperation) {
        Mockito.when(repository.save(studentEntity)).thenReturn(studentEntity);
        Mockito.when(mapper.toAssignedDto(studentEntity)).thenReturn(studentAssigned);

        StudentAssigned response;
        if (createOperation) {
            Mockito.when(mapper.toEntity(createStudentRequest)).thenReturn(studentEntity);
            response = service.createStudent(createStudentRequest);
        } else {
            Mockito.when(repository.findById("220001")).thenReturn(Optional.of(studentEntity));
            response = service.updateStudentById("220001", updateStudentRequest);
        }
        Assertions.assertNotNull(response, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertEquals(studentAssigned, response, Constants.STUDENT_ASSIGNED_NOT_MATCH.toString());
        if (!createOperation) {
            Mockito.verify(mapper).updateStudentFromDto(updateStudentRequest, studentEntity);
        }
    }

    @Test
    void testGetStudentById() {
        studentEntity.setParentId(220001);
        Mockito.when(repository.findById("220001")).thenReturn(Optional.of(studentEntity));
        Mockito.when(parentManagementService.getParentById("220001")).thenReturn(parentDetails);
        Mockito.when(mapper.toDto(studentEntity)).thenReturn(studentDetails);

        StudentDetails response = service.getStudentById("220001");
        Assertions.assertNotNull(response, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertEquals(
                studentDetails.parentDetails(parentDetails),
                response,
                Constants.STUDENT_DETAILS_NOT_MATCH.toString()
        );
    }

    @Test
    void testGetStudentByIdNotFound() {
        Mockito.when(repository.findById("220001")).thenReturn(Optional.empty());
        Assertions.assertThrows(
                NotFoundException.class,
                () -> service.getStudentById("220001"),
                Constants.EXCEPTION_NOT_THROWN.toString()
        );
    }

    @ParameterizedTest
    @MethodSource("provideNullDependencies")
    void testStudentServiceWhenDependenciesNull(boolean parentServiceNull, boolean repositoryNull, boolean mapperNull) {
        StudentManagementService nullDependencyService = new StudentManagementService(
                parentServiceNull ? null : parentManagementService,
                repositoryNull ? null : repository,
                mapperNull ? null : mapper
        );
        Assertions.assertThrows(
                DependencyUnavailableException.class,
                () -> nullDependencyService.getStudentById("220001"),
                Constants.EXCEPTION_NOT_THROWN.toString()
        );
    }

    private static Stream<Arguments> provideNullDependencies() {
        return Stream.of(
                Arguments.of(true, false, false),
                Arguments.of(false, true, false),
                Arguments.of(false, false, true)
        );
    }

}
