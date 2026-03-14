package org.anubhav.student_management.mapper;

import org.anubhav.model.CreateStudentRequest;
import org.anubhav.model.ParentDetails;
import org.anubhav.model.StudentAssigned;
import org.anubhav.model.StudentDetails;
import org.anubhav.model.UpdateStudentRequest;
import org.anubhav.student_management.entity.StudentEntity;
import org.anubhav.student_management.utils.Constants;
import org.anubhav.student_management.utils.TestType;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Method;
import java.util.Date;

@Tag(TestType.UNIT)
class StudentMapperTest {

    private final StudentMapper mapper = Mappers.getMapper(StudentMapper.class);

    @Test
    void testToDtoReturnsNullWhenEntityIsNull() {
        Assertions.assertNull(mapper.toDto(null));
    }

    @Test
    void testToDtoMapsAllFields() {
        StudentEntity entity = getStudentEntity();

        StudentDetails details = mapper.toDto(entity);
        Assertions.assertNotNull(details, Constants.ENTITY_NOT_NULL.toString());
        Assertions
                .assertEquals("220001", details.getEnrollmentNumber(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        assert details.getParentDetails() != null;
        Assertions.assertEquals(
                "220002",
                details.getParentDetails().getParentId(),
                Constants.STUDENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals(
                StudentDetails.StatusEnum.ACTIVE,
                details.getStatus(),
                Constants.STUDENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals(
                StudentDetails.DepartmentEnum.COMPUTER_SCIENCE,
                details.getDepartment(),
                Constants.STUDENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals(
                StudentDetails.GenderEnum.M,
                details.getGender(),
                Constants.STUDENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals("Arjun", details.getFirstName(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("Sharma", details.getLastName(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("arjun@mail.com", details.getEmail(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("9876543210", details.getPhoneNumber(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("Some Address", details.getAddress(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
    }

    private static @NonNull StudentEntity getStudentEntity() {
        Date now = new Date();
        StudentEntity entity = new StudentEntity();
        entity.setEnrollmentNumber(220001);
        entity.setParentId(220002);
        entity.setStatus("ACTIVE");
        entity.setDepartment("COMPUTER_SCIENCE");
        entity.setGender("M");
        entity.setFirstName("Arjun");
        entity.setLastName("Sharma");
        entity.setEmail("arjun@mail.com");
        entity.setPhoneNumber("9876543210");
        entity.setAddress("Some Address");
        entity.setCity("Noida");
        entity.setState("UP");
        entity.setZipcode("201301");
        entity.setDateOfBirth(now);
        entity.setDateOfEnrollment(now);
        entity.setLastModifiedAt(now);
        return entity;
    }

    @Test
    void testToDtoHandlesNullableOptionalFields() {
        StudentEntity entity = new StudentEntity();
        entity.setFirstName("Arjun");

        StudentDetails details = mapper.toDto(entity);
        Assertions.assertNotNull(details, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertNull(details.getEnrollmentNumber());
        Assertions.assertNull(details.getStatus());
        Assertions.assertNull(details.getDepartment());
        Assertions.assertNull(details.getGender());
        Assertions.assertNotNull(details.getParentDetails(), Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertNull(details.getParentDetails().getParentId());
    }

    @Test
    void testToEntityReturnsNullWhenRequestIsNull() {
        Assertions.assertNull(mapper.toEntity(null));
    }

    @Test
    void testToEntity() {
        CreateStudentRequest request = getCreateStudentRequest();

        StudentEntity entity = mapper.toEntity(request);
        Assertions.assertNotNull(entity, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertEquals("Arjun", entity.getFirstName(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("Sharma", entity.getLastName(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("arjun@mail.com", entity.getEmail(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("9876543210", entity.getPhoneNumber(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals(220001, entity.getParentId(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals(
                "COMPUTER_SCIENCE",
                entity.getDepartment(),
                Constants.STUDENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals("M", entity.getGender(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
    }

    private static @NonNull CreateStudentRequest getCreateStudentRequest() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("Arjun");
        request.setLastName("Sharma");
        request.setEmail("arjun@mail.com");
        request.setPhoneNumber("9876543210");
        request.setAddress("Some Address");
        request.setCity("Noida");
        request.setState("UP");
        request.setZipcode("201301");
        request.setParentId("220001");
        request.setDepartment(CreateStudentRequest.DepartmentEnum.COMPUTER_SCIENCE);
        request.setGender(CreateStudentRequest.GenderEnum.M);
        return request;
    }

    @Test
    void testToEntityWithoutOptionalEnumAndParentId() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("Arjun");
        request.setLastName("Sharma");
        request.setEmail("arjun@mail.com");
        request.setPhoneNumber("9876543210");
        StudentEntity entity = mapper.toEntity(request);
        Assertions.assertNotNull(entity, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertNull(entity.getDepartment());
        Assertions.assertNull(entity.getGender());
        Assertions.assertNull(entity.getParentId());
    }

    @Test
    void testToAssignedDtoReturnsNullWhenEntityIsNull() {
        Assertions.assertNull(mapper.toAssignedDto(null));
    }

    @Test
    void testToAssignedDto() {
        int enrollmentNumber = 220001;
        Date now = new Date();
        StudentEntity entity = new StudentEntity();
        entity.setEnrollmentNumber(enrollmentNumber);
        entity.setDateOfEnrollment(now);

        StudentAssigned assigned = mapper.toAssignedDto(entity);
        Assertions.assertNotNull(assigned, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertEquals(
                String.valueOf(enrollmentNumber),
                assigned.getEnrollmentNumber(),
                Constants.STUDENT_ASSIGNED_NOT_MATCH.toString()
        );
        Assertions.assertEquals(now, assigned.getDateOfEnrollment(), Constants.STUDENT_ASSIGNED_NOT_MATCH.toString());
    }

    @Test
    void testToAssignedDtoWhenEnrollmentNumberMissing() {
        StudentEntity entity = new StudentEntity();
        StudentAssigned assigned = mapper.toAssignedDto(entity);
        Assertions.assertNotNull(assigned, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertNull(assigned.getEnrollmentNumber());
    }

    @Test
    void testUpdateStudentFromDtoIgnoresNulls() {
        StudentEntity entity = new StudentEntity();
        entity.setFirstName("Old Name");
        entity.setCity("Old City");
        entity.setStatus("ACTIVE");

        UpdateStudentRequest update = new UpdateStudentRequest();

        mapper.updateStudentFromDto(update, entity);

        Assertions.assertEquals("Old Name", entity.getFirstName(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("Old City", entity.getCity(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("ACTIVE", entity.getStatus(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
    }

    @Test
    void testUpdateStudentFromDtoWithAllFields() {
        Date now = new Date();
        StudentEntity entity = new StudentEntity();
        UpdateStudentRequest update = getUpdateStudentRequest(now);
        mapper.updateStudentFromDto(update, entity);

        Assertions.assertEquals("Address", entity.getAddress(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("City", entity.getCity(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("State", entity.getState(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("201301", entity.getZipcode(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("First", entity.getFirstName(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("Last", entity.getLastName(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("mail@mail.com", entity.getEmail(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("9999999999", entity.getPhoneNumber(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals(
                "COMPUTER_SCIENCE",
                entity.getDepartment(),
                Constants.STUDENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals("F", entity.getGender(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals(now, entity.getDateOfBirth(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("GRADUATED", entity.getStatus(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
    }

    private static @NonNull UpdateStudentRequest getUpdateStudentRequest(Date now) {
        UpdateStudentRequest update = new UpdateStudentRequest();
        update.setAddress("Address");
        update.setCity("City");
        update.setState("State");
        update.setZipcode("201301");
        update.setFirstName("First");
        update.setLastName("Last");
        update.setEmail("mail@mail.com");
        update.setPhoneNumber("9999999999");
        update.setDepartment(UpdateStudentRequest.DepartmentEnum.COMPUTER_SCIENCE);
        update.setGender(UpdateStudentRequest.GenderEnum.F);
        update.setDateOfBirth(now);
        update.setStatus(UpdateStudentRequest.StatusEnum.GRADUATED);
        return update;
    }

    @Test
    void testUpdateStudentFromDtoWithNullDtoDoesNothing() {
        StudentEntity entity = new StudentEntity();
        mapper.updateStudentFromDto(null, entity);
        Assertions.assertNotNull(entity, Constants.ENTITY_NOT_NULL.toString());
    }

    @Test
    void testStudentEntityToParentDetailsBranching() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("studentEntityToParentDetails", StudentEntity.class);
        method.setAccessible(true);

        Assertions.assertNull(method.invoke(mapper, new Object[]{null}));

        ParentDetails noParentId = (ParentDetails) method.invoke(mapper, new StudentEntity());
        Assertions.assertNotNull(noParentId, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertNull(noParentId.getParentId());

        StudentEntity entity = new StudentEntity();
        entity.setParentId(220002);
        ParentDetails mapped = (ParentDetails) method.invoke(mapper, entity);
        Assertions.assertEquals("220002", mapped.getParentId(), Constants.STUDENT_DETAILS_NOT_MATCH.toString());
    }

}
