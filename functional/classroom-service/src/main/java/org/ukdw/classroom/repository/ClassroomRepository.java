package org.ukdw.classroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ukdw.classroom.entity.ClassroomEntity;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long> {
}
