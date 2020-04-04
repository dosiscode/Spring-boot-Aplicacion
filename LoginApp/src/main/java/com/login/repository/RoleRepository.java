package com.login.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.login.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

}
