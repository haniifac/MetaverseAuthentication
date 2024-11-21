package org.ukdw.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukdw.authservice.entity.TeacherEntity;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
}
