package org.ukdw.authservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukdw.authservice.dto.GroupDTO;
import org.ukdw.authservice.dto.GroupWithResourcesDTO;
import org.ukdw.authservice.entity.GroupEntity;
import org.ukdw.authservice.repository.GroupRepository;
import org.ukdw.common.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final ResourceService resourceService;

    public List<GroupEntity> getAllGroups() {
        return groupRepository.findAll();
    }

    public List<GroupWithResourcesDTO> getAllGroupsWithResources(){
        List<GroupEntity> groups = groupRepository.findAll();
        return groups.stream()
                .map(group -> {
                    Map<Long, String> resources = resourceService.loadResourceNames(group.getPermission());
                    return new GroupWithResourcesDTO(group, resources);
                })
                .collect(Collectors.toList());
    }

    public GroupEntity findByGroupname(String groupname) {
        return groupRepository.findByGroupname(groupname);
    }

    public GroupWithResourcesDTO getGroupById(Long id) {
//        return groupRepository.findById(id);
        Optional<GroupEntity> groupOpt = groupRepository.findById(id);
        if(groupOpt.isEmpty()){
            throw new ResourceNotFoundException("Group id: "+ id + " not found");
        }

        GroupEntity group = groupOpt.get();
        Map<Long, String> resources = resourceService.loadResourceNames(group.getPermission());
        return new GroupWithResourcesDTO(group, resources);
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
