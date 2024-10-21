/**
 * Author: dendy
 * Date:26/09/2024
 * Time:8:38
 * Description:
 */

package org.ukdw.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ukdw.entity.UserAccountEntity;

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
