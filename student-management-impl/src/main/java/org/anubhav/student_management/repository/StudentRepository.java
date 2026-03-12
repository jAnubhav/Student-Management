package org.anubhav.student_management.repository;

import org.anubhav.student_management.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access repository for student records.
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String> {
}
