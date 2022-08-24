package com.github.backend.persist.admin.repository;

import com.github.backend.persist.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> { ;
    boolean existsByAdminId(String string);
}
