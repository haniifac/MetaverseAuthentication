package org.ukdw.services;

import org.ukdw.exception.BadRequestException;
//import org.ukdw.model.remote.RolePermission;
//import org.ukdw.repository.dao.remote.RolePermissionDao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

//    private final RolePermissionDao repository;

    private final ModelMapper modelMapper;

    public Boolean getRolePermission(String email, String category){
       /* Optional<RolePermission> permission = repository.findByEmail(email,category);
        if(permission.isPresent()){
            RolePermissionDTO permissionDTO = modelMapper.map(permission.get(),RolePermissionDTO.class);
            return permissionDTO.isStatus();
        }
        throw new BadRequestException("Role or Permission Category doesnt exist !");*/
        throw new BadRequestException("Role or Permission Category doesnt exist !");
    }
}
