package org.anubhav.student_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * Bootstrap class for the Student Management Spring Boot application.
 */
public class StudentManagementApplication {

    /**
     * Entry point for launching the application.
     *
     * @param args command-line arguments passed at startup
     */
    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }

}
