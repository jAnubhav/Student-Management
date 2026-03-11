package org.anubhav.student_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
/**
 * Registers method-level validation support for @Validated beans.
 */
public class MethodValidationConfig {

    /**
     * Spring Boot 4.0 does not autoconfigure MethodValidationPostProcessor (no
     * ValidationAutoConfiguration exists in any Spring Boot 4.0 module). Without
     * this bean, @Validated on controllers has no effect — no AOP proxy is created
     * and @Pattern/@Size constraints on generated interface method parameters are
     * never enforced by the framework.
     * <p>
     * Registering this bean causes FilteredMethodValidationPostProcessor to create
     * a CGLIB proxy for every @Validated bean, so constraint violations throw
     * ConstraintViolationException before the method body is reached.
     */
    @Bean
    /**
     * Creates a MethodValidationPostProcessor to enforce Bean Validation on method parameters.
     *
     * @return configured method validation post-processor
     */
    public static MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
