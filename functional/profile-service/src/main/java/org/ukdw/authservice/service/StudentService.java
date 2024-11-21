package org.ukdw.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ukdw.authservice.entity.StudentEntity;
import org.ukdw.authservice.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Page<StudentEntity> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public StudentEntity findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public StudentEntity save(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public StudentEntity update(Long id, StudentEntity studentEntity) {
        StudentEntity student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.setFirstName(studentEntity.getFirstName());
            student.setLastName(studentEntity.getLastName());
            student.setNim(studentEntity.getNim());
            student.setPhoneNumber(studentEntity.getPhoneNumber());
            student.setAddress(studentEntity.getAddress());
            student.setCity(studentEntity.getCity());
            student.setRegion(studentEntity.getRegion());
            student.setCountry(studentEntity.getCountry());
            student.setZipCode(studentEntity.getZipCode());
            student.setGender(studentEntity.getGender());
            return studentRepository.save(student);
        }
        return null;
    }

}
