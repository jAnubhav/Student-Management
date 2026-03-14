package org.anubhav.student_management;

import org.anubhav.student_management.utils.Constants;
import org.anubhav.student_management.utils.TestType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Tag(TestType.UNIT)
class StudentManagementApplicationTest {

    @Test
    void testMainInvokesSpringApplicationRun() {
        String[] args = new String[]{"--spring.profiles.active=test"};

        try (MockedStatic<SpringApplication> springApplicationMock = Mockito.mockStatic(SpringApplication.class)) {
            StudentManagementApplication.main(args);
            springApplicationMock.verify(() -> SpringApplication.run(StudentManagementApplication.class, args));
        }
    }

    @Test
    void testSpringBootApplicationAnnotationPresent() {
        Assertions.assertTrue(
                StudentManagementApplication.class.isAnnotationPresent(SpringBootApplication.class),
                Constants.EXCEPTION_NOT_THROWN.toString()
        );
    }

}
