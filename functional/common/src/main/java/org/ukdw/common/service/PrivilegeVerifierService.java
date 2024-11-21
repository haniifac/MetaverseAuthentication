package org.ukdw.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.ukdw.common.client.AuthClient;
import org.ukdw.common.dto.AppsCheckPermissionRequest;
import org.ukdw.common.dto.AppsCheckPermissionResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivilegeVerifierService {

    private final AuthClient authClient;

    public boolean hasPrivilege(String roles, Long... permissions) {
        try {
            log.info("Checking permission for roles: {} and permissions: {}", roles, permissions);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String[] rolesArray = roles.split(",");
            AppsCheckPermissionRequest permissionRequest = new AppsCheckPermissionRequest();
            permissionRequest.setRoles(rolesArray);
            permissionRequest.setPermissions(permissions);

            var response = authClient.checkPermission(permissionRequest, request);

            ObjectMapper mapper = new ObjectMapper();

            var convert = mapper.convertValue(response,  AppsCheckPermissionResponse.class);
            return convert.isStatus();

        } catch (Exception e) {
            log.error("Error while checking permission", e);
            return false;
        }
    }
}
