package org.anubhav.student_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Data
public class BaseEntity {

    @Column(length = 100)
    private String address;

    @Column(length = 40)
    private String city;

    @Column(length = 40)
    private String state;

    @Column(length = 6)
    private String zipcode;

    @Column(name = "last_modified_at")
    @UpdateTimestamp
    private Date lastModifiedAt;

}
