package org.ukdw.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project: SRM-BE
 * Package: com.srmbe.model.request.auth
 * <p>
 * Creator: dendy
 * Date: 8/5/2020
 * Time: 11:17 AM
 * <p>
 * Description : request dto for normal sign up. this type of sign up require no authcode.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 5, max = 40, message = "Password must be between 5 and 40 characters")
    private String password;

    @NotBlank(message = "Username is required")
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9._]{3,38}[A-Za-z0-9]$",
            message = "Username must be 5-40 characters long, start with a letter, and only contain letters, numbers, underscores, or dots. It cannot start or end with an underscore or dot."
    )
    private String username;

    @NotBlank(message = "Name is required")
    private String name;
    private String imageUrl;
    //registration number
    private String regNumber;

    private String studentId;
    private String teacherId;
    private String dayOfBirth;
    private String birthPlace;
    private String address;
    private String gender;
    private String registerYear;
    private String employmentNumber;
    private String urlGoogleScholar;
//    private String role;
}