package org.ukdw.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import org.springframework.http.HttpStatus;
import org.ukdw.dto.request.auth.*;
import org.ukdw.dto.response.RefreshAccessTokenResponse;
import org.ukdw.dto.response.ResponseWrapper;
import org.ukdw.entity.UserAccountEntity;
import org.ukdw.exception.InvalidTokenException;
import org.ukdw.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Creator: dendy
 * Date: 7/11/2020
 * Time: 12:52 PM
 * Description : auth controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
//@Api(tags = AUTH_SERVICE_TAG)
public class AuthController {

    private final AuthService authService;


    @ResponseBody
//    @ApiOperation(value = "Signin")
    @PostMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signin(
//            @ApiParam(name = "SignInRequest", value = "serverAuthCode=server authcode dari google, " +
//                    "clientType=mobile_app atau web_app", required = true)
            @RequestBody SignInRequest request) {
        UserAccountEntity userEntity = authService.signIn(request.getEmail(), request.getPassword());
        ResponseWrapper<UserAccountEntity> response = new ResponseWrapper<>(HttpStatus.OK.value(), userEntity);
        return ResponseEntity.ok(response);
    }

    @ResponseBody
//    @ApiOperation(value = "Meminta Acces Token")
    @PostMapping(value = "/refreshaccesstoken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshAccessTokenRequest request) throws InvalidTokenException {
        GoogleTokenResponse googleTokenResponse = authService.refreshAccessToken(request.getRefreshToken());
        RefreshAccessTokenResponse tokenResponse = new RefreshAccessTokenResponse(googleTokenResponse.getAccessToken(),googleTokenResponse.getIdToken());
        ResponseWrapper<RefreshAccessTokenResponse> response = new ResponseWrapper<>(HttpStatus.OK.value(), tokenResponse);
        return ResponseEntity.ok(response);
    }
}
