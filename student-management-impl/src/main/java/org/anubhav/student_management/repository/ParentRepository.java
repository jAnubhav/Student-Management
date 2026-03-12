package org.anubhav.student_management.repository;

import org.anubhav.student_management.entity.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access repository for parent records.
 */
@Repository
public interface ParentRepository extends JpaRepository<ParentEntity, String> {
}
