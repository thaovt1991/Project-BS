package com.cg.service.role;

import com.cg.model.Role;
import com.cg.service.IGeneralService;

import java.util.List;

public interface RoleService extends IGeneralService<Role> {

    Role findByName(String name);

    List<Role> findAllByIdIsNot(Long id);
}
