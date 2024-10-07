package org.ukdw.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.ukdw.dto.user.UserRoleDTO;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleService {

//    private final UserRoleDao repository;

    private final ModelMapper modelMapper;

    public UserRoleDTO getRole(String role) {
        UserRoleDTO dummy = new UserRoleDTO();
        dummy.setRole(role);
        dummy.setIdRole(role);
        return dummy;
    }

    /*public UserRoleDTO getRole(String name){
        Optional<UserRole> role = repository.findByRoleName(name);
        if(role.isPresent()){
            UserRoleDTO data = modelMapper.map(role.get(),UserRoleDTO.class);
            return data;
        }else{
            throw new BadRequestException("Role is doesnt exist");
        }

    }

    public UserRoleDTO getRoleByEmail(String email){
        Optional<UserRole> role = repository.findByEmail(email);
        if(role.isPresent()){
            UserRoleDTO data = modelMapper.map(role.get(),UserRoleDTO.class);
            return data;
        }else{
            throw new BadRequestException("Role is doesnt exist");
        }
    }*/
}
