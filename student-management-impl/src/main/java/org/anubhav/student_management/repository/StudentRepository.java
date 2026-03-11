package org.anubhav.student_management.repository;

import org.anubhav.student_management.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * Data-access repository for student records.
 */
public interface StudentRepository extends JpaRepository<StudentEntity, String> {
}
