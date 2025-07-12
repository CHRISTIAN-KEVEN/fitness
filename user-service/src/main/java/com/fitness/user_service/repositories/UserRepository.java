package com.fitness.user_service.repositories;

import com.fitness.user_service.models.TUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<TUser, String> {

//    @Query(value = "SELECT * FROM t_user WHERE em_status = ?1 LIMIT ?3 OFFSET ?2", nativeQuery = true)
    @Query(value = "SELECT * FROM t_user WHERE em_status = ?1 LIMIT ?2, ?3", nativeQuery = true)
    List<TUser> findByEmStatus(String enabled, int start, int size);

    // Pageable version
    Page<TUser> findByEmStatus(String enabled, Pageable pageable);

    TUser findByLgUserId(String lgUserId);
}
