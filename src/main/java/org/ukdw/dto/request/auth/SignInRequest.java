package org.ukdw.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Project: SRM-BE
 * Package: com.srmbe.model.request.auth
 * <p>
 * Creator: dendy
 * Date: 7/11/2020
 * Time: 12:55 PM
 * <p>
 * Description : AuthRequest
 */
@Setter
@Getter
public class SignInRequest {
//    @JsonProperty("serverAuthCode")
//    private String serverAuthCode;
//@JsonProperty("clientType")
//private String clientType;
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "email is required")
    private String password;
}
