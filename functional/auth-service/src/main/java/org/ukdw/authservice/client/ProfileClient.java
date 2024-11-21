package org.ukdw.authservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.ukdw.authservice.dto.StudentRequestDto;
import org.ukdw.authservice.dto.TeacherRequestDto;

@Component
@RequiredArgsConstructor
public class ProfileClient {

    @Value("${profile.service.student-path}")
    private String studentPath;

    @Value("${profile.service.teacher-path}")
    private String teacherPath;

    private final RestTemplate restTemplate;

    public void createStudent(StudentRequestDto studentRequestDto) {
        restTemplate.postForEntity(studentPath, studentRequestDto, Void.class);
    }

    public void createTeacher(TeacherRequestDto teacherRequestDto) {
        restTemplate.postForEntity(teacherPath, teacherRequestDto, Void.class);
    }
}
