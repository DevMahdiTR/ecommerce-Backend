package com.ecommerce.ecommerce.service.role;


import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.role.Role;
import com.ecommerce.ecommerce.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role fetchRoleByName(String roleName) {
        return roleRepository.fetchRoleByName(roleName).orElseThrow(
                ()-> new ResourceNotFoundException("The role with name : %s could not be found.")
        );
    }
}
