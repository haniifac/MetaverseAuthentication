package com.virtualclassroom.authrestapi.repository;

import com.virtualclassroom.authrestapi.entities.Role;
import com.virtualclassroom.authrestapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByRole(Role role);

}
