package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.enumerations.RoleType;
import com.linkedin.linkedinclone.model.Role;
import com.linkedin.linkedinclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

public interface RoleRepository extends JpaRepository<Role, Long>  {
    @Query("SELECT r FROM Role r WHERE r.name  = :rn ")
    Role findByName(@PathVariable RoleType rn);
}
