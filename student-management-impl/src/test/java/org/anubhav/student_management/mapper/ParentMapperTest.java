package org.anubhav.student_management.mapper;

import org.anubhav.model.CreateParentRequest;
import org.anubhav.model.ParentAssigned;
import org.anubhav.model.ParentDetails;
import org.anubhav.model.UpdateParentRequest;
import org.anubhav.student_management.entity.ParentEntity;
import org.anubhav.student_management.utils.Constants;
import org.anubhav.student_management.utils.TestType;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@Tag(TestType.UNIT)
class ParentMapperTest {

    private final ParentMapper mapper = Mappers.getMapper(ParentMapper.class);

    @Test
    void testToDtoReturnsNullWhenEntityIsNull() {
        Assertions.assertNull(mapper.toDto(null));
    }

    @Test
    void testToDtoMapsAllFields() {
        ParentEntity entity = getParentEntity();

        ParentDetails details = mapper.toDto(entity);
        Assertions.assertNotNull(details, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertEquals("220001", details.getParentId(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("Father Name", details.getFatherName(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals(
                "mother@mail.com",
                details.getMotherEmail(),
                Constants.PARENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals("201301", details.getZipcode(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
    }

    private static @NonNull ParentEntity getParentEntity() {
        ParentEntity entity = new ParentEntity();
        entity.setParentId(220001);
        entity.setFatherName("Father Name");
        entity.setFatherEmail("father@mail.com");
        entity.setFatherPhoneNumber("9876543210");
        entity.setMotherName("Mother Name");
        entity.setMotherEmail("mother@mail.com");
        entity.setMotherPhoneNumber("8765432109");
        entity.setAddress("Some Address");
        entity.setCity("Noida");
        entity.setState("UP");
        entity.setZipcode("201301");
        return entity;
    }

    @Test
    void testToDtoWhenParentIdMissing() {
        ParentEntity entity = new ParentEntity();
        ParentDetails details = mapper.toDto(entity);
        Assertions.assertNotNull(details, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertNull(details.getParentId());
    }

    @Test
    void testToEntityReturnsNullWhenRequestIsNull() {
        Assertions.assertNull(mapper.toEntity(null));
    }

    @Test
    void testToEntity() {
        CreateParentRequest request = new CreateParentRequest();
        request.setFatherName("Father Name");
        request.setFatherEmail("father@mail.com");
        request.setFatherPhoneNumber("9876543210");
        request.setAddress("Some Address");
        request.setCity("Noida");
        request.setState("UP");
        request.setZipcode("201301");

        ParentEntity entity = mapper.toEntity(request);
        Assertions.assertNotNull(entity, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertEquals("Father Name", entity.getFatherName(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals(
                "father@mail.com",
                entity.getFatherEmail(),
                Constants.PARENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals(
                "9876543210",
                entity.getFatherPhoneNumber(),
                Constants.PARENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals("Some Address", entity.getAddress(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("Noida", entity.getCity(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("UP", entity.getState(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("201301", entity.getZipcode(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
    }

    @Test
    void testToAssignedDtoReturnsNullWhenEntityIsNull() {
        Assertions.assertNull(mapper.toAssignedDto(null));
    }

    @Test
    void testToAssignedDtoWithParentId() {
        int parentId = 220001;
        ParentEntity entity = new ParentEntity();
        entity.setParentId(parentId);

        ParentAssigned assigned = mapper.toAssignedDto(entity);
        Assertions.assertNotNull(assigned, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertEquals(
                String.valueOf(parentId),
                assigned.getParentId(),
                Constants.PARENT_DETAILS_NOT_MATCH.toString()
        );
    }

    @Test
    void testToAssignedDtoWithoutParentId() {
        ParentEntity entity = new ParentEntity();
        ParentAssigned assigned = mapper.toAssignedDto(entity);
        Assertions.assertNotNull(assigned, Constants.ENTITY_NOT_NULL.toString());
        Assertions.assertNull(assigned.getParentId());
    }

    @Test
    void testUpdateParentFromDtoIgnoresNulls() {
        ParentEntity entity = new ParentEntity();
        entity.setFatherName("Old Name");
        entity.setCity("Old City");
        entity.setMotherName("Old Mother Name");

        UpdateParentRequest update = new UpdateParentRequest();

        mapper.updateParentFromDto(update, entity);

        Assertions.assertEquals("Old Name", entity.getFatherName(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("Old City", entity.getCity(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions
                .assertEquals("Old Mother Name", entity.getMotherName(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
    }

    @Test
    void testUpdateParentFromDtoWithAllFields() {
        ParentEntity entity = new ParentEntity();
        UpdateParentRequest update = new UpdateParentRequest();
        update.setAddress("Some Address");
        update.setCity("Noida");
        update.setState("UP");
        update.setZipcode("201301");
        update.setFatherName("Father Name");
        update.setFatherEmail("father@mail.com");
        update.setFatherPhoneNumber("9999999999");
        update.setMotherName("Mother Name");
        update.setMotherEmail("mother@mail.com");
        update.setMotherPhoneNumber("8888888888");
        mapper.updateParentFromDto(update, entity);

        Assertions.assertEquals("Some Address", entity.getAddress(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("Noida", entity.getCity(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("UP", entity.getState(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("201301", entity.getZipcode(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals("Father Name", entity.getFatherName(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals(
                "father@mail.com",
                entity.getFatherEmail(),
                Constants.PARENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals(
                "9999999999",
                entity.getFatherPhoneNumber(),
                Constants.PARENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals("Mother Name", entity.getMotherName(), Constants.PARENT_DETAILS_NOT_MATCH.toString());
        Assertions.assertEquals(
                "mother@mail.com",
                entity.getMotherEmail(),
                Constants.PARENT_DETAILS_NOT_MATCH.toString()
        );
        Assertions.assertEquals(
                "8888888888",
                entity.getMotherPhoneNumber(),
                Constants.PARENT_DETAILS_NOT_MATCH.toString()
        );
    }

    @Test
    void testUpdateParentFromDtoWithNullDtoDoesNothing() {
        ParentEntity entity = new ParentEntity();
        mapper.updateParentFromDto(null, entity);
        Assertions.assertNotNull(entity, Constants.ENTITY_NOT_NULL.toString());
    }

}
