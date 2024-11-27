package org.ukdw.classroom.client;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.ukdw.classroom.dto.client.FindUserByIdRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClient {

    @Value("${users.service.user-exist-url}")
    private String findUser;

    private final RestTemplate restTemplateCommon;

    public Object findUserExistbyId(FindUserByIdRequest request, HttpServletRequest httpServletRequest){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", httpServletRequest.getHeader("Authorization"));
        headers.set("Content-Type", "application/json");
        headers.set("X-internal", "secret");

        HttpEntity<FindUserByIdRequest> entity = new HttpEntity<>(request, headers);
        log.info("checking to {} if there is user id: {}", findUser, request.getUserId());

        ResponseEntity<Object> response = restTemplateCommon.exchange(
                findUser+"/"+request.getUserId(), HttpMethod.GET, entity, Object.class
        );

        log.info("body of request is: {}", response.getBody());
        return response.getBody();
    }
}
