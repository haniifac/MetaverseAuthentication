package org.ukdw.dto.response;

import lombok.Data;

/**
 * Project: SRM-BE
 * Package: com.srmbe.dto.response.auth
 * <p>
 * Creator: dendy
 * Date: 7/11/2020
 * Time: 12:52 PM
 * <p>
 * Description : refresh access token response
 */

@Data
public class RefreshAccessTokenResponse {
    private String accessToken;
    private String idToken;

    public RefreshAccessTokenResponse(String accessToken, String idToken) {
        this.accessToken = accessToken;
        this.idToken = idToken;
    }
}
