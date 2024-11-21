package org.ukdw.classroom.util;

import org.ukdw.classroom.dto.classroom.ClassroomDetailDTO;
import org.ukdw.classroom.dto.classroom.ClassroomPublicDTO;
import org.ukdw.classroom.entity.ClassroomEntity;

public class ClassroomMapper {

    public static ClassroomPublicDTO toPublicDTO(ClassroomEntity entity) {
        return new ClassroomPublicDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getTahunAjaran(),
                entity.getSemester()
        );
    }

    public static ClassroomDetailDTO toDetailDTO(ClassroomEntity entity) {
        return new ClassroomDetailDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getTahunAjaran(),
                entity.getSemester(),
                entity.getTeacherIds(),
                entity.getStudentIds()
        );
    }
}
