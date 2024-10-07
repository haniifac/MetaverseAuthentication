package org.ukdw.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String firstName;
    private String lastName;
    private String email;
    //registration number
    private String regnumber;
    private String password;

//    private String role;
}