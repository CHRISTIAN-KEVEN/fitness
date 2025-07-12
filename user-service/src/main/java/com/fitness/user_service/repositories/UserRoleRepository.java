package com.fitness.user_service.repositories;

import com.fitness.user_service.models.TUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<TUserRole, Long> {
}
