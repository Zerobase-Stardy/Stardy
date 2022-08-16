package com.github.backend.persist.repository;

import com.github.backend.persist.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> { ;
    boolean existsByAdminId(String string);
}
