package org.ukdw.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;

/**
 * Creator: dendy
 * Date: 8/4/2020
 * Time: 1:59 PM
 * Description : taken from Table Teacher
 */
@Setter
@Getter
@Entity(name = "teacher")
public class TeacherEntity extends UserAccountEntity{

    @Column(name = "teacher_id", nullable = false)
    private String teacherId;

    @Column(name = "employment_number")
    String employment_number;

    @Column(name = "name")
    String name;

    @Column(name="gender")
    String gender;

    @Column(name="day_of_birth")
    String dayOfBirth;

    @Column(name="birth_place")
    String birthPlace;

    @Column(name="address")
    String address;

    @Column(name="input_date")
    Date inputDate;

    @Column(name = "url_google_scholar")
    String url_google_scholar;

    public TeacherEntity() {}

    public TeacherEntity(
            String username,
            String password,
            String regNumber,
            String email,
            String imageUrl,
            String teacherId,
            String employment_number,
            String name,
            String gender,
            String dayOfBirth,
            String birthPlace,
            String address,
            String url_google_scholar
    ){
        super(username, password, regNumber, email, imageUrl);
        this.teacherId = teacherId;
        this.employment_number = employment_number;
        this.name = name;
        this.gender = gender;
        this.dayOfBirth = dayOfBirth;
        this.birthPlace = birthPlace;
        this.address = address;
        this.url_google_scholar = url_google_scholar;
    }
}