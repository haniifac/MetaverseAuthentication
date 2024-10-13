package org.ukdw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ukdw.entity.GroupEntity;
import org.ukdw.repository.GroupRepository;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupEntity findByGroupname(String groupname) {
        return groupRepository.findByGroupname(groupname);
    }
}
