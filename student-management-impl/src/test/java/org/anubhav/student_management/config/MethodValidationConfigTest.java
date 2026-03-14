package org.anubhav.student_management.config;

import org.anubhav.student_management.utils.Constants;
import org.anubhav.student_management.utils.TestType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Tag(TestType.UNIT)
class MethodValidationConfigTest {

    @Test
    void testMethodValidationPostProcessorBean() {
        MethodValidationPostProcessor processor = MethodValidationConfig.methodValidationPostProcessor();
        Assertions.assertNotNull(processor, Constants.ENTITY_NOT_NULL.toString());
    }

    @Test
    void testConfigurationAnnotationPresent() {
        Assertions.assertTrue(
                MethodValidationConfig.class.isAnnotationPresent(Configuration.class),
                Constants.EXCEPTION_NOT_THROWN.toString()
        );
    }

}
