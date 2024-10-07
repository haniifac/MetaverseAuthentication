package org.ukdw.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String email;
    private String password;
}
