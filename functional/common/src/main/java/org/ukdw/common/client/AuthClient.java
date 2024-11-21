package org.ukdw.common.client;

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
import org.ukdw.common.dto.AppsCheckPermissionRequest;
import org.ukdw.common.dto.AppsCheckPermissionResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthClient {

    @Value("${auth.service.check-permission-path-common}")
    private String checkPermissionUrl;

    private final RestTemplate restTemplateCommon;

    public Object checkPermission(AppsCheckPermissionRequest request, HttpServletRequest httpServletRequest) {
        HttpHeaders headers2 = new HttpHeaders();
        headers2.set("Authorization", httpServletRequest.getHeader("Authorization"));
        headers2.set("Content-Type", "application/json");
        headers2.set("X-id", httpServletRequest.getHeader("X-id"));

        HttpEntity<AppsCheckPermissionRequest> entity = new HttpEntity<>(request, headers2);
        log.info("Request to auth service: {}", entity);
        ResponseEntity<Object> response = restTemplateCommon.exchange(
                checkPermissionUrl, HttpMethod.POST, entity, Object.class);

        log.info("Response from auth service: {}", response.getBody());
        return response.getBody();
    }
}
