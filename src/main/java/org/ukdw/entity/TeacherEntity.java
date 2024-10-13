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
}