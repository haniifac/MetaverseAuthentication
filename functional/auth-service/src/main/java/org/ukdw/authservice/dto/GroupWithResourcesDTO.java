package org.ukdw.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.ukdw.authservice.entity.GroupEntity;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class GroupWithResourcesDTO {
    private Long id;
    private String groupName;
    private long permission;
    private Map<Long, String> resources;

    public GroupWithResourcesDTO(GroupEntity group, Map<Long, String> resources) {
        this.id = group.getId();
        this.groupName = group.getGroupname();
        this.permission = group.getPermission();
        this.resources = resources;
    }
}
