/**
 * Author: dendy
 * Date:17/09/2024
 * Time:12:49
 * Description: a class for verify token from google service.
 */

package org.ukdw.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.ukdw.config.AppProperties;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import org.ukdw.exception.InvalidTokenException;
//import org.ukdw.exception.OAuth2AuthenticationProcessingException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class GoogleTokenVerifier {
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new GsonFactory();
    private static final Logger logger = LoggerFactory.getLogger(GoogleTokenVerifier.class);
    private final AppProperties appProperties;

    public Payload verifyIdToken(String idTokenString)
            throws InvalidTokenException {

        final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.
                Builder(HTTP_TRANSPORT, JSON_FACTORY)
                .setIssuers(Arrays.asList("https://accounts.google.com", "accounts.google.com"))
                .setAudience(Collections.singletonList(appProperties.getOauth2().getGoogleClientId()))
                .build();
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            logger.debug("validating:" + idTokenString);
            if (idToken == null) {
                throw new InvalidTokenException("idToken is outdated");
            }
            return idToken.getPayload();
        } catch (IllegalArgumentException | GeneralSecurityException | IOException e) {
            // means token was not valid and idToken
            throw new InvalidTokenException("idToken is invalid");
        }
    }

    /*

    https://cloud.google.com/java/docs/reference/google-api-client/latest/com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
    */
    public GoogleTokenResponse verifyAuthCode(String authCode, String clientType)
            throws IOException {
        String redirectUri = "";
        if (clientType.equalsIgnoreCase(Constant.WEB_APP)) {
            //https://stackoverflow.com/questions/74189161/google-identity-services-sdk-authorization-code-model-in-popup-mode-how-to-r
            redirectUri = "postmessage";
        }
        GoogleTokenResponse tokenResponse =
                new GoogleAuthorizationCodeTokenRequest(
                        HTTP_TRANSPORT,
                        JSON_FACTORY,
                        "https://oauth2.googleapis.com/token",
                        appProperties.getOauth2().getGoogleClientId(),
                        appProperties.getOauth2().getGoogleSecret(),
                        authCode,
                        redirectUri)
                        .execute();
        return tokenResponse;
    }

    public GoogleTokenResponse refreshAccessToken(String refreshToken) throws InvalidTokenException {
        try {
            GoogleTokenResponse response =
                    new GoogleRefreshTokenRequest(HTTP_TRANSPORT, JSON_FACTORY,
                            refreshToken,
                            appProperties.getOauth2().getGoogleClientId(),
                            appProperties.getOauth2().getGoogleSecret()).execute();
            return response;
        } catch (TokenResponseException e) {
            throw new InvalidTokenException("Token is invalid");
        } catch (IOException e) {
//            throw new OAuth2AuthenticationProcessingException("Something wrong on I/O!");
            throw new RuntimeException(e);
        }
    }

    public static void verifyGoogleAccessToken(String accessToken, String audience, String jwkUrl) {
//        String jwkUrl = "https://www.googleapis.com/oauth2/v1/tokeninfo";
    }

    public Credential getGoogleCredential(String accessToken) throws IOException {
        /*
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JACKSON_FACTORY,Path to credential.json);
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT,
                        JACKSON_FACTORY,
                        clientSecrets,
                        Arrays.asList("https://www.googleapis.com/auth/calendar"))
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow,
                new LocalServerReceiver()).authorize("user");*/

        //

        GoogleCredential googleCredential = new GoogleCredential.Builder()
                .setClientSecrets(appProperties.getOauth2().getGoogleClientId(),
                        appProperties.getOauth2().getGoogleSecret())
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .build();
        googleCredential.setAccessToken(accessToken);
        return googleCredential;
    }
}
