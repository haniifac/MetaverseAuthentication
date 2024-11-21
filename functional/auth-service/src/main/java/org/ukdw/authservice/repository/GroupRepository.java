package org.ukdw.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ukdw.authservice.entity.GroupEntity;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    GroupEntity findByGroupname(String groupname);
}