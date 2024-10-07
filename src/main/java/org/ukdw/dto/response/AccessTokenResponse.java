package org.ukdw.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Project: SRM-BE
 * Package: com.srmbe.dto.response.googleapis
 * <p>
 * Creator: dendy
 * Date: 8/29/2020
 * Time: 7:52 AM
 * <p>
 * Description : AccessTokenResponse
 */

@Data
public class AccessTokenResponse {

    @JsonProperty("issued_to")
    private String issuedTo;

    private String audience;

    @JsonProperty("user_id")
    private String userId;

    private String scope;

    @JsonProperty("expires_in")
    private long expireIn;

    private String email;

    @JsonProperty("verified_email")
    private String verifiedEmail;

    @JsonProperty("access_type")
    private String accessType;
}
