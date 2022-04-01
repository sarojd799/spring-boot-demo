package com.demoapp.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.demoapp.dto.RoleDTO;

@Repository
public interface RolesRepository extends CrudRepository<RoleDTO, Integer> {
	public abstract RoleDTO findByRoleName(String roleName);
}


