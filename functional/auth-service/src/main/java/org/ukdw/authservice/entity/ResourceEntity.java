package org.ukdw.authservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Setter
@Getter
@Entity
@Table(name = "resource")
public class ResourceEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "RESOURCE_GEN")
//    @SequenceGenerator(name = "RESOURCE_GEN", sequenceName = "RESOURCE_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long resourceId;

    @Column(name = "resource_name", nullable = false,  unique = true)
    private String resourceName;

    @Column(name = "resource_bitmask", nullable = false, unique = true)
    private Long resourceBitmask;

}
