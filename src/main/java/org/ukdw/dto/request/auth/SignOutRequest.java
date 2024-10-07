package org.ukdw.dto.request.auth;

import lombok.Data;

/**
 * Project: SRM-BE
 * Package: com.srmbe.model.request.auth
 * <p>
 * Creator: dendy
 * Date: 8/5/2020
 * Time: 11:17 AM
 * <p>
 * Description : request dto sign out
 */
@Data
public class SignOutRequest {

    private String accessToken;
}