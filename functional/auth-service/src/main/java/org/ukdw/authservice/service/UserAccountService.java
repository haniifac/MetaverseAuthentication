package org.ukdw.authservice.service;


import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.ukdw.authservice.dto.SignUpRequest;
import org.ukdw.authservice.entity.UserAccountEntity;
import org.ukdw.authservice.repository.UserAccountRepository;
import org.ukdw.common.exception.RequestParameterErrorException;
//import org.ukdw.authservice.dto.user.UserRoleDTO;
//import org.ukdw.authservice.entity.GroupEntity;
//import org.ukdw.authservice.entity.StudentEntity;
//import org.ukdw.authservice.entity.TeacherEntity;
//import org.ukdw.authservice.repository.GroupRepository;
//import org.ukdw.authservice.repository.StudentRepository;
//import org.ukdw.authservice.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;

//https://www.baeldung.com/spring-transactional-propagation-isolation
@Service
@RequiredArgsConstructor
public class UserAccountService {

    private static final Logger log = LogManager.getLogger(UserAccountService.class);
    private final UserAccountRepository userAccountRepository;

    @Transactional
    public List<UserAccountEntity> listUserAccount() {
        return userAccountRepository.findAll();
    }

    @Transactional
    public UserAccountEntity createUserAccount(UserAccountEntity userAccount) {
        try{
            var newAccount = userAccountRepository.save(userAccount);
            return newAccount;
        }catch (DataIntegrityViolationException ex){
            throw new RequestParameterErrorException("A User with the same email or username already exists.");
        }
    }

    @Transactional
    public boolean deleteUserAccount(Long id){
        // Find the user
        Optional<UserAccountEntity> userOpt = userAccountRepository.findById(id);

        if(userOpt.isPresent()){
            UserAccountEntity user = userOpt.get();

            // Now delete the user
            userAccountRepository.delete(user);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAccountEntity findUserAccountById(Long id) {
        Optional<UserAccountEntity> person = userAccountRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public boolean updateUserAccount(Long id, SignUpRequest updateRequest){
        Optional<UserAccountEntity> userOpt = userAccountRepository.findById(id);

        if (userOpt.isEmpty()) {
            return false;
        }

        UserAccountEntity user = userOpt.get();
        UserAccountEntity updatedUser = user;

        if (updateRequest.getEmail() != null) {
            user.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getPassword() != null) {
            user.setPassword(updateRequest.getPassword());
        }
        if (updateRequest.getUsername() != null) {
            user.setUsername(updateRequest.getUsername());
        }

        // Save the updated user entity
        userAccountRepository.save(updatedUser);
        return true;
    }
}

