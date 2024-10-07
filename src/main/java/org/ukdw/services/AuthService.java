package org.ukdw.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import org.h2.engine.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.ukdw.dto.request.auth.SignUpRequest;
import org.ukdw.dto.response.JwtAuthenticationResponse;
import org.ukdw.dto.user.UserRoleDTO;
import org.ukdw.entity.CustomUserDetails;
import org.ukdw.exception.AuthenticationExceptionImpl;
import org.ukdw.exception.BadRequestException;
import org.ukdw.exception.ScNotFoundException;
import org.ukdw.exception.InvalidTokenException;
import org.ukdw.filter.EmailValidation;
import org.ukdw.entity.UserAccountEntity;
import org.ukdw.repository.UserAccountRepository;
import org.ukdw.util.GoogleTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

/**
 * <p>
 * Creator: dendy
 * Date: 8/29/2020
 * Time: 7:52 AM
 * <p>
 * Description : service for auth process
 */

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserAccountService userAccountService;

    private final UserRoleService roleService;

    private final EmailValidation emailValidation;

    private final GoogleTokenVerifier googleTokenVerifier;

    private final UserAccountRepository userAccountRepository;

    private final JwtService jwtService;

    /*public JwtAuthenticationResponse signup(SignUpRequest request) {
        UserAccountEntity user = UserAccountEntity.builder()
                .firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }*/

    public boolean signUpWithGoogleAuthCode(String authCode, String regNumber, String role, String clientType)
            throws InvalidTokenException {
        if (!regNumber.isEmpty() || !role.isEmpty()) {
            UserRoleDTO userRole = roleService.getRole(role);
            GoogleTokenResponse response;
            GoogleIdToken.Payload payload;
            try {
                response = googleTokenVerifier.verifyAuthCode(authCode, clientType);
                payload = response.parseIdToken().getPayload();
            } catch (IOException e) {
                throw new InvalidTokenException("invalid authcode");
            }
            return !googleTokenVerifier.verifyIdToken(response.getIdToken()).getEmail().isEmpty();
//            if (emailValidation.emailIsValid(payload.getEmail())) {
//                return userAccountService.addUserAccount(payload.getEmail(), regNumber, userRole,
//                        response.getRefreshToken());
//            }
//            throw new UnauthorizedException("Email must be ti, si or staff");
        }
        throw new BadRequestException("Sorry! NIM or Role can't empty");
    }

    public boolean normalSignUp(String email, String nomorInduk, String role) throws InvalidTokenException {
       /* if (!nomorInduk.isEmpty() || !role.isEmpty()) {
            UserRoleDTO userRole = roleService.getRole(role);
            if (emailValidation.emailIsValid(email)) {
                return userAccountService.addUserAccount(email, nomorInduk, userRole, null);
            }
            throw new UnauthorizedException("Email must be ti, si or staff");
        }*/
        throw new BadRequestException("Sorry! NIM or Role can't empty");
    }

    public boolean signOut(String accessToken) {
       /* try {
            //sign out means revoke apps access from user account
            //fcmtoken wont deleted
            //makesure front end delete its session
            AccessTokenResponse verifyAccessTokenResponse = googleApiClient.verifyAccessToken(accessToken);
            UserAccountDTO userAccount = userAccountService.getDetailData(verifyAccessTokenResponse.getEmail());
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            AccessTokenResponse revokeTokenResponse = googleAccountApiClient.revokeToken(headers, accessToken);
            userAccount.setRefreshToken(null);
            userAccountService.updateUserAccount(userAccount);
            return revokeTokenResponse != null;
        } catch (FeignException e) {*/
        return false;
//        }
    }

    public UserAccountEntity signIn(String email, String password) throws ScNotFoundException, BadRequestException {
        try {
//            GoogleTokenResponse response = googleTokenVerifier.verifyAuthCode(authCode, clientType);
//            GoogleIdToken.Payload payload = response.parseIdToken().getPayload();
            UserAccountEntity accountEntity = userAccountRepository.findByEmailAndPassword(email, password);
            if (accountEntity == null) {
                throw new AuthenticationExceptionImpl("email or password is wrong. email :"
                        + email);
            }
            CustomUserDetails userDetails = new CustomUserDetails(accountEntity);
            String token = jwtService.generateToken(userDetails);
            accountEntity.setAccessToken(token);
//            accountEntity.setRole("ADMIN");
            return accountEntity;
           /* UserAccountDTO userAccount = userAccountService.getDetailData(payload.getEmail());
            UserRoleDTO userRoleDTO = roleService.getRoleByEmail(payload.getEmail());

            if (userAccount != null) {
                if (response.getRefreshToken() != null) {
                    userAccount.setRefreshToken(response.getRefreshToken());
                    userAccountService.updateUserAccount(userAccount);
                }
                if (userRoleDTO.getRole().equalsIgnoreCase(AuthoritiesConstants.TEACHER) ||
                        userRoleDTO.getRole().equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {
                    DosenDTO dosenDTO = dosenService.getDosenByKodeDosen(userAccount.getIdUser());
                    return new User(
                            response.getAccessToken(),
                            response.getIdToken(),
                            userAccount.getRefreshToken(),
                            dosenDTO.getNik(),
                            dosenDTO.getNama(),
                            userAccount.getEmail(),
                            (String) payload.get("picture"),
                            userRoleDTO.getRole());
                } else if (userRoleDTO.getRole().equalsIgnoreCase(AuthoritiesConstants.STUDENT)) {
                    MahasiswaDTO mahasiswaDTO = mahasiswaService.getMahasiswaByNim(userAccount.getIdUser());
                    return new User(
                            response.getAccessToken(),
                            response.getIdToken(),
                            userAccount.getRefreshToken(),
                            mahasiswaDTO.getNim(),
                            mahasiswaDTO.getNama(),
                            userAccount.getEmail(),
                            (String) payload.get("picture"),
                            userRoleDTO.getRole());
                } else {
                    throw new OAuth2AuthenticationProcessingException("Sorry! Login with role "
                            + userRoleDTO.getRole() + " is not supported yet.");
                }
            }
            throw new ScNotFoundException("User not found");
            */
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

//        throw new BadRequestException("Not implemented yet");
    }

    public String validateIdToken(String idToken) throws InvalidTokenException {
        if (idToken != null) {
            return googleTokenVerifier.verifyIdToken(idToken).getEmail();
        }
        throw new BadRequestException("Id Token cant be empty");
    }

    public boolean validateAccessToken(String accessToken) {
//        try {
//            AccessTokenResponse accessTokenResponse = googleApiClient.verifyAccessToken(accessToken);
//            return accessTokenResponse != null;
//        } catch (FeignException e) {
        return false;
//        }
    }

    public GoogleTokenResponse refreshAccessToken(String refreshToken) throws InvalidTokenException {
        return googleTokenVerifier.refreshAccessToken(refreshToken);
    }


    public boolean revokeGoogleToken(String token) {
//        try {
//            //token can be access token or refresh token
//            Map<String, String> headers = new HashMap<>();
//            headers.put("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
//            AccessTokenResponse accessTokenResponse = googleAccountApiClient.revokeToken(headers, token);
//            return accessTokenResponse != null;
//        } catch (FeignException e) {
//            return false;
//        }
        return false;
    }

    /*public Authentication getAuthorization(String accessToken) throws FeignException, IOException {
        AccessTokenResponse accessTokenResponse = googleApiClient.verifyAccessToken(accessToken);
        UserRoleDTO userRoleDTO = roleService.getRoleByEmail(accessTokenResponse.getEmail());
        UserAccountDTO userAccount = userAccountService.getDetailData(accessTokenResponse.getEmail());
        List<UserRoleDTO> accountRoleList = new ArrayList<>();
        accountRoleList.add(userRoleDTO);
        //get credential
        Credential credential = googleTokenVerifier.getGoogleCredential(accessToken);
        credential.setRefreshToken(userAccount.getRefreshToken());

        Collection<? extends GrantedAuthority> authorities = accountRoleList
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(accessTokenResponse.getEmail(), credential, authorities);
        throw new IOException("Not implemented yet");
    }*/

    public UserDetailsService userDetailsService() {
        return username -> {
            UserAccountEntity accountEntity = userAccountRepository.findByUsername(username);
            return new CustomUserDetails(accountEntity);
            /*return userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));*/
        };
    }
}
