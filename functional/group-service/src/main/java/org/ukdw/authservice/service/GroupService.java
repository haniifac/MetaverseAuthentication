package org.ukdw.authservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukdw.authservice.dto.group.GroupDTO;
import org.ukdw.authservice.entity.GroupEntity;
import org.ukdw.authservice.repository.GroupRepository;
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

    public boolean addGroupPermission(Long id, Long permission){
        Optional<GroupEntity> groupOpt = groupRepository.findById(id);
        if(groupOpt.isPresent()){
            GroupEntity group = groupOpt.get();
            group.addRoleOrPermission(permission);
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    public boolean removeGroupPermission(Long id, Long permission){
        Optional<GroupEntity> groupOpt = groupRepository.findById(id);
        if(groupOpt.isPresent()){
            GroupEntity group = groupOpt.get();
            group.removeRoleOrPermission(permission);
            groupRepository.save(group);
            return true;
        }
        return false;
    }
}
