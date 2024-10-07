package org.ukdw.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.ukdw.dto.user.UserRoleDTO;
import org.ukdw.entity.StudentEntity;
import org.ukdw.entity.TeacherEntity;
import org.ukdw.entity.UserAccountEntity;
import org.ukdw.repository.StudentRepository;
import org.ukdw.repository.TeacherRepository;
import org.ukdw.repository.UserAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//https://www.baeldung.com/spring-transactional-propagation-isolation
@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public List<UserAccountEntity> listUserAccount() {
//        List<TeacherEntity> teacherEntityList = teacherRepository.findAll();
//        List<StudentEntity> studentEntityList = studentRepository.findAll();
        return userAccountRepository.findAll();
    }

    @Transactional
    public void createUserAccount(UserAccountEntity userAccount) {
        userAccountRepository.save(userAccount);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAccountEntity findUserAccountById(Long id) {
        Optional<UserAccountEntity> person = userAccountRepository.findById(id);
        return person.orElse(null);
    }


}
