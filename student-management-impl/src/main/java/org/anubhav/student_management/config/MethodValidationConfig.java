package org.anubhav.student_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Registers method-level validation support for @Validated beans.
 */
@Configuration
public class MethodValidationConfig {

    /**
     * Creates a MethodValidationPostProcessor to enforce Bean Validation on method
     * parameters. This enables @Validated method parameter checks at runtime.
     *
     * @return configured method validation post-processor
     */
    @Bean
    public static MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
