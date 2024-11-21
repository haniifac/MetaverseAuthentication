package org.ukdw.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ukdw.authservice.entity.TeacherEntity;
import org.ukdw.authservice.repository.TeacherRepository;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public Page<TeacherEntity> findAll(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    public TeacherEntity findById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public TeacherEntity save(TeacherEntity teacherEntity) {
        return teacherRepository.save(teacherEntity);
    }

    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }

    public TeacherEntity update(Long id, TeacherEntity teacherEntity) {
        TeacherEntity teacher = teacherRepository.findById(id).orElse(null);
        if (teacher != null) {
            teacher.setFirstName(teacherEntity.getFirstName());
            teacher.setLastName(teacherEntity.getLastName());
            teacher.setNid(teacherEntity.getNid());
            teacher.setAddress(teacherEntity.getAddress());
            teacher.setPhoneNumber(teacherEntity.getPhoneNumber());
            teacher.setRegion(teacherEntity.getRegion());
            teacher.setCity(teacherEntity.getCity());
            teacher.setCountry(teacherEntity.getCountry());
            teacher.setZipCode(teacherEntity.getZipCode());
            teacher.setGender(teacherEntity.getGender());
            teacher.setGoogleScholar(teacherEntity.getGoogleScholar());
            return teacherRepository.save(teacher);
        }
        return null;
    }

}
