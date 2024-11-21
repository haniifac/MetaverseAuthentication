package org.ukdw.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukdw.authservice.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
