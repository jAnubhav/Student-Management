package org.anubhav.student_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "parent")
@Data
@EqualsAndHashCode(callSuper = true)
public class ParentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "father_name", nullable = false, length = 40)
    private String fatherName;

    @Column(name = "father_email", nullable = false, length = 40)
    private String fatherEmail;

    @Column(name = "father_phone_number", nullable = false, length = 10, unique = true)
    private String fatherPhoneNumber;

    @Column(name = "mother_name", length = 40)
    private String motherName;

    @Column(name = "mother_email", length = 40)
    private String motherEmail;

    @Column(name = "mother_phone_number", length = 10, unique = true)
    private String motherPhoneNumber;

}
