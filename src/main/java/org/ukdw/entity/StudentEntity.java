package org.ukdw.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import java.util.Date;

/**

 * Creator: dendy
 * Date: 8/4/2020
 * Time: 1:59 PM
 * Description : taken from Table Mahasiswa of srm rdb
 */
@Setter
@Getter
@Entity(name = "student")
public class StudentEntity extends UserAccountEntity{

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name="register_year",columnDefinition="char(4)")
    String registerYear;

    @Column(name="name")
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

    public StudentEntity() {}

    public StudentEntity(
            String username,
            String password,
            String regNumber,
            String email,
            String imageUrl,
            String studentId,
            String registerYear,
            String name,
            String gender,
            String dayOfBirth,
            String birthPlace,
            String address
    ) {
        super(username, password, regNumber, email, imageUrl);
        this.studentId = studentId;
        this.registerYear = registerYear;
        this.name = name;
        this.gender = gender;
        this.dayOfBirth = dayOfBirth;
        this.birthPlace = birthPlace;
        this.address = address;
    }
}