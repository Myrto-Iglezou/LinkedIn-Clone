package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.enumerations.RoleType;
import com.linkedin.linkedinclone.model.Role;
import com.linkedin.linkedinclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, RoleType>  {

}
