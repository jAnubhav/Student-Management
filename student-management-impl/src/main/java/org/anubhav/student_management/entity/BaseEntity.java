package org.anubhav.student_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Data
public class BaseEntity {

    private String address;

    private String city;

    private String state;

    @Column(length = 6)
    private String zipcode;

    @Column(name = "last_modified_at")
    @UpdateTimestamp
    private Date lastModifiedAt;

}
