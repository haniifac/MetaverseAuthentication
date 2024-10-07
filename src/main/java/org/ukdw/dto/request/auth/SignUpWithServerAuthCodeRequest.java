package org.ukdw.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Project: SRM-BE
 * Package: com.srmbe.model.request.auth
 * <p>
 * Creator: dendy
 * Date: 8/5/2020
 * Time: 11:17 AM
 * <p>
 * Description : request dto for sign up
 */
@Data
public class SignUpWithServerAuthCodeRequest {

    @JsonProperty("serverAuthCode")
    private String serverAuthCode;

    //registration number
    @JsonProperty("regNumber")
    private String regNumber;

    @JsonProperty("role")
    private String role;

    @JsonProperty("clientType")
    private String clientType;
}


