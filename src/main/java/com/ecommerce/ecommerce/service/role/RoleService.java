package com.ecommerce.ecommerce.service.role;


import com.ecommerce.ecommerce.model.role.Role;

public interface RoleService {

    public Role fetchRoleByName(final String roleName);
}
