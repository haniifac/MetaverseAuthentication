package org.ukdw.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukdw.authservice.entity.GroupEntity;
import org.ukdw.authservice.entity.UserAccountEntity;
import org.ukdw.authservice.repository.GroupRepository;
import org.ukdw.authservice.repository.UserAccountRepository;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserAccountRepository userAccountRepository;
    private final GroupRepository groupRepository;

    public boolean addUserGroup(long userId, long groupId){
        Optional<UserAccountEntity> userOpt = userAccountRepository.findById(userId);
        Optional<GroupEntity> groupOpt = groupRepository.findById(groupId);
        if(userOpt.isPresent() && groupOpt.isPresent()){
            UserAccountEntity user = userOpt.get();
            GroupEntity group = groupOpt.get();
            Set<GroupEntity> existingGroups = user.getGroups();
            existingGroups.add(group);

            userAccountRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean removeUserGroup(long userId, long groupId){
        Optional<UserAccountEntity> userOpt = userAccountRepository.findById(userId);
        Optional<GroupEntity> groupOpt = groupRepository.findById(groupId);
        if(userOpt.isPresent() && groupOpt.isPresent()){
            UserAccountEntity user = userOpt.get();
            GroupEntity group = groupOpt.get();
            Set<GroupEntity> existingGroups = user.getGroups();
            existingGroups.remove(group);

            userAccountRepository.save(user);
            return true;
        }
        return false;
    }
}
