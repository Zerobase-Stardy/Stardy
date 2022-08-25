package com.github.backend.persist.admin.repository;

import com.github.backend.persist.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> { ;
    Optional<Admin> findByAdminId(String AdminId);
    boolean existsByAdminId(String string);
}
