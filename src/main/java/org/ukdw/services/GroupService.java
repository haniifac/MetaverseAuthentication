package org.ukdw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ukdw.dto.group.GroupDTO;
import org.ukdw.entity.GroupEntity;
import org.ukdw.entity.UserAccountEntity;
import org.ukdw.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public List<GroupEntity> getAllGroups() {
        return groupRepository.findAll();
    }

    public GroupEntity findByGroupname(String groupname) {
        return groupRepository.findByGroupname(groupname);
    }

    public Optional<GroupEntity> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public GroupEntity createGroup(GroupEntity groupEntity) {
        return groupRepository.save(groupEntity);
    }

    public Optional<GroupEntity> updateGroup(Long id, GroupDTO groupDetails) {
        return groupRepository.findById(id).map(group -> {
            if(groupDetails.getGroupname().isPresent()){
                group.setGroupname(groupDetails.getGroupname().get());
            }

            if(groupDetails.getPermission().isPresent()){
            group.setPermission(groupDetails.getPermission().get());

            }
            return groupRepository.save(group);
        });
    }

    public boolean deleteGroup(Long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
