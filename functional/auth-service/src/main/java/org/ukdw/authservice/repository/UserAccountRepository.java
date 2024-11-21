package org.ukdw.authservice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ukdw.authservice.entity.UserAccountEntity;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {
    @EntityGraph(attributePaths = {"groups"})
    Optional<UserAccountEntity> findByUsername(String username);

    @EntityGraph(attributePaths = {"groups"})
    UserAccountEntity findByEmail(String email);

    @EntityGraph(attributePaths = {"groups"})
    UserAccountEntity findByEmailAndPassword(String email, String password);
}
