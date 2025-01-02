package org.ukdw.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ukdw.authservice.entity.GroupEntity;
import org.ukdw.authservice.entity.UserAccountEntity;
import org.ukdw.authservice.repository.GroupRepository;
import org.ukdw.authservice.repository.UserAccountRepository;
import org.ukdw.common.exception.RequestParameterErrorException;
import org.ukdw.common.exception.ResourceNotFoundException;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserAccountRepository userAccountRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public boolean addUserGroup(Long userId, Long groupId){
        Optional<UserAccountEntity> userOpt = userAccountRepository.findById(userId);
        Optional<GroupEntity> groupOpt = groupRepository.findById(groupId);
        if(userOpt.isEmpty()){
            throw new ResourceNotFoundException("User not found with id: "+ userId);
        }
        if(groupOpt.isEmpty()){
            throw new ResourceNotFoundException("Group not found with id: "+ groupId);
        }

        UserAccountEntity user = userOpt.get();
        GroupEntity group = groupOpt.get();
        Set<GroupEntity> existingGroups = user.getGroups();
        var isAdded = existingGroups.add(group);

        if(!isAdded){
            throw new RequestParameterErrorException("Group already exist in this user");
        }
        userAccountRepository.save(user);
        return true;
    }

    @Transactional
    public boolean removeUserGroup(long userId, long groupId){
        Optional<UserAccountEntity> userOpt = userAccountRepository.findById(userId);
        Optional<GroupEntity> groupOpt = groupRepository.findById(groupId);
        if(userOpt.isEmpty()){
            throw new ResourceNotFoundException("User not found with id: "+ userId);
        }
        if(groupOpt.isEmpty()){
            throw new ResourceNotFoundException("Group not found with id: "+ groupId);
        }

        UserAccountEntity user = userOpt.get();
        GroupEntity group = groupOpt.get();
        Set<GroupEntity> existingGroups = user.getGroups();

        var isRemoved = existingGroups.remove(group);
        if(!isRemoved){
            throw new RequestParameterErrorException("Group did not exist in this user");
        }
        userAccountRepository.save(user);
        return true;
    }
}
