package org.ukdw.authservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    private String firstName;
    private String lastName;
    private String nid;
    private String phoneNumber;
    private String address;
    private String city;
    private String region;
    private String country;
    private String zipCode;
    private String gender;
    private String googleScholar;
}
