package org.ukdw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukdw.entity.GroupEntity;
import org.ukdw.entity.UserAccountEntity;
import org.ukdw.repository.UserAccountRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessControlService {
    private final UserAccountRepository userAccountRepository;

    public boolean canAccessResource(String username, long requiredPermission) {
        Optional<UserAccountEntity> userOpt = userAccountRepository.findByUsername(username);

        if (userOpt.isPresent()){
            UserAccountEntity user = userOpt.get();
            for (GroupEntity group : user.getGroups()) {
                if (group.hasPermission(requiredPermission)) {
                    return true;
                }
            }
        }
        return false;
    }
}
