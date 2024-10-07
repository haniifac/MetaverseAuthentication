package org.ukdw.dto;

import lombok.Data;

@Data
public class RolePermissionDTO {

    int id_permission;

    String role;

    String category;

    boolean status;

}
