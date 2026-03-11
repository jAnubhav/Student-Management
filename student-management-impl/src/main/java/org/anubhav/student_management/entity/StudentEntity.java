package org.anubhav.student_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "student")
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_number")
    private Integer enrollmentNumber;

    @CreationTimestamp
    @Column(name = "date_of_enrollment", nullable = false)
    private Date dateOfEnrollment;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(name = "phone_number", length = 10, nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 29)
    private String department;

    @Column(nullable = false, length = 1)
    private String gender;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false, length = 11)
    private String status;

    @Column(name = "parent_id", nullable = false)
    private Integer parentId;

    @PrePersist
    public void setDefaultStatus() {
        status = "ACTIVE";
    }

}
