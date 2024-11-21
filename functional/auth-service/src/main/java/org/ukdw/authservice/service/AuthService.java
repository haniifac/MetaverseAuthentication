package org.ukdw.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.ukdw.authservice.dto.request.auth.SignUpRequest;
//import org.ukdw.authservice.dto.response.RefreshAccessTokenResponse;
//import org.ukdw.authservice.dto.user.UserRoleDTO;
import org.springframework.web.server.ResponseStatusException;
import org.ukdw.authservice.client.ProfileClient;
import org.ukdw.authservice.dto.SignUpRequest;
import org.ukdw.authservice.dto.StudentRequestDto;
import org.ukdw.authservice.dto.TeacherRequestDto;
import org.ukdw.authservice.entity.*;
import org.ukdw.authservice.exception.AuthenticationExceptionImpl;
import org.ukdw.authservice.exception.BadRequestException;
import org.ukdw.authservice.exception.ScNotFoundException;
//import org.ukdw.authservice.exception.InvalidTokenException;
//import org.ukdw.authservice.filter.EmailValidation;
import org.ukdw.authservice.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.ukdw.common.dto.VerifyTokenDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserAccountService userAccountService;
    private final UserAccountRepository userAccountRepository;
    private final JwtService jwtService;
    private final ProfileClient profileClient;
    private final GroupService groupService;

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

    public UserAccountEntity signUp(SignUpRequest request){
        UserAccountEntity userAccountEntity = new UserAccountEntity(
                request.getEmail(),
                request.getUsername(),
                request.getPassword(),
                request.getRegNumber(),
                request.getScope()
        );

        Map<String, Runnable> groupActions = Map.of(
                "student", () -> {
                    GroupEntity studentGroup = groupService.findByGroupname("STUDENT");
                    userAccountEntity.getGroups().add(studentGroup);
                },
                "teacher", () -> {
                    GroupEntity teacherGroup = groupService.findByGroupname("TEACHER");
                    userAccountEntity.getGroups().add(teacherGroup);
                },
                "admin", () -> {
                    GroupEntity adminGroup = groupService.findByGroupname("ADMIN");
                    userAccountEntity.getGroups().add(adminGroup);
                }
        );
        Optional.ofNullable(request.getScope())
                .map(groupActions::get)
                .ifPresent(Runnable::run);

        var userAccountEntitySaved = userAccountService.createUserAccount(userAccountEntity);

        Map<String, Runnable> scopeActions = Map.of(
                "student", () -> this.createStudent(userAccountEntitySaved.getId(), request),
                "teacher", () -> this.createTeacher(userAccountEntitySaved.getId(), request)
        );

        Optional.ofNullable(request.getScope())
                .map(scopeActions::get)
                .ifPresent(Runnable::run);


        return userAccountEntitySaved;
    }

    public UserAccountEntity signIn(String email, String password) throws ScNotFoundException, BadRequestException {
        try {
            UserAccountEntity accountEntity = userAccountRepository.findByEmailAndPassword(email, password);
            if (accountEntity == null) {
                throw new AuthenticationExceptionImpl("email or password is wrong. email :" + email);
            }
            CustomUserDetails userDetails = new CustomUserDetails(accountEntity);

            System.out.println(accountEntity.getGroups());
            String token = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            accountEntity.setAccessToken(token);
            accountEntity.setRefreshToken(refreshToken);
            userAccountRepository.save(accountEntity);
            return accountEntity;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDetailsService userDetailsService() {
        return username -> {
            UserAccountEntity accountEntity = userAccountRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new CustomUserDetails(accountEntity);
        };
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean canAccessFeature(long requiredPermission) {
        Authentication authentication = getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String currentUserName = authentication.getName();
            Set<GroupEntity> groups = userDetails.getUserAccountEntity().getGroups();

            //check each permission on each group
            for (GroupEntity group : groups) {
                if (group.hasPermission(requiredPermission)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canAccessFeature(String[] roles, Long[] permissions, HttpServletRequest httpServletRequest) {
        long id = Long.parseLong(httpServletRequest.getHeader("X-id"));

        UserAccountEntity userAccountEntity = userAccountRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // match role from parameter and db, if match check permission
        for (String role : roles) {
            if (userAccountEntity.getGroups().stream().anyMatch(group -> group.getGroupname().equals(role))) {
                for (Long permission : permissions) {
                    if (userAccountEntity.getGroups().stream().anyMatch(group -> group.hasPermission(permission))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public VerifyTokenDto isTokenValidAndNotExpired(String token) {
        try {
            String username = jwtService.extractUserName(token);
            UserDetails userDetails = userDetailsService().loadUserByUsername(username);
            if (userDetails == null || jwtService.isTokenExpired(token)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
            }

            Claims claims = jwtService.extractAllClaims(token);
            int id = ((Double) claims.get("id")).intValue();
            String role = String.valueOf(claims.get("role"));
            int permission = ((Double) claims.get("permission")).intValue();

            return VerifyTokenDto.builder().id(id).permission(permission).role(role).build();
        } catch (JwtException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token", e);
        }
    }

    public String refreshAccessToken(String refreshToken) throws ParseException {
        String username = jwtService.extractUserName(refreshToken);
        UserDetails userDetails = userDetailsService().loadUserByUsername(username);
        if (jwtService.validateRefreshToken(refreshToken, userDetails)) {
            return jwtService.generateToken(userDetails);
        } else {
            throw new JwtException("Invalid refresh token");
        }
    }

    private void createTeacher(Long userId, SignUpRequest request){
        var teacherRequest = TeacherRequestDto.builder()
                .userId(userId)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .nid(request.getNid())
                .address(request.getAddress())
                .city(request.getCity())
                .region(request.getRegion())
                .country(request.getCountry())
                .zipCode(request.getZipCode())
                .gender(request.getGender())
                .googleScholar(request.getUrlGoogleScholar())
                .build();
        profileClient.createTeacher(teacherRequest);
    }

    private void createStudent(Long userId, SignUpRequest request){
        var studentRequest = StudentRequestDto.builder()
                .userId(userId)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .nim(request.getNim())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .city(request.getCity())
                .region(request.getRegion())
                .country(request.getCountry())
                .zipCode(request.getZipCode())
                .gender(request.getGender())
                .build();
        profileClient.createStudent(studentRequest);
    }
}
