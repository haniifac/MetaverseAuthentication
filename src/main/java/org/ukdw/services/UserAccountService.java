package org.ukdw.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.ukdw.dto.request.auth.SignUpRequest;
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
    public UserAccountEntity createUserAccount(UserAccountEntity userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Transactional
    public boolean deleteUserAccount(Long id){
        if (userAccountRepository.existsById(id)) {
            userAccountRepository.deleteById(id);
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

        if(user instanceof StudentEntity){
            StudentEntity student = (StudentEntity) user;
            if (updateRequest.getName() != null) {
                student.setName(updateRequest.getName());
            }
            if (updateRequest.getStudentId() != null) {
                student.setStudentId(updateRequest.getStudentId());
            }
            updatedUser = student;
        } else if (user instanceof TeacherEntity){
            TeacherEntity teacher = (TeacherEntity) user;
            if (updateRequest.getName() != null) {
                teacher.setName(updateRequest.getName());
            }
            if (updateRequest.getTeacherId() != null) {
                teacher.setTeacherId(updateRequest.getTeacherId());
            }
            updatedUser = teacher;
        }

        if (updateRequest.getEmail() != null) {
            user.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getPassword() != null) {
            user.setPassword(updateRequest.getPassword());
        }
        if (updateRequest.getUsername() != null) {
            user.setUsername(updateRequest.getUsername());
        }
//        if (updateRequest.getRegNumber() != null) {
//            user.setRegNumber(updateRequest.getRegNumber());
//        }
//        if (updateRequest.getImageUrl() != null) {
//            user.setImageUrl(updateRequest.getImageUrl());
//        }
//        if (updateRequest.getDayOfBirth() != null) {
//            user.setDayOfBirth(updateRequest.getDayOfBirth());
//        }
//        if (updateRequest.getBirthPlace() != null) {
//            user.setBirthPlace(updateRequest.getBirthPlace());
//        }
//        if (updateRequest.getAddress() != null) {
//            user.setAddress(updateRequest.getAddress());
//        }
//        if (updateRequest.getGender() != null) {
//            user.setGender(updateRequest.getGender());
//        }
//        if (updateRequest.getRegisterYear() != null) {
//            user.setRegisterYear(updateRequest.getRegisterYear());
//        }
//        if (updateRequest.getEmploymentNumber() != null) {
//            user.setEmploymentNumber(updateRequest.getEmploymentNumber());
//        }
//        if (updateRequest.getUrlGoogleScholar() != null) {
//            user.setUrlGoogleScholar(updateRequest.getUrlGoogleScholar());
//        }

        // Save the updated user entity
        userAccountRepository.save(updatedUser);
        return true;
    }


}
