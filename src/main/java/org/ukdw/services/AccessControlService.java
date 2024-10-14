package org.ukdw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukdw.entity.GroupEntity;
import org.ukdw.entity.UserAccountEntity;

@Service
@RequiredArgsConstructor
public class AccessControlService {
    public boolean canAccessResource(UserAccountEntity user, int requiredPermission) {
        for (GroupEntity group : user.getGroups()) {
            if (group.hasPermission(requiredPermission)) {
                return true;
            }
        }

        return false;
    }
}
